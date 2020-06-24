package com.zhy.test.base;


import com.zhy.test.modules.login.model.LoginModel;

import java.util.Map;

import base.BList;
import base.BResponse;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface RetrofitService {
    @POST("pub/login")
    Observable<BResponse<LoginModel>> login(@Body RequestBody body);

    @GET("app/infoNews/homeNewsList")
    Observable<BResponse<BList<Object>>> getNewsList(@QueryMap Map<String, Object> map);
}
