package com.zhy.app.modules.test;

import com.zhy.app.MainActivity;
import com.zhy.app.R;
import com.zhy.app.modules.login.view.LoginFragment;

import base.BActivity;
import base.BConfig;
import hawk.Hawk;
import rx.functions.Action1;
import util.GoTo;
import util.Timer;

public class SplashActivity extends BActivity {
    @Override
    public void beforeView() {
        contentView = R.layout.frame_layout;
        statusBarColor = 0xffffffff;
    }

    @Override
    public void afterView() {
        findViewById(R.id.content).setBackgroundResource(R.mipmap.splash);
        Timer.timer(17).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                if (Hawk.get(BConfig.LOGIN) == null)
                    GoTo.start(LoginFragment.class);
                else
                    GoTo.start(MainActivity.class);
                finish();
            }
        });
    }

}
