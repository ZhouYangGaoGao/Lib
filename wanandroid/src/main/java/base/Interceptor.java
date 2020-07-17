package base;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import util.LogUtils;
import util.SystemUtil;

public class Interceptor implements okhttp3.Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        HttpUrl.Builder urlNewBuilder;
        List<String> headerValues = request.headers("baseUrl");
        if (headerValues != null && headerValues.size() > 0) {
            builder.removeHeader("baseUrl");
            urlNewBuilder = HttpUrl.parse(headerValues.get(0)).newBuilder();
        } else urlNewBuilder = request.url().newBuilder();
        RequestBody body = request.body();
        //添加头部
        Request newRequest = builder.addHeader("client", "app")
                .addHeader("system", "android " + SystemUtil.getDeviceBrand() + " " + SystemUtil.getSystemModel())
                .addHeader("Content-Type", "application/json; charset=UTF-8")
//                .addHeader("loginUserName", BaseApp.app().getUser().getUsername())
//                .addHeader("loginUserPassword", BaseApp.app().getUser().getPassword())
                .method(request.method(), body)
                .url(urlNewBuilder.build())
                .build();
        LogUtils.e("Url==", newRequest.toString() + "\nHeader==" + newRequest.headers().toString());
        return chain.proceed(newRequest);
    }
}
