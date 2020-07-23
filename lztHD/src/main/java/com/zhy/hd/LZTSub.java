package com.zhy.hd;

import base.BSub;
import base.BView;
import rx.Observable;

public class LZTSub<T> extends BSub<LZTBean<T>,T> {

    public LZTSub(Observable<LZTBean<T>> observable) {
        super(observable);
    }

    public LZTSub(BView<T> mView, Observable<LZTBean<T>> observable) {
        super(mView, observable);
    }
}

