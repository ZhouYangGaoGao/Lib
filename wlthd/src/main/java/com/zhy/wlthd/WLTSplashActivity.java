package com.zhy.wlthd;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.widget.RelativeLayout;

import base.BSplashActivity;
import util.IncludeUtil;
import util.MLayoutParams;

public class WLTSplashActivity extends BSplashActivity {

    @Override
    public void beforeView() {
        homeCls = WLTMainActivity.class;
        centerIconRes = R.mipmap.ic_logo;
        bgRes = R.mipmap.bg_welcome;
        otherView = getView(R.layout.layout_login);
        RelativeLayout.LayoutParams params = MLayoutParams.marginRLP(0);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        otherView.setLayoutParams(params);
        delay = 55500;
    }

    @Override
    public void initView() {
        super.initView();
        mStatusView.empty("互联网+造林 v1.0\n安徽省营造林管理平台");
//        IncludeUtil.setSize(mStatusView.getTextView(),"互联网+造林", 1.2f);
        IncludeUtil.with(mStatusView.getTextView())
                .addColor("互联网+造林 v1.0", Color.BLACK)
                .addSize("v1.0", 0.9f)
                .addSize("互联网+造林", 1.2f)
                .setColor("安徽省营造林管理平台", 0xff0c5b5f);
        otherView.setAlpha(0);
    }

    @Override
    protected void startLogin() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(otherView, "alpha", 0f, 1f),
                ObjectAnimator.ofFloat(otherView, "scaleX", 0.3f, 1f),
                ObjectAnimator.ofFloat(otherView, "scaleY", 0.3f, 1f),
                ObjectAnimator.ofFloat(mStatusView, "translationX", 0, -mStatusView.getLeft()),
                ObjectAnimator.ofFloat(mStatusView, "translationY", 0, -mStatusView.getTop()),
                ObjectAnimator.ofFloat(mStatusView, "scaleX", 1f, 0.6f),
                ObjectAnimator.ofFloat(mStatusView, "scaleY", 1f, 0.6f));
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mStatusView.empty(" \n ");
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.setDuration(2000).start();
    }
}
