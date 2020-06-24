package base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BPresenter<V extends BView> {

    public V mView;
    private CompositeSubscription mCompositeSubscription;

    public void attachView(V view) {
        this.mView = view;
    }

    public void detachView() {
        if (mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    public void subscribe(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

}
