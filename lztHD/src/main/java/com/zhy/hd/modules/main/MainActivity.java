package com.zhy.hd.modules.main;


import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.necer.ndialog.ChoiceDialog;
import com.tencent.bugly.beta.Beta;
import com.zhy.hd.R;

import base.BActivity;
import base.BApp;
import base.WebFragment;
import hawk.Hawk;
import rx.functions.Action1;
import util.Dialogs;
import util.ScreenUtils;
import util.Timer;

public class MainActivity extends BActivity {
    private int scale = 200;

    @Override
    public void beforeView() {
        contentView = R.layout.activity_main;
    }

    @Override
    public void afterView() {
        WebFragment fragment = (WebFragment) getSupportFragmentManager().findFragmentById(R.id.mWebFragment);
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
                                    simulateClick(fragment.mWebView,ScreenUtils.getScreenHeight()/2);
                            break;
                        case 4:
                            if (scale < 100) {
                                toast("已放大至极限");
                                break;
                            }
                            fragment.mWebView.setInitialScale(scale = scale - 50);
                                    simulateClick(fragment.mWebView,ScreenUtils.getScreenHeight()/2);
                            break;
                    }
                }, "切换账号", "刷新", "更新", "缩小", "放大");
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Hawk.put("scale", scale);
    }

    private void simulateClick(View view ,int point) {
        long downTime = SystemClock.uptimeMillis();
        final MotionEvent downEvent = MotionEvent.obtain(downTime, downTime,MotionEvent.ACTION_DOWN, point, point, 0);
        downTime += 1000;
        final MotionEvent upEvent = MotionEvent.obtain(downTime, downTime,MotionEvent.ACTION_UP, point, point, 0);
        view.onTouchEvent(downEvent);
        view.onTouchEvent(upEvent);
        downEvent.recycle();
        upEvent.recycle();
    }
}
