package com.zhy.hd.modules.login.presenter;

import com.zhy.hd.base.Manager;
import com.zhy.hd.modules.login.contract.ILoginContract;

import base.BPresenter;
import util.RetrofitHelper;
import util.Subs;

/**
 * @author ZhouYang
 * @describe
 * @date 2020/6/9  11:45 AM
 * - generate by MvpAutoCodePlus plugin.
 */

public class LoginPresenter extends BPresenter<ILoginContract.View> implements ILoginContract.Presenter {

    @Override
    public void login(String phone, String psw) {
        sub(Subs.get(mView, RetrofitHelper.get(Manager.class).login(phone, psw)));
    }
}

