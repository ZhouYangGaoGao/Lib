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
                .setToken("88a5f621de3e2594d0410825b93209a1");
    }

    @Override
    public void logout(){
        Hawk.deleteAll();
//        BActivity.start(LoginActivity.class);
    }
}
