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
                .setBugLy("5c1d24a237")
                .setToken(loginModel == null ? "0" : loginModel.getToken());
    }
}
