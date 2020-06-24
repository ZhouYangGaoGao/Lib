package com.zhy.app.modules.test;

import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.app.R;

import java.util.Collections;

import base.PagerFragment;
import custom.AutoScrollViewPager;

public class BannerFragment extends PagerFragment<String> {

    @Override
    public void beforeView() {
        Collections.addAll(mData, getResources().getStringArray(R.array.load_name));
    }

    @Override
    public void convert(Viewholder h, String i) {
        AVLoadingIndicatorView avl = h.getView(R.id.avl);
        avl.setIndicator(i);
    }

    @Override
    public void onPageClick(AutoScrollViewPager pager, int position) {
        mData.remove(0);
        mData.remove(0);
        log("onPageClick"+mData.size());
        upDate();
    }
}
