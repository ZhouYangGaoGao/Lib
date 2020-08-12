package com.zhy.app.modules.test;

import android.view.View;

import com.wang.avi.AVLoadingIndicatorView;

import com.zhy.app.R;


import java.util.Collections;

import adapter.ViewHolder;
import base.BListFragment;
import enums.CacheType;

public class AVLFragment extends BListFragment<String> {
    @Override
    public void beforeView() {
        grid.numColumns = 3;
        grid.itemLayoutId = R.layout.item_avl;
        info.setCacheType(CacheType.none);
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
