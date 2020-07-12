package mvp.main.view;

import base.BSplashActivity;
import base.TestSub;
import mvp.login.view.LoginFragment;
import mvp.main.HomeActivity;

public class SplashActivity extends BSplashActivity {

    @Override
    public void beforeView() {
        homeCls= HomeActivity.class;
        loginCls= LoginFragment.class;
        delay=1000;
    }

    @Override
    public void afterView() {
//        presenter.sub(new TestSub());
    }
}
