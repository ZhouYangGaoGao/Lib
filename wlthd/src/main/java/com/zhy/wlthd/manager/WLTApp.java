package com.zhy.wlthd.manager;

import com.zhy.wlthd.WLTSplashActivity;

import base.BApp;
import base.BConfig;
import hawk.Hawk;
import util.GoTo;

public class WLTApp extends BApp {
//        public static final String url = "http://192.168.21.215:8080/operation-manual?user=";
//    public static final String url = "http://192.168.20.55:8028/plantation-pzw/#/505?user=";
    public static final String url = "https://www.ahlzz.com/ahyl/operation-manual?user=";

    @Override
    protected void initApp() {
//        BConfig.get().setBaseUrl("http://192.168.20.55:8991/api/")
        BConfig.get().setBaseUrl("https://www.ahlzz.com/ahyl-api/")
                .setClient("cms")
                .setFullScreen(true)
                .setBugLy("a19f54cc33")
                .setWebInterface(WLTJS.class)
                .setLoginClass(WLTSplashActivity.class)
                .setExpiredCode("401")
                .setTestPassword("123456")
                .setTestPhone("17600117227");
    }
}
