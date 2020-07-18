package com.zhy.hd;

import android.content.Intent;
import android.view.View;

import com.google.gson.Gson;

import base.BConfig;
import base.BLoginFragment;
import rx.Subscription;
import util.GoTo;

/**
 * @author ZhouYang
 * @describe
 * @date 2020/6/9  11:45 AM
 * - generate by MvpAutoCodePlus plugin.
 */

public class LoginFragment extends BLoginFragment<LoginModel> {

    @Override
    public void afterView() {
        titleView.topContent.setVisibility(View.GONE);
        register.setVisibility(View.GONE);
    }

    @Override
    protected Subscription login(String phone, String password) {
        return new Subs<LoginModel>(Manager.get().login(phone, password)) {
            @Override
            public void onSuccess(LoginModel o) {
                BConfig.get().setToken(o.getToken());
                GoTo.start(MainActivity.class, new Intent().putExtra(Constant.URL, Constant.url+ new Gson().toJson(o)));
                success(o);
            }
        };
    }

}

