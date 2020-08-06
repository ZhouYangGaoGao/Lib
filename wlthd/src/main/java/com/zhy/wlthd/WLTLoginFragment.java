package com.zhy.wlthd;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;

import com.google.gson.Gson;

import base.BConfig;
import base.BLoginFragment;
import rx.Subscription;
import util.GoTo;
import util.MDrawable;
import util.ScreenUtils;

/**
 * @author ZhouYang
 * @describe
 * @date 2020/6/9  11:45 AM
 * - generate by MvpAutoCodePlus plugin.
 */

public class WLTLoginFragment extends BLoginFragment<WLTLoginModel> {

    @Override
    public void afterView() {
        titleView.initTextColor(0xffffffff,0);
        titleView.topContent.setBackgroundColor(0x00ffffff);
        titleView.centerTextView.setText("用户登录");
        titleView.centerTextView.setGravity(Gravity.CENTER_VERTICAL);
        captchaStr="123456";
        register.setVisibility(View.GONE);
        forget.setVisibility(View.VISIBLE);
        Drawable tag = MDrawable.tag(0x88ffffff, 2);
        phone.setBackground(tag);
        password.setBackground(tag);
        captcha.setBackground(tag);
        password.setBackground(tag);
        checkPassword.setBackground(tag);
        editContent.setPadding(0, ScreenUtils.dip2px(-5),0,ScreenUtils.dip2px(-5));
    }

    @Override
    protected Subscription login(String phone, String password) {
        return new WLTSub<WLTLoginModel>(this,WLTManager.get().login(phone, password)) {
            @Override
            public void onSuccess(WLTLoginModel o) {
                BConfig.get().setToken(o.getToken());
                GoTo.start(WLTMainActivity.class, new Intent().putExtra(BConfig.URL, WLTConstant.url + new Gson().toJson(o)));
                success(o);
            }
        };
    }

    @Override
    protected void captchaSuccess() {
        initReset();
    }

    @Override
    protected Subscription captcha(String phone) {
        return super.captcha(phone);
    }

    @Override
    protected Subscription reset(String password, String checkPassword) {
        return super.reset(password, checkPassword);
    }
}

