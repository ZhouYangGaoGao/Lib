package com.zhy.hd;


import android.content.Intent;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;

import com.google.gson.Gson;
import com.tencent.bugly.beta.Beta;

import base.BActivity;
import base.BApp;
import base.BConfig;
import base.BWebFragment;
import hawk.Hawk;
import util.Dialogs;
import util.GoTo;
import util.ScreenUtils;

public class MainActivity extends BActivity {
    private int scale = 200;

    @Override
    public void beforeView() {
        LoginModel model = Hawk.get(BConfig.LOGIN);
        if (model != null) {
            setIntent(new Intent()
                    .putExtra(BConfig.TOP_SHOW,false)
//                    .putExtra(Constant.URL, Constant.url + new Gson().toJson(model))
                    .putExtra(Constant.URL, "http://www.ahdhst.com/forest/ysweb/login"));
        } else {
            GoTo.start(LoginFragment.class);
            finish();
        }
        contentViewId = R.layout.activity_main;
    }

    @Override
    public void afterView() {
        BWebFragment fragment = (BWebFragment) getSupportFragmentManager().findFragmentById(R.id.mWebFragment);
        fragment.mWebView.setInitialScale(scale = Hawk.get("scale", scale));
        findViewById(R.id.btn_more).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Dialogs.show((onClickView, position) -> {
                    switch (position) {
                        case 0:
                            BApp.app().logout();
                            finish();
                            break;
                        case 1:
                            fragment.mWebView.reload();
                            break;
                        case 2:
                            Beta.checkUpgrade();
                            break;
                        case 3:
                            fragment.mWebView.setInitialScale(scale = scale + 50);
                            simulateClick(fragment.mWebView, ScreenUtils.getScreenHeight() / 2);
                            Hawk.put("scale", scale);
                            break;
                        case 4:
                            if (scale < 100) {
                                toast("已放大至极限");
                                break;
                            }
                            fragment.mWebView.setInitialScale(scale = scale - 50);
                            simulateClick(fragment.mWebView, ScreenUtils.getScreenHeight() / 2);
                            Hawk.put("scale", scale);
                            break;
                    }
                }, "切换账号", "刷新", "更新", "缩小", "放大");
            }
        });
    }

    private void simulateClick(View view, int point) {
        long downTime = SystemClock.uptimeMillis();
        final MotionEvent downEvent = MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN, point, point, 0);
        downTime += 1000;
        final MotionEvent upEvent = MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_UP, point, point, 0);
        view.onTouchEvent(downEvent);
        view.onTouchEvent(upEvent);
        downEvent.recycle();
        upEvent.recycle();
    }
}
