package com.zhy.app.base;

import base.BApp;
import base.BConfig;
import hawk.Hawk;

public class BaseApp extends BApp {
    @Override
    public void onCreate() {
        super.onCreate();
        BConfig.getConfig().setBaseUrl("https://www.ahlzz.com/api/")
                .setClient("cms")
                .setToken("9af27eb7b9908ce3772d578aff2a517a");
    }

    @Override
    public void logout(){
        Hawk.deleteAll();
//        BActivity.start(LoginActivity.class);
    }
}
