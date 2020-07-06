package com.zhy.hd.base;

import com.zhy.hd.modules.login.model.LoginModel;
import com.zhy.hd.modules.login.view.LoginActivity;
import com.zhy.hd.util.Constant;

import base.BApp;
import base.BConfig;
import hawk.Hawk;
import util.GoTo;

public class BaseApp extends BApp {

    @Override
    public void logout(){
        Hawk.deleteAll();
        GoTo.start(LoginActivity.class);
    }

    @Override
    protected void initApp() {
        LoginModel loginModel = Hawk.get(Constant.LOGIN);
        BConfig.getConfig().setBaseUrl("https://www.ahlzz.com/api/")
                .setClient("cms")
                .setFullScreen(true)
                .setBugLy("5c1d24a237")
                .setToken(loginModel == null ? "0" : loginModel.getToken());
    }
}
