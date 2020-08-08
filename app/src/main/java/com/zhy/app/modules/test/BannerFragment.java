package com.zhy.app.modules.test;

import java.util.Collections;

import adapter.group.BaseViewHolder;
import base.BBannerFragment;
import custom.page.ScaleInTransformer;

public class BannerFragment extends BBannerFragment<String> {

    @Override
    public void beforeView() {
        banner.indicatorSpacing = 2;
        banner.pageMargin = 10;
        banner.multiWidth = 25;
        banner.transformer = new ScaleInTransformer();
        Collections.addAll(mData, "", "", "");
    }

    @Override
    public void convert(BaseViewHolder h, String i) {
//        AVLoadingIndicatorView avl = h.get(R.id.avl);
//        avl.setIndicator(i);
//        avl.setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }
}
