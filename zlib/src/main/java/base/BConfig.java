package base;

import android.content.pm.ActivityInfo;

import com.zhy.android.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hawk.Hawk;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import util.LogUtils;
import util.Resource;
import util.SystemUtil;

public class BConfig {
    public static final String LOGIN = "login";
    public static final String REGISTER = "register";
    public static final String LOGIN_MODE = "mode";
    public static final String LOGIN_MODE_LOGIN = "login";
    public static final String LOGIN_MODE_REGISTER = "register";
    public static final String LOGIN_MODE_RESET = "reset";
    public static final String LOGIN_MODE_CAPTCHA = "captcha";

    public static final String TOP_SHOW = "showTop";
    public static final String TITLE = "title";
    public static final String BACK = "back";
    public static final String URL = "url";
    public static final String ANDROID = "android";
    public static final String TEST_URL = "article/listproject/0/json";
    public static final String RE_PASSWORD = "repassword";
    public static final String PASSWORD = "password";
    public static final String USER_NAME = "username";
    public static final String PHONE = "phone";
    public static final String PHONE_CODE = "phoneCode";
    public static final String CLIENT = "client";
    public static final String PAGE = "page";
    public static final String ID = "id";
    public static final String TABS = "tabs";
    public static final String COOKIES = "cookies";
    public static final String TYPE = "type";
    public static String[] LOADINGS;
    private static BConfig config;
    private Interceptor interceptor;
    private CookieJar cookieJar;
    private String baseUrl;
    private String client = "app";
    private String token = "0";
    private String bugLy;
    private String testPhone;
    private String testPassword;
    private String expiredCode;
    private Class<?> webInterface;
    private Class<?> loginClass;
    private boolean fullScreen = false;
    private boolean isExpired = false;
    private int colorTheme, colorTheme88;
    private int orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

    public static BConfig get() {
        if (config == null)
            config = new BConfig();
        return config;
    }

    private BConfig() {
        LOADINGS = Resource.stringArray(R.array.load_name);
    }

    public CookieJar getCookieJar() {
        if (cookieJar == null)
            cookieJar = new CookieJar() {
                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                    if ((url.toString().contains(LOGIN) || url.toString().contains(REGISTER)) && cookies.size() > 0)
                        Hawk.put(COOKIES, cookies);
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    return Hawk.get(COOKIES, new ArrayList<>());
                }
            };
        return cookieJar;
    }

    public BConfig setCookieJar(CookieJar cookieJar) {
        this.cookieJar = cookieJar;
        return this;
    }


    public int getColorTheme() {
        return colorTheme == 0 ? BApp.app().getResources().getColor(R.color.clo_theme) : colorTheme;
    }

    public int getColorTheme88() {
        return colorTheme == 0 ? BApp.app().getResources().getColor(R.color.clo_theme_88) : colorTheme88;
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

    public BConfig setColorTheme88(int colorTheme88) {
        this.colorTheme88 = colorTheme88;
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

    public Class getWebInterface() {
        return webInterface;
    }

    public BConfig setWebInterface(Class webInterface) {
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
                Request newRequest = builder.addHeader("client", client)
                        .addHeader("system", "android " + SystemUtil.getDeviceBrand() + " " + SystemUtil.getSystemModel())
                        .addHeader("Content-Type", "application/json; charset=UTF-8")
                        .addHeader("token", getToken())
                        .method(request.method(), body)
                        .url(urlNewBuilder.build())
                        .build();
                LogUtils.e("Url==", newRequest.toString());
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

    public boolean isNoColor() {
        return Hawk.get("noColor", false);
    }

    public BConfig setNoColor(boolean noColor) {
        Hawk.put("noColor", noColor);
        return this;
    }

    public String getTestPhone() {
        return testPhone;
    }

    public BConfig setTestPhone(String testPhone) {
        this.testPhone = testPhone;
        return this;
    }

    public String getTestPassword() {
        return testPassword;
    }

    public BConfig setTestPassword(String testPassword) {
        this.testPassword = testPassword;
        return this;
    }

    public String getExpiredCode() {
        return expiredCode;
    }

    public BConfig setExpiredCode(String expiredCode) {
        this.expiredCode = expiredCode;
        return config;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public BConfig setExpired(boolean expired) {
        isExpired = expired;
        return config;
    }

    public Class<?> getLoginClass() {
        return loginClass;
    }

    public BConfig setLoginClass(Class<?> loginClass) {
        this.loginClass = loginClass;
        return config;
    }
}
