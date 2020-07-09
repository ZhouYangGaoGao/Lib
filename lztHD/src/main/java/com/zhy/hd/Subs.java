package com.zhy.hd;

import base.BSub;
import base.BView;
import rx.Observable;

public class Subs<T> extends BSub<BaseBean<T>,T> {

    public Subs(Observable<BaseBean<T>> observable) {
        super(observable);
    }

    public Subs(BView<T> mView, Observable<BaseBean<T>> observable) {
        super(mView, observable);
    }
}

