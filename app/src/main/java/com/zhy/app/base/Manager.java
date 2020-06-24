package com.zhy.app.base;


import com.zhy.app.modules.test.TestModel;

import base.BConfig;
import base.BList;
import base.BResponse;
import rx.Observable;
import util.BodyMap;
import util.RetrofitHelper;

public class Manager {

    public Observable<BResponse<BList<TestModel>>> getNewsList() {
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
