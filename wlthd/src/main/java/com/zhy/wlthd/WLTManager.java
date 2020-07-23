package com.zhy.wlthd;


import base.BConfig;
import base.BManager;
import rx.Observable;
import util.MD5Util;
import util.MMap;
import util.RetrofitHelper;

public class WLTManager extends BManager<WLTApi> {

    public Observable<WLTBean<WLTLoginModel>> login(String phone, String password) {
        return service.login(new MMap(WLTConstant.PHONE, phone)
                .add(WLTConstant.CLIENT, BConfig.get().getClient()).body(WLTConstant.PASSWORD, MD5Util.MD5(password)));
    }

    public static WLTManager get(){
        return RetrofitHelper.get(WLTManager.class);
    }
}
