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
        return Subs.get(this, Manager.get().login(phone, password));
    }

    @Override
    protected Subscription register(String phone, String captcha) {
        return super.register(phone, captcha);
    }

    @Override
    protected Subscription captcha(String phone) {
        return super.captcha(phone);
    }

    @Override
    protected Subscription reset(String password, String checkPassword) {
        return super.reset(password, checkPassword);
    }

    @Override
    protected Class<?> goTo(LoginModel data) {
        return MainActivity.class;
    }
}
