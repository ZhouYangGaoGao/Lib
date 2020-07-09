package mvp.main.view;

import base.BSplashActivity;
import mvp.login.view.LoginFragment;
import mvp.main.HomeActivity;

public class SplashActivity extends BSplashActivity {

    @Override
    public void beforeView() {
        homeCls= HomeActivity.class;
        loginCls= LoginFragment.class;
    }
}
