package com.zhy.wlthd;

import base.BSub;
import base.BView;
import rx.Observable;

public class WLTSub<T> extends BSub<WLTBean<T>,T> {

    public WLTSub(Observable<WLTBean<T>> observable) {
        super(observable);
    }

    public WLTSub(BView<T> mView, Observable<WLTBean<T>> observable) {
        super(mView, observable);
    }
}

