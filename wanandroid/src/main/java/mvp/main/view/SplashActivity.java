package mvp.main.view;

import android.widget.RelativeLayout;

import com.wang.avi.AVLoadingIndicatorView;

import base.BSplashActivity;
import custom.StatusView;
import mvp.login.view.LoginFragment;
import util.MLayoutParams;

public class SplashActivity extends BSplashActivity {

    @Override
    public void beforeView() {
        homeCls = HomeActivity.class;
        loginCls = LoginFragment.class;
        delay = 2000;
        otherView = new StatusView(this).loading();
        RelativeLayout.LayoutParams rlp = MLayoutParams.marginRLP(0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
        rlp.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
        otherView.setLayoutParams(rlp);
    }
}
