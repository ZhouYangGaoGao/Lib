package com.zhy.hd;

import android.content.Intent;
import android.view.View;

import com.google.gson.Gson;

import base.BConfig;
import base.BLoginFragment;
import rx.Subscription;
import util.GoTo;

/**
 * @author ZhouYang
 * @describe
 * @date 2020/6/9  11:45 AM
 * - generate by MvpAutoCodePlus plugin.
 */

public class LZTLoginFragment extends BLoginFragment<LZTLoginModel> {

    @Override
    public void afterView() {
        titleView.topContent.setVisibility(View.GONE);
        register.setVisibility(View.GONE);
    }

    @Override
    protected Subscription login(String phone, String password) {
        return new LZTSub<LZTLoginModel>(LZTManager.get().login(phone, password)) {
            @Override
            public void onSuccess(LZTLoginModel o) {
                BConfig.get().setToken(o.getToken());
                GoTo.start(LZTMainActivity.class, new Intent().putExtra(LZTConstant.URL, LZTConstant.url + new Gson().toJson(o)));
                success(o);
            }
        };
    }

}

