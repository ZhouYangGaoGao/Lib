package util;

/**
 * 网络加载工具类
 */

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import base.BConfig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

public class RetrofitHelper {

    private static RetrofitHelper instance = null;
    private Retrofit mRetrofit = null;
    private Map<Class<?>, Object> singles;

    /**
     * 获取
     *
     * @param tClass
     * @param <T>    DataManager apiService
     * @return DataManager apiService
     */
    public static <T> T get(Class<T> tClass) {
        return init(tClass, null);
    }

    /**
     * 初始化 获取
     *
     * @param tClass
     * @param mRetrofit
     * @param <T>
     * @return DataManager apiService
     */
    private static <T> T init(Class<T> tClass, Retrofit mRetrofit) {
        if (instance == null) instance = new RetrofitHelper();

        if (instance.singles == null) instance.singles = new HashMap<>();

        T t = null;
        if (!instance.singles.containsKey(tClass)) {
            if (tClass.isInterface()) {//如果是接口 判断为apiService
                if (mRetrofit == null) { //如果 Retrofit为空 使用默认mRetrofit
                    if (instance.mRetrofit == null)//初始化默认Retrofit
                        instance.mRetrofit = getRetrofit(null, null);
                    mRetrofit = instance.mRetrofit;
                }//否则是自定义 Retrofit
                t = mRetrofit.create(tClass);
            } else {//否则判断为 DataManager
                try {
                    t = tClass.newInstance();
                } catch (IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }
            }
            if (t != null)
                instance.singles.put(tClass, t);//存储 DataManager或apiService
        }
        return t == null ? (T) instance.singles.get(tClass) : t;
    }

    /**
     * 添加自定义 Retrofit 并获取 DataManager apiService
     *
     * @param tClass
     * @param baseUrl
     * @param interceptor
     * @param <T>
     * @return DataManager apiService
     */
    public static <T> T custom(Class<T> tClass, String baseUrl, Interceptor interceptor) {
        return init(tClass, getRetrofit(baseUrl, interceptor));
    }

    /**
     * 私有化构造器 防止重复实例化
     */
    private RetrofitHelper() {
    }

    /**
     * 根据baseUrl 和 interceptor 创建 Retrofit
     *
     * @param baseUrl
     * @param interceptor
     * @return
     */
    private static Retrofit getRetrofit(String baseUrl, Interceptor interceptor) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(55, TimeUnit.SECONDS)
                .writeTimeout(55, TimeUnit.SECONDS)
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .cookieJar(BConfig.getConfig().getCookieJar())
                .addInterceptor(interceptor == null ? BConfig.getConfig().getInterceptor() : interceptor)
                .build();

        return new Retrofit.Builder()
                .baseUrl(TextUtils.isEmpty(baseUrl) ? BConfig.getConfig().getBaseUrl() : baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

}

