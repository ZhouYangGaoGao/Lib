package com.zhy.wlthd;

import android.location.Location;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.photo.FileModel;

import java.util.ArrayList;
import java.util.List;

import base.BApp;
import base.BWebJS;
import photopicker.lib.entity.LocalMedia;
import util.GPSUtils;
import util.JsonUtil;
import util.LogUtils;
import util.MMap;

public class WLTJS extends BWebJS {
    private final static String LOCATION = "locationPosition(";
    private final static String FILE = "file(";
    private GPSUtils gpsUtils;
    private FileModel fileModel;

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
            case 99://关闭页面
                BApp.app().act().finish();
                break;
            case 100://当前位置获取
                GPSUtils.location(this);
                break;
            case 101://连续位置获取
                gpsUtils = GPSUtils.updates(this);
                break;
            case 102://停止连续位置获取
                if (gpsUtils != null) gpsUtils.removeListener();
                break;
            case 103://图片上传
                if (fileModel == null) {
                    fileModel = new FileModel();
                    fileModel.initModel((AppCompatActivity) activity);
                    fileModel.setListener(new FileModel.OnFilesGetListener() {
                        @Override
                        public void files(List<LocalMedia> mediaList) {
                            callJs(FILE + JsonUtil.getJson(mediaList) + ")");
                        }
                    });
                }
                List<LocalMedia> list;
                if (TextUtils.isEmpty(info)) {
                    list = new ArrayList<>();
                } else {
                    list = new Gson().fromJson(info,new TypeToken<List<LocalMedia>>() {
                    }.getType());
                }
                fileModel.go(list);
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
