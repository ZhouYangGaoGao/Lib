package com.zhy.app.base;


import com.zhy.app.modules.login.model.LoginModel;
import com.zhy.app.modules.test.NewsModel;

import base.BConfig;
import base.BList;
import base.BManager;
import rx.Observable;
import util.BodyMap;
import util.MD5Util;
import util.RetrofitHelper;

public class Manager extends BManager<Api> {

    public Observable<BaseBean<LoginModel>> login(String phone, String password) {
        return service.login(new BodyMap("phone", phone)
                .add("client", BConfig.getConfig().getClient()).body("password", MD5Util.MD5(password)));
    }

    public Observable<BaseBean<BList<NewsModel>>> getNewsList() {
        return service.getNewsList(new BodyMap("regionCode", "POINT(" + 117.5334 + " " + 31.3434324 + ")")
                .add("newsType", "xw")
                .add("pageSize", 10)
                .add("pageNum", 1)
        );
    }

    public static Manager get(){
        return RetrofitHelper.get(Manager.class);
    }
}
