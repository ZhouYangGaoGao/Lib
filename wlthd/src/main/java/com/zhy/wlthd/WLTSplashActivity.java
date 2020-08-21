package com.zhy.wlthd;

import android.graphics.Color;
import android.view.Gravity;
import android.widget.RelativeLayout;

import base.BSplashActivity;
import custom.TextView;
import util.AnimatorUtil;
import util.IncludeUtil;
import util.layout.RLParams;

public class WLTSplashActivity extends BSplashActivity {

    private TextView mCompany;

    @Override
    public void beforeView() {
        homeCls = WLTHomeActivity.class;
        centerIconRes = R.mipmap.ic_splash;
        bgRes = R.mipmap.bg_welcome;
        otherView = getView(R.layout.layout_login);
        otherView.setLayoutParams(RLParams.WW().rule(RelativeLayout.CENTER_IN_PARENT));

        mCompany = new TextView(this);
        mCompany.setText("东华（安徽）生态规划院有限公司\ncopyright © 2019\n");
        mCompany.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        mCompany.setTextColor(0xffcccccc);
        mCompany.setBackgroundColor(0x44ffffff);
        mCompany.setTextSize(12);
        mCompany.setLayoutParams(RLParams.MM());
        delay = 1500;
    }

    @Override
    public void afterView() {
        otherView.setAlpha(0);
        mRootView.addView(mCompany, 0);
        mStatusView.getTextView().setEnabled(false);
        mStatusView.empty("互联网+造林 v1.0\n安徽省营造林管理平台");
        mStatusView.getTextView().setTextSize(20);
        IncludeUtil.with(mStatusView.getTextView())
                .addColor("互联网+造林 v1.0", Color.BLACK)
                .addSize("v1.0", 0.7f)
                .addSize("互联网+造林", 1.1f)
                .addSize("安徽省营造林管理平台", 0.85f)
                .setColor("安徽省营造林管理平台", 0xff0C5B60);
    }

    @Override
    protected void startLogin() {
        mStatusView.empty("\n");
        AnimatorUtil.with(mStatusView, 2000)
                .translationX(0, -mStatusView.getLeft() - (int) (mStatusView.getWidth() / 7))
                .translationY(0, -mStatusView.getTop()- (int) (mStatusView.getHeight() / 7))
                .scale(1f, 0.5f)
                .scale(otherView, 0.3f, 1f)
                .alpha(otherView, 0f, 1f)
                .alpha(mCompany, 1f, 0f)
                .playTogether();
    }
}
