package com.zhy.app.modules.test;


import com.zhy.app.MainActivity;
import com.zhy.app.modules.login.view.LoginFragment;

import base.BSplashActivity;

public class SplashActivity extends BSplashActivity {
    @Override
    public void beforeView() {
        loginCls = LoginFragment.class;
        homeCls= MainActivity.class;
    }

}
