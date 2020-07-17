package com.zhy.app;

import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.zhy.app.modules.test.BannerFragment;
import com.zhy.app.modules.test.PhotoFragment;
import com.zhy.app.modules.test.TestFragment;

import base.BHomeActivity;
import base.BWebFragment;

public class MainActivity extends BHomeActivity {

    @Override
    protected FragmentPagerItems.Creator initFragments(FragmentPagerItems.Creator creator) {
        icons = new int[]{R.drawable.icon_one, R.drawable.icon_one, R.drawable.icon_one, R.drawable.icon_one};
        return creator.add("智能列表", TestFragment.class)
                .add("轮播图", BannerFragment.class)
                .add("图片选择", PhotoFragment.class)
                .add("网页", BWebFragment.class);
    }
}
