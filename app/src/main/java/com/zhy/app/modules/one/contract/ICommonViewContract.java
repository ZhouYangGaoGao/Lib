package com.zhy.app.modules.one.contract;

import com.zhy.app.modules.one.model.CommonViewModel;

import base.BView;
import base.IPresenter;
import base.IModel;

/**
 * @author ZhouYang
 * @describe
 * @date 2020/6/15  11:47 AM
 * - generate by MvpAutoCodePlus plugin.
 */

public interface ICommonViewContract {
    interface View extends BView<CommonViewModel> {
    }

    interface Presenter extends IPresenter {
    }

    interface Model extends IModel {
    }
}
