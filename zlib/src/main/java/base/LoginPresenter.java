package base;

import rx.Subscription;

public class LoginPresenter<M> extends BPresenter<BView<M>> {

    public void action(Subscription subs){
        sub(subs);
    }

}
