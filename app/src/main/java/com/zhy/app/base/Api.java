package com.zhy.app.base;


import com.zhy.app.modules.login.model.LoginModel;
import com.zhy.app.modules.test.NewsModel;

import java.util.Map;

import base.BList;
import base.BResponse;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface Api {
    @POST("pub/login")
    Observable<BResponse<LoginModel>> login(@Body RequestBody body);

    @GET("app/infoNews/homeNewsList")
    Observable<BResponse<BList<NewsModel>>> getNewsList(@QueryMap Map<String, Object> map);
}
