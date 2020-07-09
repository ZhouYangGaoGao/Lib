package com.zhy.hd;


import base.BConfig;
import base.BManager;
import rx.Observable;
import util.BodyMap;
import util.MD5Util;
import util.RetrofitHelper;

public class Manager extends BManager<RetrofitService> {

    public Observable<BaseBean<LoginModel>> login(String phone, String password) {
        return service.login(new BodyMap(Constant.PHONE, phone)
                .add(Constant.CLIENT, BConfig.getConfig().getClient()).body(Constant.PASSWORD, MD5Util.MD5(password)));
    }

    public static Manager get(){
        return RetrofitHelper.get(Manager.class);
    }
}
