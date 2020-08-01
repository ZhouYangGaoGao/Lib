package com.ahdhst.wall;

import base.BApp;
import base.BConfig;

public class BaseApp extends BApp {

    @Override
    protected void initApp() {
        BConfig.get().setBaseUrl("https://www.ahlzz.com/api/")
                .setClient("cms")
                .setFullScreen(true)
                .setBugLy("59f02d25c2")
                .setToken("0");
    }
}
