package com.zhy.app.modules.test;

import android.widget.RelativeLayout;

import com.zhy.app.R;

import base.BFragment;
import butterknife.BindView;
import butterknife.OnClick;
import slike.BitmapProvider;
import slike.SuperLikeLayout;
import util.ScreenUtils;

public class SLikeFragment extends BFragment {
    @BindView(R.id.mSuperLikeLayout)
    SuperLikeLayout mSuperLikeLayout;
    @BindView(R.id.mContent)
    RelativeLayout mContent;

    @Override
    public void beforeView() {
        contentViewId = R.layout.fragment_slike;
    }

    @Override
    public void afterView() {
        int[] arry = {R.mipmap.multi_digg_num_0, R.mipmap.multi_digg_num_1, R.mipmap.multi_digg_num_2, R.mipmap.multi_digg_num_3,
                R.mipmap.multi_digg_num_4, R.mipmap.multi_digg_num_5, R.mipmap.multi_digg_num_6, R.mipmap.multi_digg_num_7,
                R.mipmap.multi_digg_num_8, R.mipmap.multi_digg_num_9};

        mSuperLikeLayout.setProvider(new BitmapProvider.Builder(getContext())
                .setDrawableArray(arry)
                .setNumberDrawableArray(arry)
                .setLevelDrawableArray(R.mipmap.multi_digg_word_level_1, R.mipmap.multi_digg_word_level_2, R.mipmap.multi_digg_word_level_3)
                .build());
    }

    @OnClick(R.id.mContent)
    public void onClick() {
        mSuperLikeLayout.launch(ScreenUtils.getScreenWidth() / 2, ScreenUtils.getScreenHeight() / 2);
    }
}
