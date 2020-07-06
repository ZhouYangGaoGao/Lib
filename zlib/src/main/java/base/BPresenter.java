package base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BPresenter<V extends BView> {

    protected V mView;
    private CompositeSubscription mCompositeSubscription;

    protected void attach(V view) {
        this.mView = view;
    }

    protected void detach() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    public void sub(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

}
