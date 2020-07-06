package com.zhy.app.modules.one.view;

import android.content.Intent;
import android.view.View;
import android.widget.Switch;

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
import util.GoTo;

/**
 * @author ZhouYang
 * @describe
 * @date 2020/6/15  11:47 AM
 * - generate by MvpAutoCodePlus plugin.
 */

public class SmartViewFragment extends BFragment<CommonViewModel, CommonViewPresenter> implements ICommonViewContract.View {
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

    @Override
    public void beforeView() {
        contentViewId = R.layout.fragment_commonview;
    }

    @Override
    public void afterView() {
        topView.rightTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPassword.actionErrorCheck()) return;
                toast(phone.getText() + "\n" + captcha.getText() + "\n" + password.getText());
            }
        });
        searchView.rightTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BApp.app().logout();
            }
        });

        testView.leftTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoTo.start(LoginFragment.class, new Intent().putExtra(BConfig.LOGIN_MODE, BConfig.LOGIN_MODE_RESET));
            }
        });
        testView.centerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoTo.start(LoginFragment.class, new Intent().putExtra(BConfig.LOGIN_MODE, BConfig.LOGIN_MODE_CAPTCHA));
            }
        });
        testView.rightTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoTo.start(LoginFragment.class, new Intent().putExtra(BConfig.LOGIN_MODE, BConfig.LOGIN_MODE_REGISTER));
            }
        });
    }
}


