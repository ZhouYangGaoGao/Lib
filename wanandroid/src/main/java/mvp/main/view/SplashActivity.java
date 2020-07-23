package mvp.main.view;

import base.BSplashActivity;
import mvp.login.view.LoginFragment;

public class SplashActivity extends BSplashActivity {

    @Override
    public void beforeView() {
        homeCls = HomeActivity.class;
        loginCls = LoginFragment.class;
        delay = 1000;
    }
}
