package base;


import java.util.List;
import java.util.Map;

import mvp.chapter.model.Article;
import mvp.login.model.LoginModel;
import mvp.navigation.model.Navigation;
import mvp.tree.model.Tree;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
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

    @GET("article/top/json")
    Observable<BaseBean<List<Article>>> top();

    @GET("article/list/{page}/json")
    Observable<BaseBean<MList<Article>>> list(@Path(BConfig.PAGE) int page);

    //收藏网站列表
    @GET("lg/collect/usertools/json")
    Observable<BaseBean<MList<Article>>> collectTools();

    //收藏站内文章
    @POST("lg/collect/{id}/json")
    Observable<BaseBean<Object>> collect(@Path(BConfig.ID) int id);

    //收藏站外文章 title，author，link
    @POST("lg/collect/add/json")
    Observable<BaseBean<Object>> collectAdd(@Query(BConfig.TITLE) String title,
                                            @Query("author") String author,
                                            @Query("link") String link);

    //取消收藏 ->文章列表
    @POST("lg/uncollect_originId/{id}/json")
    Observable<BaseBean<Object>> unCollect(@Path(BConfig.ID) int id);

    //取消收藏 ->我的收藏  originId:列表页下发，无则为-1
    @POST("lg/uncollect/{id}/json?originId=-1")
    Observable<BaseBean<Object>> unMyCollect(@Path(BConfig.ID) int id,@Query("originId")int originId);

    //收藏文章列表
    @GET("lg/collect/list/{page}/json")
    Observable<BaseBean<MList<Article>>> collectList(@Path(BConfig.PAGE) int page);

    @POST("article/query/{page}/json")
    Observable<BaseBean<MList<Article>>> query(@Path(BConfig.PAGE) int page, @Query("k") String k);

    @GET("article/list/{page}/json?")
    Observable<BaseBean<MList<Article>>> treeList(@Path(BConfig.PAGE) int page, @Query("cid") int cid);

    @GET("project/list/{page}/json")
    Observable<BaseBean<MList<Article>>> projects(@Path(BConfig.PAGE) int page, @Query("cid") int cid);

    @GET("wenda/list/{page}/json")
    Observable<BaseBean<MList<Article>>> qa(@Path(BConfig.PAGE) int page);

    @GET("user_article/list/{page}/json")
    Observable<BaseBean<MList<Article>>> square(@Path(BConfig.PAGE) int page);

    @GET("wxarticle/chapters/json")
    Observable<BaseBean<List<Tree>>> chapters();

    @GET("banner/json")
    Observable<BaseBean<List<Article>>> banner();

    @GET("hotkey/json")
    Observable<BaseBean<List<Article>>> hotKey();

    @GET("friend/json")
    Observable<BaseBean<List<Article>>> friend();

    @GET("navi/json")
    Observable<BaseBean<List<Navigation>>> navigation();

    @GET("tree/json")
    Observable<BaseBean<List<Tree>>> tree();

    @GET("project/tree/json")
    Observable<BaseBean<List<Tree>>> projectTree();

    @GET("wxarticle/list/{id}/{page}/json")
    Observable<BaseBean<MList<Article>>> article(@Path("id") int id, @Path("page") int page);

}
