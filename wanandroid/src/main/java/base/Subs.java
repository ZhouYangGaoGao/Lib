package base;

import rx.Observable;

public class Subs<T> extends BSub<BaseBean<T>,T> {

    public Subs(Observable<BaseBean<T>> observable) {
        super(observable);
    }

    public Subs(BView<T> mView, Observable<BaseBean<T>> observable) {
        super(mView, observable);
    }
}

