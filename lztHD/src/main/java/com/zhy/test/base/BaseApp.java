package com.zhy.test.base;

import android.content.pm.ActivityInfo;

import com.zhy.test.modules.login.model.LoginModel;
import com.zhy.test.modules.login.view.LoginActivity;
import com.zhy.test.util.Constant;

import base.BActivity;
import base.BApp;
import base.BConfig;
import hawk.Hawk;

public class BaseApp extends BApp {
    @Override
    public void onCreate() {
        super.onCreate();
        LoginModel loginModel = Hawk.get(Constant.LOGIN);
        BConfig.getConfig().setBaseUrl("https://www.ahlzz.com/api/")
                .setClient("cms")
                .setFullScreen(true)
                .setToken(loginModel == null ? "0" : loginModel.getToken())
                .setOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    public void logout(){
        Hawk.deleteAll();
        BActivity.start(LoginActivity.class);
    }
}
