package com.zhy.wlthd;

import base.BResponse;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface WLTApi {
    @POST("pub/login")
    Observable<BResponse<WLTLoginModel>> login(@Body RequestBody body);
}
