package com.zhy.wlthd.manager;


import com.zhy.wlthd.bean.User;

import base.BConfig;
import base.BManager;
import base.BResponse;
import rx.Observable;
import util.MD5Util;
import util.MMap;
import util.RetrofitHelper;

public class WLTManager extends BManager<WLTApi> {

    public Observable<BResponse<User>> login(String phone, String password) {
        return service.login(new MMap("loginAccount", phone)
                .body(BConfig.PASSWORD, MD5Util.MD5(password)));
    }

    public static WLTManager get(){
        return RetrofitHelper.get(WLTManager.class);
    }
    public static WLTApi api() {
        return get().service;
    }
}
