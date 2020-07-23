package com.zhy.hd;


import base.BConfig;
import base.BManager;
import rx.Observable;
import util.MMap;
import util.MD5Util;
import util.RetrofitHelper;

public class LZTManager extends BManager<LZTApi> {

    public Observable<LZTBean<LZTLoginModel>> login(String phone, String password) {
        return service.login(new MMap(LZTConstant.PHONE, phone)
                .add(LZTConstant.CLIENT, BConfig.get().getClient()).body(LZTConstant.PASSWORD, MD5Util.MD5(password)));
    }

    public static LZTManager get(){
        return RetrofitHelper.get(LZTManager.class);
    }
}
