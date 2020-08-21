package com.zhy.wlthd;

import base.BConfig;
import base.BResponse;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface WLTApi {
    @POST("pub/login")
    Observable<BResponse<WLTLoginModel>> login(@Body RequestBody body);

    @POST("pub/findPWD/sendPhoneCode")
    Observable<BResponse<Integer>> sendPhoneCode(@Query(BConfig.PHONE) String phone);

    @POST("pub/findPWD/checkPhoneCode")
    Observable<BResponse<String>> checkPhoneCode(@Query(BConfig.PHONE) String phone, @Query(BConfig.PHONE_CODE) String phoneCode);

    @POST("pub/findPWD/updatePasswd")
    Observable<BResponse<Boolean>> reset(@Query(BConfig.PHONE) String phone, @Query("phoneToken") String phoneToken, @Query("newPasswd") String newPasswd);
}
