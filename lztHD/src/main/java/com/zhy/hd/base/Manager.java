package com.zhy.hd.base;


import com.zhy.hd.modules.login.model.LoginModel;
import com.zhy.hd.util.Constant;

import base.BConfig;
import base.BManager;
import base.BResponse;
import rx.Observable;
import util.BodyMap;
import util.MD5Util;

public class Manager extends BManager<RetrofitService> {

    public Observable<BResponse<LoginModel>> login(String phone, String password) {
        return service.login(new BodyMap(Constant.PHONE, phone)
                .add(Constant.CLIENT, BConfig.getConfig().getClient()).body(Constant.PASSWORD, MD5Util.MD5(password)));
    }

}
