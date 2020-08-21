package com.zhy.wlthd;

import base.BApp;
import base.BConfig;
import hawk.Hawk;
import util.GoTo;

public class WLTApp extends BApp {

    @Override
    public void logout(){
        Hawk.deleteAll();
        GoTo.start(WLTSplashActivity.class);
    }

    @Override
    protected void initApp() {
        BConfig.get().setBaseUrl("http://192.168.20.10:8991/api/")
                .setClient("cms")
                .setFullScreen(true)
                .setBugLy("a19f54cc33")
                .setWebInterface(WLTJS.class)
                .setTestPassword("123456")
                .setTestPhone("17600117227");
    }
}
