package util;

import android.os.Looper;
import android.widget.Toast;

import com.google.gson.Gson;

import base.BApp;
import base.BResponse;
import base.BView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import util.ExceptionHandle;
import util.LogUtils;
import util.NetUtil;

/**
 * Subscriber基类,可以在这里处理client网络连接状况
 * （比如没有wifi，没有4g，没有联网等）
 * Created by yangyang on 2017/4/19.
 */

public class Suber<T> extends Subscriber<BResponse<T>> {

    public Suber(Observable<BResponse<T>> observable) {
        this.tag = Thread.currentThread().getStackTrace()[4].getMethodName();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(this);
    }

    public Suber(BView<T> mView, Observable<BResponse<T>> observable) {
        this.mView = mView;
        this.tag = Thread.currentThread().getStackTrace()[4].getMethodName();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(this);
    }

    private BView mView;
    private String tag = "";

    @Override
    public void onStart() {
        super.onStart();
        if (!NetUtil.isNetworkAvailable()) {
            if (!isUnsubscribed()) {
                onCompleted();
                onFail("无网络");
                unsubscribe();
            }
            return;
        }
    }

    @Override
    public void onNext(BResponse<T> baseBean) {
        if (baseBean != null && baseBean.getData() != null) {
            tag = baseBean.getModelName() + "=" + tag;
        }
        LogUtils.e("onNext=" + tag + "=", new Gson().toJson(baseBean));
        assert baseBean != null;
        if (baseBean.isSuccess()) {
            if (baseBean.getData() != null) {
                onSuccess(baseBean.getData());
            } else {
                onFail(null);
                onCode(baseBean.getCode());
            }
        } else {
//            if (baseBean.getCode().equals(Constant.TOKEN_INVALUE)) {
//                onFail(null);
//                BaseApplication.getInstance().logout(false);
//                Dialogs.show("登录过期", "请重新登录", (dialogInterface, i) -> {
//                    BaseApplication.getInstance().logout(true);
//                });
//                Dialogs.show = false;
//                Obser.timer(10000).subscribe(aLong -> Dialogs.show = true);
//            } else {
                onCode(baseBean.getCode());
                onFail(baseBean.getMsg());
//            }
        }
    }

    @Override
    public void onError(Throwable e) {
        LogUtils.e("onError=" + tag + "=", e.toString());
        if (e instanceof Exception) {
            ExceptionHandle.ResponeThrowable exception = ExceptionHandle.handleException(e);
            onFail(exception.message);
        } else {
            ExceptionHandle.ResponeThrowable throwable = new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.UNKNOWN);
            onFail(throwable.message);
        }
        onCompleted();
    }

    public void onCode(String code) {

    }

    public void onFail(String message) {
        if (message != null && Looper.getMainLooper() == Looper.myLooper() && BApp.app().currentActivity() != null)
            Toast.makeText(BApp.app().currentActivity(),message,Toast.LENGTH_SHORT).show();
    }

    public void onSuccess(T t) {
        if (mView != null) {
            mView.success(t);
        }
    }

    @Override
    public void onCompleted() {
        if (mView != null) {
            mView.completed();
        }
    }
}

