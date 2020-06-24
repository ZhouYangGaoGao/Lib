package com.zhy.app.modules.test;

import android.view.View;

import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.android.adapter.CommonAdapter;
import com.zhy.app.R;


import java.util.Collections;

import base.SmartFragment;

public class AVLFragment extends SmartFragment<String> {
    @Override
    public void beforeView() {
        numColumns = 3;
        itemLayoutId = R.layout.item_avl;
        scrollAble = true;
        Collections.addAll(mData, getResources().getStringArray(R.array.load_name));
    }

    @Override
    public void afterView() {
        mTopView.topContent.setVisibility(View.GONE);
    }

    @Override
    public void convert(CommonAdapter.ViewHolder h, String i) {
        AVLoadingIndicatorView avl = h.getView(R.id.avl);
        avl.setIndicator(i);
    }

}
