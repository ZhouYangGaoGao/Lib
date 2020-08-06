package com.zhy.app.base;

import com.zhy.app.modules.login.model.LoginModel;
import com.zhy.app.modules.login.view.LoginFragment;

import base.BApp;
import base.BConfig;
import hawk.Hawk;
import util.GoTo;

public class BaseApp extends BApp {

    @Override
    public void logout() {
        Hawk.deleteAll();
        GoTo.start(LoginFragment.class);
    }

    @Override
    protected void initApp() {
        LoginModel user = Hawk.get(BConfig.LOGIN);
        BConfig.get().setBaseUrl("https://www.ahlzz.com/api/")
                .setClient("cms")
                .setToken(user==null?"0":(user.getToken()+""));
    }
}
