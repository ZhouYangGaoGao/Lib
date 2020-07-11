package base;


import java.util.List;
import java.util.Map;

import mvp.chapter.model.Article;
import mvp.chapter.model.Chapter;
import mvp.home.model.Banner;
import mvp.login.model.LoginModel;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface Api {

    @FormUrlEncoded
    @POST("user/login")
    Observable<BaseBean<LoginModel>> login(@FieldMap Map<String, Object> map);

    @POST("user/register")
    Observable<BaseBean<LoginModel>> register(@QueryMap Map<String, Object> body);

    @GET("user/logout/json")
    Observable<BaseBean<Object>> logout();

    @GET("article/list/{page}/json")
    Observable<BaseBean<MList<Article>>> list(@Path(BConfig.PAGE) int page);

    @GET("article/listproject/{page}/json")
    Observable<BaseBean<MList<Article>>> projects(@Path(BConfig.PAGE) int page);

    @GET("wxarticle/chapters/json")
    Observable<BaseBean<List<Chapter>>> chapters();

    @GET("banner/json")
    Observable<BaseBean<List<Banner>>> banner();

    @GET("wxarticle/list/{id}/{page}/json")
    Observable<BaseBean<MList<Article>>> article(@Path("id") int id, @Path("page") int page);

}
