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
        WLTLoginModel loginModel = Hawk.get(BConfig.LOGIN);
        BConfig.get().setBaseUrl("https://www.ahlzz.com/api/")
                .setClient("cms")
                .setFullScreen(true)
                .setBugLy("a19f54cc33")
                .setWebInterface(new WLTJS())
                .setTestPassword("123456")
                .setTestPhone("17600117227")
                .setToken(loginModel == null ? "0" : loginModel.getToken());
    }
}
