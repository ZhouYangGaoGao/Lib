package com.zhy.wlthd;

import adapter.group.BaseViewHolder;
import base.BBannerFragment;
import enums.CacheType;

public class WLTBannerFragment extends BBannerFragment<Integer> {
    @Override
    public void beforeView() {
        info.setCacheType(CacheType.none);
    }

    @Override
    public void afterView() {
        mData.clear();
        mData.add(R.mipmap.bg_welcome);
        mData.add(R.mipmap.bg_home);
        upData();
    }

    @Override
    protected void convert(BaseViewHolder h, Integer i) {
        h.get(R.id.image).setBackgroundResource(i);
    }
}
