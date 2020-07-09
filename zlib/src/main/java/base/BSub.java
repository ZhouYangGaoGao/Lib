package base;

import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import util.LogUtils;
import util.NetUtil;
import util.Reflector;

/**
 * Subscriber基类,可以在这里处理client网络连接状况
 * Created by YangYang on 2017/4/19.
 */

public class BSub<M extends BBean<T>, T> extends Subscriber<M> {

    private BView<T> mView;
    private String tag;

    public BSub(Observable<M> observable) {
        this(null, observable);
    }

    public BSub(BView<T> mView, Observable<M> observable) {
        this.mView = mView;
        tag = mView == null ? Reflector.name(this,1) + "" : mView.getClass().getSimpleName();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    public static <M extends BBean<T>, T> Subscription get(BView<T> view, Observable<M> observable) {
        return new BSub<M, T>(view, observable);
    }

    public static <M extends BBean<T>, T> Subscription get(Observable<M> observable) {
        return get(null, observable);
    }

    @Override
    public void onStart() {
        if (!NetUtil.isNetworkAvailable() && !isUnsubscribed()) {
            onCompleted();
            onFail("无网络");
            unsubscribe();
            return;
        }
        super.onStart();
    }

    @Override
    public void onError(Throwable e) {
        LogUtils.e(tag, e.getMessage());
        e.printStackTrace();
        onFail(e.getMessage());
        onCompleted();
    }

    @Override
    public void onNext(M m) {
        if (m == null) {
            onFail("无数据");
        } else {
            LogUtils.e(tag, new Gson().toJson(m));
            m.setOnNext( this);
        }
        onCompleted();
    }


    public boolean onCode(String code) {
        return true;
    }

    public void onFail(String message) {
        if (TextUtils.isEmpty(message)) return;
        if (Looper.getMainLooper() != Looper.myLooper()) return;
        if (BApp.app().act() == null) return;
        Toast.makeText(BApp.app().act(), message, Toast.LENGTH_SHORT).show();
    }

    public void onSuccess(T t) {
        if (mView != null) mView.success(t);
    }

    @Override
    public void onCompleted() {
        if (mView != null) mView.completed();
    }
}

