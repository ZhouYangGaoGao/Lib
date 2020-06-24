package com.zhy.test.base;


import com.zhy.test.modules.login.model.LoginModel;
import com.zhy.test.util.Constant;

import base.BConfig;
import base.BList;
import base.BResponse;
import rx.Observable;
import util.BodyMap;
import util.MD5Util;
import util.RetrofitHelper;

public class Manager {
    public Observable<BResponse<LoginModel>> login(String phone, String password) {
        return service.login(new BodyMap(Constant.PHONE, phone)
                .add(Constant.CLIENT, BConfig.getConfig().getClient()).body(Constant.PASSWORD, MD5Util.MD5(password)));
    }

    public Observable<BResponse<BList<Object>>> getNewsList() {
        return service.getNewsList(new BodyMap("regionCode", "POINT(" + 117.5334 + " " + 31.3434324 + ")")
                .add("newsType", "xw")
                .add("pageSize", 10)
                .add("pageNum", 1)
        );
    }

    private RetrofitService service;
    private static Manager manager;

    private Manager() {
        this.service = RetrofitHelper.getInstance(BConfig.getConfig().getBaseUrl())
                .getServer(RetrofitService.class);
    }

    public static Manager getManager() {
        if (manager == null) manager = new Manager();
        return manager;
    }
}
