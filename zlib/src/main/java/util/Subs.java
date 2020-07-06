package util;

import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;

import base.BApp;
import base.BResponse;
import base.BView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Subscriber基类,可以在这里处理client网络连接状况
 * Created by YangYang on 2017/4/19.
 */

public class Subs<T> extends Subscriber<BResponse<T>> {

    private BView<T> mView;

    public Subs(Observable<BResponse<T>> observable) {
        this(null,observable);
    }

    public Subs(BView<T> mView, Observable<BResponse<T>> observable) {
        this.mView = mView;
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    public static <T> Subscription get(BView<T> view, Observable<BResponse<T>> observable) {
        return new Subs<>(view,observable);
    }

    public static <T> Subscription get(Observable<BResponse<T>> observable) {
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
    public void onNext(BResponse<T> baseBean) {
        onCompleted();

        if (baseBean == null) {
            onFail("无数据");
            return;
        }

        if (baseBean.getData() != null)
            LogUtils.e(baseBean.getModelName(), new Gson().toJson(baseBean));
        else
            LogUtils.e(Reflector.name(this), new Gson().toJson(baseBean));

        if (baseBean.isSuccess() && baseBean.getData() != null)
            onSuccess(baseBean.getData());
        else {
            onCode(baseBean.getCode());
            onFail(baseBean.getMsg());
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        onFail(e.getMessage());
        onCompleted();
    }

    protected void onCode(String code) {
    }

    protected void onFail(String message) {
        if (!TextUtils.isEmpty(message)) return;
        if (Looper.getMainLooper() != Looper.myLooper()) return;
        if (BApp.app().act() == null) return;
        Toast.makeText(BApp.app().act(), message, Toast.LENGTH_SHORT).show();
    }

    protected void onSuccess(T t) {
        if (mView != null) mView.success(t);
    }

    @Override
    public void onCompleted() {
        if (mView != null) mView.completed();
    }
}

