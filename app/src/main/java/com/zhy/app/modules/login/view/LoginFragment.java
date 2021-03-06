package com.zhy.app.modules.login.view;

import com.zhy.app.MainActivity;
import com.zhy.app.base.Manager;
import com.zhy.app.modules.login.model.LoginModel;

import base.BConfig;
import base.BLoginFragment;
import base.BSub;
import rx.Subscription;

public class LoginFragment extends BLoginFragment<LoginModel> {

    @Override
    protected Subscription login(String phone, String password) {
        return new BSub<LoginModel>(this, Manager.get().login(phone, password)){
            @Override
            public void onSuccess(LoginModel loginModel) {
                BConfig.get().setToken(loginModel.getToken());
                success(loginModel);
            }
        };
    }

    @Override
    protected Subscription register(String phone, String captcha) {
        return BSub.get(this, Manager.get().login(phone, captcha));
    }

    @Override
    protected Subscription sendCaptcha(String phone) {
        return new BSub<LoginModel>(this,Manager.get().login(phone, "123456")) {
            @Override
            public void onSuccess(LoginModel loginModel) {
                toast(toast + "  " + captcha.getText());
                captchaStr = "123456";
            }
        };
    }

    @Override
    protected Subscription reset(String password, String checkPassword) {
        return BSub.get(this, Manager.get().login(password, checkPassword));
    }

    @Override
    protected Class<?> goTo(LoginModel data) {
        switch (mode) {
            case BConfig.LOGIN_MODE_CAPTCHA:
                return LoginFragment.class;
            default:
                return MainActivity.class;
        }
    }
}
