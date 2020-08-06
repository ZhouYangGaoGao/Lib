package com.zhy.wlthd;


import base.BConfig;
import base.BManager;
import base.BResponse;
import rx.Observable;
import util.MD5Util;
import util.MMap;
import util.RetrofitHelper;

public class WLTManager extends BManager<WLTApi> {

    public Observable<BResponse<WLTLoginModel>> login(String phone, String password) {
        return service.login(new MMap(BConfig.PHONE, phone)
                .add(BConfig.CLIENT, BConfig.get().getClient()).body(BConfig.PASSWORD, MD5Util.MD5(password)));
    }

    public static WLTManager get(){
        return RetrofitHelper.get(WLTManager.class);
    }
}
