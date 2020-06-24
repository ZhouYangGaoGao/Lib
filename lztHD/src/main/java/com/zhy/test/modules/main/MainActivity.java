package com.zhy.test.modules.main;


import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.necer.ndialog.ChoiceDialog;
import com.zhy.test.base.BaseApp;

import base.BApp;
import base.WebActivity;
import util.Dialogs;

public class MainActivity extends WebActivity {
    RelativeLayout mRootView;

    @Override
    public void afterView() {
        initDragView();
    }

    private void initDragView() {
        mRootView = findViewById(com.zhy.android.R.id.mRootView);
        findViewById(com.zhy.android.R.id.btn_more).setOnTouchListener(new View.OnTouchListener() {
            int lastX, lastY, startX, startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = lastX = (int) event.getRawX();
                        startY = lastY = (int) event.getRawY();
                        layoutParams.leftMargin = lastX - v.getWidth() / 2;
                        layoutParams.topMargin = lastY - v.getHeight();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int dx = (int) event.getRawX() - lastX;
                        int dy = (int) event.getRawY() - lastY;
                        int l = layoutParams.leftMargin + dx;
                        int t = layoutParams.topMargin + dy;
                        int b = mRootView.getHeight() - t - v.getHeight();
                        int r = mRootView.getWidth() - l - v.getWidth();
                        if (l < 0) {//处理按钮被移动到上下左右四个边缘时的情况，决定着按钮不会被移动到屏幕外边去
                            l = 0;
                            r = mRootView.getWidth() - v.getWidth();
                        }
                        if (t < 0) {
                            t = 0;
                            b = mRootView.getHeight() - v.getHeight();
                        }

                        if (r < 0) {
                            r = 0;
                            l = mRootView.getWidth() - v.getWidth();
                        }
                        if (b < 0) {
                            b = 0;
                            t = mRootView.getHeight() - v.getHeight();
                        }
                        layoutParams.leftMargin = l;
                        layoutParams.topMargin = t;
                        layoutParams.bottomMargin = b;
                        layoutParams.rightMargin = r;
                        v.setLayoutParams(layoutParams);

                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        v.postInvalidate();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (Math.abs(lastX - startX) < 5 && Math.abs(lastY - startY) < 5)
                            Dialogs.show(new ChoiceDialog.OnItemClickListener() {
                                @Override
                                public void onItemClick(TextView onClickView, int position) {
                                    if (position == 0)
                                        BApp.app().logout();
                                }
                            }, "切换账号");
                        break;
                }
                return true;
            }
        });
    }

}
