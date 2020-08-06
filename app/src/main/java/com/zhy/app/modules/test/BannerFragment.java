package com.zhy.app.modules.test;

import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.app.R;

import java.util.Collections;

import adapter.group.BaseViewHolder;
import base.BPagerFragment;

public class BannerFragment extends BPagerFragment<String> {

    @Override
    public void beforeView() {
        Collections.addAll(mData, getResources().getStringArray(R.array.load_name));
    }

    @Override
    public void convert(BaseViewHolder h, String i) {
        AVLoadingIndicatorView avl = h.get(R.id.avl);
        avl.setIndicator(i);
    }
}
