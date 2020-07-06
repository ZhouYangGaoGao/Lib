package base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;

import com.tencent.bugly.Bugly;
import com.zhy.android.BuildConfig;

import java.lang.ref.WeakReference;

import hawk.Hawk;

public abstract class BApp extends Application implements Runnable{
    private static BApp app;
    private WeakReference<Activity> sCurrentActivityWeakRef;

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
        Activity currentActivity = null;
        if (sCurrentActivityWeakRef != null) {
            currentActivity = sCurrentActivityWeakRef.get();
        }
        return currentActivity;
    }

    private void initActivityCallBack() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                sCurrentActivityWeakRef = new WeakReference<Activity>(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                sCurrentActivityWeakRef = new WeakReference<Activity>(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    @Override
    public void run() {
        Hawk.init(app).build();
        initActivityCallBack();
        initApp();
        if (!TextUtils.isEmpty(BConfig.getConfig().getBugLy()))
            Bugly.init(app, BConfig.getConfig().getBugLy(), BuildConfig.DEBUG);
    }

    protected abstract void initApp();
}
