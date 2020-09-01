package base;

import android.app.Activity;
import android.location.Location;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.just.agentweb.AgentWeb;

import java.util.ArrayList;
import java.util.List;

import hawk.Hawk;
import util.GPSUtils;
import util.JsonUtil;
import util.LogUtils;
import util.MMap;

public class BWebJS implements ValueCallback<String>, GPSUtils.OnLocationListener {
    private final static String LOCATION = "locationPosition(";
    private GPSUtils gpsUtils;
    protected Activity activity;
    protected AgentWeb agentWeb;
    protected String returnInfo = "";

    public BWebJS setActivity(Activity activity) {
        this.activity = activity;
        return this;
    }

    public BWebJS setAgentWeb(AgentWeb agentWeb) {
        this.agentWeb = agentWeb;
        return this;
    }

    @JavascriptInterface
    public String getInfo(int code) {
        return getInfo(code, "");
    }

    @JavascriptInterface
    public String getInfo(int code, String info) {
        returnInfo = "";
        switch (code) {
            case 96://获取login信息
                returnInfo = JsonUtil.getJson(Hawk.contains(BConfig.LOGIN) ? Hawk.get(BConfig.LOGIN) : "");
                break;
            case 97://获取token
                returnInfo = BConfig.get().getToken();
                break;
            case 98://重新登录 登录过期
                BApp.app().logout();
                break;
            case 99://关闭页面
                activity.finish();
                break;
            case 100://当前位置获取
                GPSUtils.get().location(this);
                break;
            case 101://连续位置获取
                gpsUtils = GPSUtils.get().updates(this);
                break;
            case 102://停止连续位置获取
                if (gpsUtils != null) gpsUtils.removeListener();
                break;

        }
        LogUtils.e("code = " + code, "info = " + info + "\nreturn = " + returnInfo);
        return returnInfo;
    }

    @Override
    public void onReceiveValue(String value) {
        LogUtils.e("onReceive = " + value);
    }

    public void callJs(String jsStr) {
        LogUtils.e("callJs", jsStr);
        if (agentWeb != null)
            agentWeb.getJsAccessEntrace().callJs(jsStr, this);
    }

    @Override
    public void location(Location location) {
        if (location == null) return;
        String json = JsonUtil.getJson(new MMap("latitude", location.getLatitude()).add("longitude", location.getLongitude()));
        callJs(LOCATION + json + ")");
    }
}
