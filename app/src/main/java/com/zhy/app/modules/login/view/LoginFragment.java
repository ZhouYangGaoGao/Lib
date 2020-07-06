package com.zhy.app.modules.login.view;

import com.zhy.app.MainActivity;
import com.zhy.app.base.Manager;
import com.zhy.app.modules.login.model.LoginModel;

import base.BLoginFragment;
import rx.Subscription;
import util.Subs;

public class LoginFragment extends BLoginFragment<LoginModel> {

    @Override
    protected Subscription login(String phone, String password) {
        return new Subs<>(this, Manager.get().login(phone, password));
    }

    @Override
    protected Class goTo(LoginModel data) {
        return MainActivity.class;
    }
}
