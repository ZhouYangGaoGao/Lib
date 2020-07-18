package base;

import mvp.login.model.LoginModel;
import rx.Observable;
import util.MMap;
import util.RetrofitHelper;

public class Manager extends BManager<Api> {
    public static Manager get() {
        return RetrofitHelper.get(Manager.class);
    }

    public static Api getApi() {
        return get().service;
    }

    public Observable<BaseBean<LoginModel>> login(String username, String password) {
        return service.login(new MMap(BConfig.USER_NAME, username).add(BConfig.PASSWORD, password));
    }

    public Observable<BaseBean<LoginModel>> register(String username, String password, String rePassword) {
        return service.register(new MMap(BConfig.USER_NAME, username)
                .add(BConfig.RE_PASSWORD, rePassword).add(BConfig.PASSWORD, password));
    }
}
