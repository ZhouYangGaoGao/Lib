package com.zhy.test.modules.login.presenter;

import com.zhy.test.base.Manager;
import util.Suber;
import com.zhy.test.modules.login.contract.ILoginContract;
import com.zhy.test.modules.login.model.LoginModel;

import base.BPresenter;

/**
 * @author ZhouYang
 * @describe
 * @date 2020/6/9  11:45 AM
 * - generate by MvpAutoCodePlus plugin.
 */

public class LoginPresenter extends BPresenter<ILoginContract.View> implements ILoginContract.Presenter {

    @Override
    public void login(String phone, String psw) {
        subscribe(new Suber<LoginModel>(mView, Manager.getManager().login(phone, psw)));
    }
}

