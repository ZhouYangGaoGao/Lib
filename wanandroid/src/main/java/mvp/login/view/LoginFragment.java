package mvp.login.view;

import android.view.View;

import base.BConfig;
import base.BLoginFragment;
import base.BSub;
import base.BaseApp;
import base.Manager;
import mvp.login.model.LoginModel;
import mvp.main.view.HomeActivity;
import rx.Subscription;

public class LoginFragment extends BLoginFragment<LoginModel> {
    @Override
    protected Subscription login(String phone, String password) {
        return BSub.get(this, Manager.get().login(phone, password));
    }

    @Override
    public void afterView() {
        if (mode.equals(BConfig.LOGIN_MODE_REGISTER)) {
            captcha.setVisibility(View.GONE);
            password.setVisibility(View.VISIBLE);
            checkPassword.setVisibility(View.VISIBLE);
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkPassword.actionErrorCheck()) return;
                    BSub.get(LoginFragment.this, Manager.get().register(phone.getText(), password.getText(), checkPassword.getText()));
                }
            });
        }
    }

    @Override
    protected Class<?> goTo(LoginModel data) {
        return HomeActivity.class;
    }

    @Override
    public void success(LoginModel data) {
        data.setPassword(password.getText());
        BaseApp.app().setUser(data);
        super.success(data);
    }
}
