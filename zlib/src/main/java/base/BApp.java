package base;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebStorage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tencent.bugly.Bugly;
import com.zhy.android.BuildConfig;
import com.zhy.lifecycle.LifeCycle;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import hawk.Hawk;
import util.GoTo;
import util.LogUtils;

public abstract class BApp extends Application implements Runnable {
    private static BApp app;
    private WeakReference<Activity> act;
    private List<WeakReference<Activity>> acts = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        LifeCycle.application(this, new LifeCycle.ActListener() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
                super.onActivityCreated(activity, bundle);
                acts.add(new WeakReference<>(activity));
            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                super.onActivityDestroyed(activity);
                for (int i = 0; i < acts.size(); i++) {
                    if (acts.get(i).get().equals(activity)) {
                        acts.remove(i);
                        return;
                    }
                }
            }

            @Override
            protected void onCurrentActivity(Activity activity) {
                act = new WeakReference<>(activity);
            }
        });
        new Thread(this).start();
    }

    public static BApp app() {
        return app;
    }

    public void logout() {
        expired();
    }

    public void expired() {
        Hawk.deleteAll();
        BConfig.get().setExpired(true);
        if (BConfig.get().getLoginClass() != null) {
            clearActs();
            if (acts.size() > 0 && acts.get(0).get() != null && BSplashActivity.class.isAssignableFrom(acts.get(0).get().getClass())) {
                ((BSplashActivity) acts.get(0).get()).startLogin();
            } else {
                GoTo.start(BConfig.get().getLoginClass());
            }
        }
    }

    public Activity act() {
        return act != null ? act.get() : null;
    }

    public void clearActs() {
        for (int i = 0; i < acts.size(); i++) {
            Activity activity = acts.get(i).get();
            if (activity != null && !activity.getClass().equals(BConfig.get().getLoginClass())) {
                acts.remove(i);
                activity.finish();
                i--;
            }
        }
    }

    @Override
    public void run() {
        Hawk.init(app).build();
        initApp();
        if (!TextUtils.isEmpty(BConfig.get().getBugLy()))
            Bugly.init(app, BConfig.get().getBugLy(), BuildConfig.DEBUG);
    }

    protected abstract void initApp();
}
