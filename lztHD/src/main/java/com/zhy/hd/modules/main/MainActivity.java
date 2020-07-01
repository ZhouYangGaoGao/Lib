package com.zhy.hd.modules.main;


import android.view.View;
import android.widget.TextView;

import com.necer.ndialog.ChoiceDialog;
import com.tencent.bugly.beta.Beta;

import base.BApp;
import base.WebActivity;
import hawk.Hawk;
import util.Dialogs;

public class MainActivity extends WebActivity {
    int scale = 200;
    @Override
    public void afterView() {
        initDragView();
    }

    private void initDragView() {
        getmWebFragment().mWebView.setInitialScale(scale = Hawk.get("scale", scale));
        findViewById(com.zhy.android.R.id.btn_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialogs.show(new ChoiceDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(TextView onClickView, int position) {
                        switch (position) {
                            case -1:
                                break;
                            case 0:
                                BApp.app().logout();
                                finish();
                                break;
                            case 1:
                                reLoad();
                                break;
                            case 2:
                                Beta.checkUpgrade();
                                break;
                            default:
                                Hawk.put("scale", scale = position * 50 - 100);
                                getmWebFragment().mWebView.setInitialScale(scale);
                                reLoad();
                        }
                    }
                }, "切换账号", "刷新", "更新", "缩放0.5倍", "缩放1倍", "缩放1.5倍", "缩放2.0倍", "缩放2.5倍", "缩放3.0倍");
            }
        });
    }

}
