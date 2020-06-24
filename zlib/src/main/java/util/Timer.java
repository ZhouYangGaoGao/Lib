package util;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Timer extends Observable<Long> {

    /**
     * Creates an Observable with a Function to execute when it is subscribed to.
     * <p>
     * <em>Note:</em> Use {@link #unsafeCreate(OnSubscribe)} to create an Observable, instead of this constructor,
     * unless you specifically have a need for inheritance.
     *
     * @param f {@link OnSubscribe} to be executed when  is called
     */
    protected Timer(OnSubscribe<Long> f) {
        super(f);
    }


    public static Observable<Long> interval(long initialDelay, long period, TimeUnit unit) {
        return main(interval(initialDelay, period, unit, Schedulers.computation()));
    }

    public static Observable<Long> interval(long initialDelay, long period) {
        return main(interval(initialDelay, period, TimeUnit.MILLISECONDS, Schedulers.computation()));
    }

    public static Observable<Long> interval(long period) {
        return main(interval(0, period, TimeUnit.MILLISECONDS, Schedulers.computation()));
    }

    public static Observable<Long> timer(long initialDelay) {
        return main(timer(initialDelay, TimeUnit.MILLISECONDS, Schedulers.computation()));
    }

    private static Observable<Long> main(Observable<Long> o) {
        return o.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
