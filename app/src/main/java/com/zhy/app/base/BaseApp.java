package com.zhy.app.base;

import com.zhy.app.modules.login.view.LoginFragment;

import base.BApp;
import base.BConfig;
import hawk.Hawk;
import util.GoTo;

public class BaseApp extends BApp {

    @Override
    public void logout(){
        Hawk.deleteAll();
        GoTo.start(LoginFragment.class);
    }

    @Override
    protected void initApp() {
        BConfig.getConfig().setBaseUrl("https://www.ahlzz.com/api/")
                .setClient("cms")
                .setToken("28660e36f6f8c2f14ec22e5322f03de6");
    }
}
