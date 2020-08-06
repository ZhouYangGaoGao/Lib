package com.zhy.hd;


import base.BConfig;
import base.BManager;
import base.BResponse;
import rx.Observable;
import util.MMap;
import util.MD5Util;
import util.RetrofitHelper;

public class LZTManager extends BManager<LZTApi> {

    public Observable<BResponse<LZTLoginModel>> login(String phone, String password) {
        return service.login(new MMap(BConfig.PHONE, phone)
                .add(BConfig.CLIENT, BConfig.get().getClient())
                .body(BConfig.PASSWORD, MD5Util.MD5(password)));
    }

    public static LZTManager get(){
        return RetrofitHelper.get(LZTManager.class);
    }
}
