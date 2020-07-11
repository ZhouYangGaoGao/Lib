package base;

import com.google.gson.Gson;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import util.LogUtils;
import util.NetUtil;

public class TestSub extends Subscriber<Object> {

    public TestSub() {
        BManager.test().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    @Override
    public void onStart() {
        if (!NetUtil.isNetworkAvailable() && !isUnsubscribed()) {
            onCompleted();
            LogUtils.e("无网络");
            unsubscribe();
            return;
        }
        super.onStart();
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onNext(Object o) {
        LogUtils.e("TestSubscriber",new Gson().toJson(o));
    }
}
