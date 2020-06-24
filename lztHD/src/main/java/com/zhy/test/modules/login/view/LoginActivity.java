package com.zhy.test.modules.login.view;

import android.widget.Button;

import com.google.gson.Gson;
import com.zhy.test.R;
import com.zhy.test.modules.login.contract.ILoginContract;
import com.zhy.test.modules.login.model.LoginModel;
import com.zhy.test.modules.login.presenter.LoginPresenter;
import com.zhy.test.modules.main.MainActivity;
import com.zhy.test.util.Constant;

import base.BActivity;
import base.BConfig;
import butterknife.BindView;
import butterknife.OnClick;
import custom.SmartView;
import hawk.Hawk;

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
        presenter = new LoginPresenter();
        contentView = R.layout.activity_login;
    }

    @Override
    public void initView() {
        go(Hawk.get(Constant.LOGIN));
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
            start(MainActivity.class, "https://www.ahlzz.com/web/commandCenter?user=" + new Gson().toJson(data));
            finish();
        }
    }

    @Override
    public void completed() {
        btnLogin.setEnabled(true);
    }
}

