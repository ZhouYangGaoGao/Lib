package com.zhy.hd.modules.login.view;

import android.content.Intent;
import android.widget.Button;

import com.google.gson.Gson;
import com.zhy.hd.R;
import com.zhy.hd.modules.login.contract.ILoginContract;
import com.zhy.hd.modules.login.model.LoginModel;
import com.zhy.hd.modules.login.presenter.LoginPresenter;
import com.zhy.hd.modules.main.MainActivity;
import com.zhy.hd.util.Constant;

import base.BActivity;
import base.BConfig;
import butterknife.BindView;
import butterknife.OnClick;
import custom.SmartView;
import hawk.Hawk;
import util.BundleCreator;
import util.GoTo;

/**
 * @author ZhouYang
 * @describe
 * @date 2020/6/9  11:45 AM
 * - generate by MvpAutoCodePlus plugin.
 */

public class LoginActivity extends BActivity<LoginModel, LoginPresenter> implements ILoginContract.View {

    @BindView(R.id.edt_phone)
    SmartView edtPhone;
    @BindView(R.id.edt_psw)
    SmartView edtPsw;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    public void beforeView() {
        go(Hawk.get(Constant.LOGIN));
        contentView = R.layout.activity_login;
    }

    @Override
    public void initView() {
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        presenter.login(edtPhone.getText(), edtPsw.getText());
        btnLogin.setEnabled(false);
    }

    @Override
    public void success(LoginModel data) {
        toast("登录成功");
        Hawk.put(Constant.LOGIN, data);
        go(data);
    }

    private void go(LoginModel data) {
        if (data != null) {
            BConfig.getConfig().setToken(data.getToken());
            GoTo.start(MainActivity.class, new Intent().putExtra("url",
                    "https://www.ahlzz.com/web/commandCenter?user=" + new Gson().toJson(data)));
            finish();
        }
    }

    @Override
    public void completed() {
        btnLogin.setEnabled(true);
    }
}

