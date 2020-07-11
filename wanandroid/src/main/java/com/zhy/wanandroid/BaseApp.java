package com.zhy.wanandroid;

import base.BApp;
import base.BConfig;
import hawk.Hawk;
import mvp.login.view.LoginFragment;
import util.GoTo;

public class BaseApp extends BApp {
    @Override
    protected void initApp() {
        BConfig.getConfig()
                .setBaseUrl("https://www.wanandroid.com/")
                .initCardView();
    }

    @Override
    public void logout() {
        Hawk.deleteAll();
        act().finish();
        GoTo.start(LoginFragment.class);
    }
}
