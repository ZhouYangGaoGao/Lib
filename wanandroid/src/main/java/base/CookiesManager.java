package base;

import java.util.ArrayList;
import java.util.List;

import hawk.Hawk;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import util.LogUtils;

public class CookiesManager implements CookieJar {

    private final static String LOGIN_URL = "https://www.wanandroid.com/user/login";

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        LogUtils.e("saveFromResponse", url.toString());
        LogUtils.e("cookies", cookies.toString());
        if (cookies.size() > 0 && LOGIN_URL.equals(url.toString())) {
            Hawk.put(LOGIN_URL, cookies);
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return Hawk.get(LOGIN_URL, new ArrayList<>());
    }
}
