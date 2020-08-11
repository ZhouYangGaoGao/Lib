package mvp.main.view;

import android.widget.RelativeLayout;

import base.BSplashActivity;
import custom.StatusView;
import mvp.login.view.LoginFragment;
import util.layoutparams.RLParams;

public class SplashActivity extends BSplashActivity {

    @Override
    public void beforeView() {
        homeCls = HomeActivity.class;
        loginCls = LoginFragment.class;
        delay = 2000;
        otherView = new StatusView(this).loading("BallScaleRippleIndicator");
        otherView.setLayoutParams(RLParams.WW().rule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.CENTER_HORIZONTAL));
    }
}
