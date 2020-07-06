package base;

import android.content.pm.ActivityInfo;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import util.LogUtils;
import util.SystemUtil;

public class BConfig {
    public static final String LOGIN = "login";
    public static final String LOGIN_MODE = "mode";
    public static final String LOGIN_MODE_LOGIN = "login";
    public static final String LOGIN_MODE_REGISTER = "register";
    public static final String LOGIN_MODE_RESET = "reset";
    public static final String LOGIN_MODE_CAPTCHA = "captcha";
    protected static final int GET_DATA_NEVER = -1;
    protected static final int GET_DATA_CREATE = 0;
    protected static final int GET_DATA_RESUME = 1;
    private static BConfig config;
    private Interceptor interceptor;
    private String baseUrl;
    private String client = "app";
    private String token = "0";
    private String bugLy;
    private Object webInterface;
    private boolean fullScreen = false;
    private int colorTheme = 0xff36C177;
    private int orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

    public static BConfig getConfig() {
        if (config == null)
            config = new BConfig();
        return config;
    }

    private BConfig() {
    }

    public int getColorTheme() {
        return colorTheme;
    }

    public boolean isFullScreen() {
        return fullScreen;
    }

    public BConfig setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
        return config;
    }

    public String getBugLy() {
        return bugLy;
    }

    public BConfig setBugLy(String bugLy) {
        this.bugLy = bugLy;
        return config;
    }

    public BConfig setColorTheme(int colorTheme) {
        this.colorTheme = colorTheme;
        return config;
    }

    public String getToken() {
        return token;
    }

    public BConfig setToken(String token) {
        this.token = token;
        return config;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public BConfig setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return config;
    }

    public String getClient() {
        return client;
    }

    public BConfig setClient(String client) {
        this.client = client;
        return config;
    }

    public Object getWebInterface() {
        if (webInterface == null)
            webInterface = new BWebInterfase();
        return webInterface;
    }

    public BConfig setWebInterface(Object webInterface) {
        this.webInterface = webInterface;
        return config;
    }

    public Interceptor getInterceptor() {
        if (interceptor == null) interceptor = new Interceptor() {
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
                Request newRequest = builder.addHeader("client", getClient())
                        .addHeader("system", "android " + SystemUtil.getDeviceBrand() + " " + SystemUtil.getSystemModel())
                        .addHeader("Content-Type", "application/json; charset=UTF-8")
                        .addHeader("token", getToken())
                        .method(request.method(), body)
                        .url(urlNewBuilder.build())
                        .build();
                LogUtils.e("Url==", newRequest.url() + "\nHeader==" + newRequest.headers().toString());
                return chain.proceed(newRequest);
            }
        };
        return interceptor;
    }

    public BConfig setInterceptor(Interceptor interceptor) {
        if (this.interceptor == null) this.interceptor = interceptor;
        return config;
    }

    public int getOrientation() {
        return orientation;
    }

    public BConfig setOrientation(int orientation) {
        this.orientation = orientation;
        return config;
    }
}
