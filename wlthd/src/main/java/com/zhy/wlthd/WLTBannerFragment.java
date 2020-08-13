package com.zhy.wlthd;

import adapter.group.BaseViewHolder;
import base.BBannerFragment;
import enums.CacheType;

public class WLTBannerFragment extends BBannerFragment<Integer> {
    @Override
    public void beforeView() {
        banner.itemLayoutId = R.layout.item_image;
        info.setCacheType(CacheType.none);
        banner.useIndicate = false;
    }

    @Override
    public void afterView() {
        log("afterView", mData.size() + "");
        mData.clear();
        mData.add(R.mipmap.bg_welcome);
        mData.add(R.mipmap.bg_home);
        mData.add(R.mipmap.bg_welcome);
        mData.add(R.mipmap.bg_home);
        upData();
    }

    @Override
    protected void convert(BaseViewHolder h, Integer i) {
        h.get(R.id.image).setBackgroundResource(i);
    }
}
