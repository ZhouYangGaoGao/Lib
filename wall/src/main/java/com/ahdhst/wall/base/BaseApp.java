package com.ahdhst.wall.base;

import android.content.pm.ActivityInfo;

import base.BApp;
import base.BConfig;

public class BaseApp extends BApp {
    @Override
    public void onCreate() {
        super.onCreate();
        BConfig.getConfig().setBaseUrl("https://www.ahlzz.com/api/")
                .setClient("cms")
                .setBugly("59f02d25c2")
                .setOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
                .setToken("9af27eb7b9908ce3772d578aff2a517a");
    }
}
