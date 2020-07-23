package com.zhy.hd;


import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface LZTApi {
    @POST("pub/login")
    Observable<LZTBean<LZTLoginModel>> login(@Body RequestBody body);
}
