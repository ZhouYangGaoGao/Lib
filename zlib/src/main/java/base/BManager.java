package base;

import java.lang.reflect.ParameterizedType;

import util.RetrofitHelper;

public class BManager<S> {
    protected S service;
    private static Class aClass;

    protected BManager() {
        aClass = this.getClass();
        try {
            // 通过反射获取RetrofitService的真实类型
            ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
            Class sClass = (Class) pt.getActualTypeArguments()[0];
            service = (S) RetrofitHelper.get(sClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static <T> T get() {
        return (T) RetrofitHelper.get(aClass);
    }

}
