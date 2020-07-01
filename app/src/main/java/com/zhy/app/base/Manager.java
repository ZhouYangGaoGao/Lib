package com.zhy.app.base;


import com.zhy.app.modules.test.TestModel;

import base.BList;
import base.BManager;
import base.BResponse;
import rx.Observable;
import util.BodyMap;

public class Manager extends BManager<RetrofitService> {

    public Observable<BResponse<BList<TestModel>>> getNewsList() {
        return service.getNewsList(new BodyMap("regionCode", "POINT(" + 117.5334 + " " + 31.3434324 + ")")
                .add("newsType", "xw")
                .add("pageSize", 10)
                .add("pageNum", 1)
        );
    }

    private Manager() {
    }

    public static Manager getManager() {
        if (manager == null) manager = new Manager();
        return (Manager) manager;
    }
}
