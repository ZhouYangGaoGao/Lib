package com.zhy.wlthd;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;

import com.google.gson.Gson;

import base.BConfig;
import base.BLoginFragment;
import base.BSub;
import custom.SmartView;
import custom.TextView;
import rx.Subscription;
import util.GoTo;
import util.MD5Util;
import util.MDrawable;
import util.ScreenUtils;
import util.layout.LLParams;

/**
 * @author ZhouYang
 * @describe
 * @date 2020/6/9  11:45 AM
 * - generate by MvpAutoCodePlus plugin.
 */

public class WLTLoginFragment extends BLoginFragment {
    private String phoneToken;

    @Override
    public void afterView() {
        titleView.initTextColor(0xffffffff, 0);
        titleView.topContent.setBackgroundColor(0x00ffffff);
        titleView.centerTextView.setText("用户登录");
        titleView.centerTextView.setGravity(Gravity.CENTER_VERTICAL);
        titleView.setListener(this, 1);
        register.setVisibility(View.GONE);
        centerContent.setVisibility(View.VISIBLE);
        TextView help = new TextView(" 帮助").setLeftRes(R.drawable.ic_help);
        help.setTextColor(Color.WHITE);
        help.setOnClickListener(v -> {

        });
        centerContent.addView(help, 0, LLParams.WW().weight(1));
        Drawable tag = MDrawable.tag(0x88ffffff, 2);
        phone.setBackground(tag);
        password.setBackground(tag);
        captcha.setBackground(tag);
        password.setBackground(tag);
        checkPassword.setBackground(tag);
        topContent.setPadding(0, ScreenUtils.dip2px(-5), 0, ScreenUtils.dip2px(-5));
    }

    @Override
    public void onClick(SmartView smartView, int textViewIndex, int drawableIndex) {

        if (smartView.equals(titleView)) {
            switch (mode) {
                case BConfig.LOGIN_MODE_LOGIN:
                    break;
                case BConfig.LOGIN_MODE_CAPTCHA:
                    initLogin(false);
                    centerContent.setVisibility(View.VISIBLE);
                    break;
                case BConfig.LOGIN_MODE_RESET:
                    initCaptcha();
                    break;

            }
            return;
        }
        super.onClick(smartView, textViewIndex, drawableIndex);

    }

    @Override
    protected Subscription login(String phone, String password) {
        return new BSub<WLTLoginModel>(this, WLTManager.get().login(phone, password)) {
            @Override
            public void onSuccess(WLTLoginModel o) {
                BConfig.get().setToken(o.getToken());
                GoTo.start(WLTHomeActivity.class, new Intent().putExtra(BConfig.URL, WLTConstant.url + new Gson().toJson(o)));
                success(o);
            }
        };
    }

    @Override
    protected Subscription sendCaptcha(String phone) {
        return new BSub<Integer>(this, WLTManager.api().sendPhoneCode(phone)) {
            @Override
            public void onSuccess(Integer integer) {
                toast("验证码已发送");
            }
        };
    }

    @Override
    protected Subscription checkCaptcha(String phone, String captcha) {
        return new BSub<String>(this, WLTManager.api().checkPhoneCode(phone, captcha)) {
            @Override
            public void onSuccess(String s) {
                phoneToken = s;
                toast("验证成功");
                initReset();
            }
        };
    }

    @Override
    protected Subscription reset(String phone, String password) {
        return new BSub<Boolean>(this, WLTManager.api().reset(phone, phoneToken, MD5Util.MD5(password))) {
            @Override
            public void onSuccess(Boolean aBoolean) {
                toast("密码已重设");
                initLogin(false);
            }
        };
    }
}

