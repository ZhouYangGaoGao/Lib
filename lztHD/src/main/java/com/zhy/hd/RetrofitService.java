package com.zhy.hd;


import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface RetrofitService {
    @POST("pub/login")
    Observable<BResponse<LoginModel>> login(@Body RequestBody body);
}
