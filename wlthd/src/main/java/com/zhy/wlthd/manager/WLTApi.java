package com.zhy.wlthd.manager;

import com.zhy.wlthd.bean.Check;
import com.zhy.wlthd.bean.News;
import com.zhy.wlthd.bean.Permission;
import com.zhy.wlthd.bean.User;

import java.util.List;

import base.BConfig;
import base.BList;
import base.BResponse;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface WLTApi {
    @POST("pub/login")
    Observable<BResponse<User>> login(@Body RequestBody body);

    @POST("pub/findPWD/sendPhoneCode")
    Observable<BResponse<Integer>> sendPhoneCode(@Query(BConfig.PHONE) String phone);

    @POST("pub/findPWD/checkPhoneCode")
    Observable<BResponse<String>> checkPhoneCode(@Query(BConfig.PHONE) String phone, @Query(BConfig.PHONE_CODE) String phoneCode);

    @POST("pub/findPWD/updatePasswd")
    Observable<BResponse<Boolean>> reset(@Query(BConfig.PHONE) String phone, @Query("phoneToken") String phoneToken, @Query("newPasswd") String newPasswd);

    @GET("user/userPermission")
    Observable<BResponse<List<Permission>>> getRolePermission();

    @GET("base/notice/indexNotice")
    Observable<BResponse<News>> indexNotice(@Query(BConfig.TYPE)String type);

    @GET("base/taskPlan/taskPlanList")
    Observable<BResponse<List<Check>>> taskPlanList(@Query("year")String year);
}
