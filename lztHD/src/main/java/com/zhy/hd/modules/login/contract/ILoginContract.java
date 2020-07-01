package com.zhy.hd.modules.login.contract;

import com.zhy.hd.modules.login.model.LoginModel;

import base.BView;
import base.IPresenter;
import base.IModel;

/**
 * @author ZhouYang
 * @describe
 * @date 2020/6/9  11:45 AM
 * - generate by MvpAutoCodePlus plugin.
 */

public interface ILoginContract {
    interface View extends BView<LoginModel> {
    }

    interface Presenter extends IPresenter {
      void login(String phone,String psw);
    }

    interface Model extends IModel {
    }
}
