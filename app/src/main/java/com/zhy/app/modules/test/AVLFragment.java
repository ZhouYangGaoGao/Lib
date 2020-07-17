package com.zhy.app.modules.test;

import android.view.View;

import com.wang.avi.AVLoadingIndicatorView;
import adapter.CommonAdapter;
import com.zhy.app.R;


import java.util.Collections;

import adapter.ViewHolder;
import base.BSmartFragment;

public class AVLFragment extends BSmartFragment<String> {
    @Override
    public void beforeView() {
        numColumns = 3;
        itemLayoutId = R.layout.item_avl;
        scrollAble = true;
        Collections.addAll(mData, getResources().getStringArray(R.array.load_name));
    }

    @Override
    public void afterView() {
        mSmartView.topContent.setVisibility(View.GONE);
    }

    @Override
    public void convert(ViewHolder h, String i) {
        AVLoadingIndicatorView avl = h.getView(R.id.avl);
        avl.setIndicator(i);
    }

}
