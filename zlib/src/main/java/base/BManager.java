package base;

import java.lang.reflect.ParameterizedType;

import util.RetrofitHelper;

public class BManager<T> {
    protected T service;
    protected static Object manager;

   protected BManager() {
        try {
            // 通过反射获取RetrofitService的真实类型
            ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
            Class<?> aClass = (Class<?>) pt.getActualTypeArguments()[0];
            service = (T) RetrofitHelper.getInstance(BConfig.getConfig().getBaseUrl())
                    .getServer(aClass);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
