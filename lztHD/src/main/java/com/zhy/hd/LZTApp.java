package com.zhy.hd;

import base.BApp;
import base.BConfig;
import hawk.Hawk;
import util.GoTo;

public class LZTApp extends BApp {

    @Override
    public void logout(){
        Hawk.deleteAll();
        GoTo.start(LZTLoginFragment.class);
    }

    @Override
    protected void initApp() {
        LZTLoginModel LZTLoginModel = Hawk.get(BConfig.LOGIN);
        BConfig.get().setBaseUrl("https://www.ahlzz.com/api/")
                .setClient("cms")
                .setFullScreen(true)
                .setBugLy("5c1d24a237")
                .setToken(LZTLoginModel == null ? "0" : LZTLoginModel.getToken());
    }
}
