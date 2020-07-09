package base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BPresenter<V extends BView<?>> {

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

    public boolean sub(Subscription subscription) {
        if (subscription == null) return false;
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
        return true;
    }

}
