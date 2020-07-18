package base;

import android.app.Activity;
import android.app.Application;
import android.text.TextUtils;

import com.tencent.bugly.Bugly;
import com.zhy.android.BuildConfig;

import java.lang.ref.WeakReference;

import hawk.Hawk;
import listener.ActCallback;

public abstract class BApp extends Application implements Runnable {
    private static BApp app;
    private WeakReference<Activity> act;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        new Thread(this).start();
    }

    public static BApp app() {
        return app;
    }

    public void logout() {
    }

    public Activity act() {
        return act != null ? act.get() : null;
    }

    @Override
    public void run() {
        Hawk.init(app).build();
        registerActivityLifecycleCallbacks(new ActCallback() {
            @Override
            protected void onCurrentActivity(Activity activity) {
                act = new WeakReference<>(activity);
            }
        });
        initApp();
        if (!TextUtils.isEmpty(BConfig.get().getBugLy()))
            Bugly.init(app, BConfig.get().getBugLy(), BuildConfig.DEBUG);
    }

    protected abstract void initApp();
}
