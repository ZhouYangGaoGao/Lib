package base;

import java.lang.reflect.ParameterizedType;

import retrofit2.http.GET;
import rx.Observable;
import util.Reflector;
import util.RetrofitHelper;

public class BManager<S> {
    protected S service;

    protected BManager() {
        try {
            // 通过反射获取RetrofitService的真实类型
            ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
            Class sClass = (Class) pt.getActualTypeArguments()[0];
            service = (S) RetrofitHelper.get(sClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BManager get() {
        return RetrofitHelper.get(BManager.class);
    }

    public static Observable<Object> test() {
        return RetrofitHelper.get(TestApi.class).test();
    }

    public static <S> S api() {
        return (S) get().service;
    }

}
