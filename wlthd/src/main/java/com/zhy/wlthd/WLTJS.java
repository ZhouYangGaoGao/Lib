package com.zhy.wlthd;

import android.location.Location;
import android.webkit.JavascriptInterface;

import com.google.gson.Gson;

import base.BWebJS;
import util.GPSUtils;
import util.LogUtils;
import util.MMap;

public class WLTJS extends BWebJS {
    private final static String LOCATION = "locationPosition(";
    private GPSUtils gpsUtils;

    @JavascriptInterface
    @Override
    public String getInfo(int code) {
        return getInfo(code, "");
    }

    @JavascriptInterface
    @Override
    public String getInfo(int code, String info) {
        returnInfo = "";
        switch (code) {
            case 100://当前位置获取
                GPSUtils.location(this);
                break;
            case 101://连续位置获取
                gpsUtils = GPSUtils.updates(this);
                break;
            case 102://停止连续位置获取
                if (gpsUtils != null) gpsUtils.removeListener();
                break;
        }
        return super.getInfo(code, info);
    }

    @Override
    public void onReceiveValue(String value) {
        LogUtils.e("onReceiveValue-->value" + value);
    }

    @Override
    public void location(Location location) {
        String json = new Gson().toJson(new MMap("latitude", location.getLatitude()).add("longitude", location.getLongitude()));
        callJs(LOCATION + json + ")");
    }
}
