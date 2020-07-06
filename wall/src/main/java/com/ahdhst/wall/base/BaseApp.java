package com.ahdhst.wall.base;

import android.content.pm.ActivityInfo;

import base.BApp;
import base.BConfig;

public class BaseApp extends BApp {

    @Override
    protected void initApp() {
        BConfig.getConfig().setBaseUrl("https://www.ahlzz.com/api/")
                .setClient("cms")
                .setBugLy("59f02d25c2")
                .setOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
                .setToken("9af27eb7b9908ce3772d578aff2a517a");
    }
}
