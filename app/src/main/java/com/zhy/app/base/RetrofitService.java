package com.zhy.app.base;



import com.zhy.app.modules.test.TestModel;

import java.util.Map;

import base.BList;
import base.BResponse;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface RetrofitService {
    @GET("app/infoNews/homeNewsList")
    Observable<BResponse<BList<TestModel>>> getNewsList(@QueryMap Map<String, Object> map);
}
