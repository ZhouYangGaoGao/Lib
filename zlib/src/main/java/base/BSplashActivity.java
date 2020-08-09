package base;


import android.view.View;
import android.widget.RelativeLayout;

import com.zhy.android.R;

import custom.TextView;
import hawk.Hawk;
import util.GoTo;
import util.StatusBarUtil;
import util.MTimer;

public class BSplashActivity extends BActivity<Object, BPresenter<BView<?>>> {

    protected int centerIconRes = R.drawable.ic_welcomm;
    protected int bgRes;
    protected Class<?> loginCls = BLoginFragment.class, homeCls = BHomeActivity.class;
    protected View otherView;
    protected TextView centerTv;
    protected RelativeLayout mRootView;
    protected int delay = 17;

    {
        contentViewId = R.layout.layout_empty;
        statusBarColor = 0xffffffff;
    }

    @Override
    public void initView() {
        centerTv = findViewById(R.id.tv_empty);
        mRootView = findViewById(R.id.mRootView);
        if (bgRes != 0) mRootView.setBackgroundResource(bgRes);
        if (otherView != null) mRootView.addView(otherView);
        centerTv.setTopRes(centerIconRes);
        centerTv.setText("欢迎页");
        StatusBarUtil.setTransparentForImageView(this, null);
        MTimer.timer(delay).subscribe(aLong -> {
            if (Hawk.get(BConfig.LOGIN) == null)
                startLogin();
            else
                startHome();
        });
    }

    protected void startHome() {
        GoTo.start(homeCls);
        finish();
    }

    protected void startLogin() {
        GoTo.start(loginCls);
        finish();
    }
}
