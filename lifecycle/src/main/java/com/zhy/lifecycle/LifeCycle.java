package com.zhy.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.lang.ref.WeakReference;

public class LifeCycle {
    private static LifeCycle lifeCycle;
    private WeakReference<Activity> mCurrentAct;

    public static LifeCycle get(Application application) {
        if (lifeCycle == null) lifeCycle = new LifeCycle(application);
        return lifeCycle;
    }

    public Activity act() {
        return mCurrentAct.get();
    }

    private LifeCycle(Application application) {
        application.registerActivityLifecycleCallbacks(new ActListener() {
            @Override
            protected void onCurrentActivity(Activity activity) {
                if (mCurrentAct != null && !activity.equals(mCurrentAct.get()))
                    mCurrentAct = new WeakReference<>(activity);
            }
        });
    }

    public static void application(Application application, ActListener fragListener) {
        application.registerActivityLifecycleCallbacks(fragListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static void activity(Activity activity, ActListener listener) {
        activity.registerActivityLifecycleCallbacks(listener);
    }

    public static LifeFragment setOnResult(AppCompatActivity activity, final onActivityResultListener listener) {
        final LifeFragment lifeFragment = new LifeFragment(listener);
        activity.getWindow().getDecorView().setId(R.id.id_window);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.id_window, lifeFragment).commit();
        return lifeFragment;
    }

    public static LifeFragment setOnActivityResult(final AppCompatActivity act, final onActivityResultListener listener) {
        final LifeFragment lifeFragment = new LifeFragment(listener);
        act.getApplication().registerActivityLifecycleCallbacks(new ActListener() {
            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                super.onActivityStarted(activity);
                if (act == activity && !act.getSupportFragmentManager().getFragments().contains(lifeFragment)) {
                    act.getWindow().getDecorView().setId(R.id.id_window);
                    act.getSupportFragmentManager().beginTransaction().add(R.id.id_window, lifeFragment).commit();
                }
            }

        });
        return lifeFragment;
    }

    public static class ActListener implements Application.ActivityLifecycleCallbacks {
        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
            onCurrentActivity(activity);
        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {

        }

        @Override
        public void onActivityResumed(@NonNull Activity activity) {
            onCurrentActivity(activity);
        }

        @Override
        public void onActivityPaused(@NonNull Activity activity) {

        }

        @Override
        public void onActivityStopped(@NonNull Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {

        }

        protected void onCurrentActivity(Activity activity) {

        }
    }

    public static class LifeFragment extends Fragment {
        private onActivityResultListener listener;

        public LifeFragment(onActivityResultListener listener) {
            this.listener = listener;
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (listener != null) listener.onActivityResult(requestCode, resultCode, data);
        }
    }

    public interface onActivityResultListener {
        void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);
    }
}
