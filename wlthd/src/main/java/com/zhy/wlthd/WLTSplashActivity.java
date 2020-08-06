package com.zhy.wlthd;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

import base.BSplashActivity;

public class WLTSplashActivity extends BSplashActivity {

    @Override
    public void beforeView() {
        homeCls = WLTMainActivity.class;
        bgRes = R.mipmap.bg_welcome;
        otherView = getView(R.layout.layout_login);
        delay = 1000;
    }

    @Override
    public void initView() {
        super.initView();
        centerTv.setVisibility(View.GONE);
        otherView.setAlpha(0);
    }

    @Override
    protected void startLogin() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(otherView, "alpha", 0f, 1f),
                ObjectAnimator.ofFloat(otherView, "scaleX", 0.3f, 1f),
                ObjectAnimator.ofFloat(otherView, "scaleY", 0.3f, 1f));
        set.setDuration(2000).start();
    }
}
