package base;

import hawk.Hawk;
import mvp.login.model.LoginModel;
import mvp.login.view.LoginFragment;
import util.GoTo;

public class BaseApp extends BApp {

    private LoginModel user;

    public LoginModel getUser() {
        if (user == null) user = new LoginModel();
        return user;
    }

    public void setUser(LoginModel user) {
        this.user = user;
    }

    @Override
    protected void initApp() {
        setUser(Hawk.get(BConfig.LOGIN));
        BConfig.getConfig()
                .setBaseUrl("https://www.wanandroid.com/")
                .setBugLy("a0cb67e1d4")
                .setInterceptor(new Interceptor())
                .initCardView();
    }

    @Override
    public void logout() {
        Hawk.deleteAll();
        act().finish();
        GoTo.start(LoginFragment.class);
    }

    public static BaseApp app() {
        return (BaseApp) BApp.app();
    }
}
