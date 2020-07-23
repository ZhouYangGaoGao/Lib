package com.zhy.wlthd;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;

import com.google.gson.Gson;

import base.BConfig;
import base.BLoginFragment;
import rx.Subscription;
import util.GoTo;
import util.MDrawable;

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

        register.setVisibility(View.GONE);

        phone.initTextColor(0xffffffff,0);
        phone.setBackground(MDrawable.tag(0x88ffffff,2));
        phone.line.setVisibility(View.GONE);

        password.initTextColor(0xffffffff,0);
        password.setBackground(MDrawable.tag(0x88ffffff,2));
        password.line.setVisibility(View.GONE);
    }

    @Override
    protected Subscription login(String phone, String password) {
        return new WLTSub<WLTLoginModel>(WLTManager.get().login(phone, password)) {
            @Override
            public void onSuccess(WLTLoginModel o) {
                BConfig.get().setToken(o.getToken());
                GoTo.start(WLTMainActivity.class, new Intent().putExtra(BConfig.URL, WLTConstant.url + new Gson().toJson(o)));
                success(o);
            }
        };
    }

}

