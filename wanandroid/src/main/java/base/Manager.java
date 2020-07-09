package base;


import mvp.login.model.LoginModel;
import rx.Observable;
import util.BodyMap;
import util.RetrofitHelper;

public class Manager extends BManager<Api> {
    public static Manager get(){
        return RetrofitHelper.get(Manager.class);
    }

    public Observable<BaseBean<LoginModel>> login(String username, String password) {
        return service.login(new BodyMap("username", username).add("password", password));
    }

    public Observable<BaseBean<LoginModel>> register(String username, String password, String repassword) {
        return service.register(new BodyMap("username", username)
                .add("repassword",repassword).add("password", password));
    }


}
