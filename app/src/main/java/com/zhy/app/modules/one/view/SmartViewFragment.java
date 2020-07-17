package com.zhy.app.modules.one.view;

import android.content.Intent;

import com.zhy.app.R;
import com.zhy.app.modules.login.view.LoginFragment;
import com.zhy.app.modules.one.contract.ICommonViewContract;
import com.zhy.app.modules.one.model.CommonViewModel;
import com.zhy.app.modules.one.presenter.CommonViewPresenter;

import base.BApp;
import base.BConfig;
import base.BFragment;
import butterknife.BindView;
import custom.SmartView;
import listener.SmartListener;
import util.GoTo;

/**
 * @author ZhouYang
 * @describe
 * @date 2020/6/15  11:47 AM
 * - generate by MvpAutoCodePlus plugin.
 */

public class SmartViewFragment extends BFragment<CommonViewModel, CommonViewPresenter> implements ICommonViewContract.View, SmartListener {
    @BindView(R.id.searchView)
    SmartView searchView;
    @BindView(R.id.phone)
    SmartView phone;
    @BindView(R.id.nameView)
    SmartView nameView;
    @BindView(R.id.captcha)
    SmartView captcha;
    @BindView(R.id.password)
    SmartView password;
    @BindView(R.id.topView)
    SmartView topView;
    @BindView(R.id.testView)
    SmartView testView;
    @BindView(R.id.checkPassword)
    SmartView checkPassword;
    @BindView(R.id.clickView)
    SmartView clickView;

    @Override
    public void beforeView() {
        contentViewId = R.layout.fragment_commonview;
    }

    /**
     * SmartBar点击事件
     *
     * @param textView 0:leftTextView 1:centerTextView 2:rightTextView
     * @param drawable 0:左图 1:上图 2:右图 3:下图 -1:文字
     */
    @Override
    public void onClick(SmartView smartView, int textView, int drawable) {
        switch (smartView.getId()) {
            case R.id.topView:
                if (checkPassword.actionErrorCheck()) return;
                toast(phone.getText() + "\n" + captcha.getText() + "\n" + password.getText());
                break;
            case R.id.searchView:
                BApp.app().logout();
                break;
            case R.id.clickView:
                toast("smartView=" + "clickView" + "\ntextView=" + textView + "\ndrawable=" + drawable);
                break;
            case R.id.captcha:
                toast("发送验证码");
                break;
            case R.id.testView:
                switch (textView) {
                    case 0:
                        GoTo.start(LoginFragment.class, new Intent().putExtra(BConfig.LOGIN_MODE, BConfig.LOGIN_MODE_RESET));
                        break;
                    case 1:
                        GoTo.start(LoginFragment.class, new Intent().putExtra(BConfig.LOGIN_MODE, BConfig.LOGIN_MODE_CAPTCHA));
                        break;
                    case 2:
                        GoTo.start(LoginFragment.class, new Intent().putExtra(BConfig.LOGIN_MODE, BConfig.LOGIN_MODE_REGISTER));
                        break;
                }
                break;
        }
    }

    @Override
    public void afterView() {
        clickView.setListener(this, 1, 2);
        topView.setListener(this, 2);
        searchView.setListener(this, 2);
        captcha.setListener(this, 2);
        testView.setListener(this);
    }


}


