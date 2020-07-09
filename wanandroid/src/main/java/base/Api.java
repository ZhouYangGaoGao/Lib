package base;


import java.util.Map;

import mvp.login.model.LoginModel;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface Api {

    @FormUrlEncoded
    @POST("user/login")
    Observable<BaseBean<LoginModel>> login(@FieldMap Map<String, Object> map);

    @POST("user/register")
    Observable<BaseBean<LoginModel>> register(@QueryMap Map<String,Object> body);

    @GET("user/logout/json")
    Observable<BaseBean<Object>> logout();

}
