package com.zhy.app;

import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.zhy.app.modules.one.view.SmartViewFragment;
import com.zhy.app.modules.test.AVLFragment;
import com.zhy.app.modules.test.BannerFragment;
import com.zhy.app.modules.test.PhotoFragment;
import com.zhy.app.modules.test.TestFragment;

import base.BActivity;
import base.WebFragment;
import butterknife.BindView;
import util.ScreenUtils;

public class MainActivity extends BActivity {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    SmartTabLayout tabLayout;

    @Override
    public void beforeView() {
        contentView = R.layout.activity_main;
    }

    @Override
    public void initView() {
        FragmentPagerItems.Creator creator = FragmentPagerItems.with(this);
        creator.add("SmartList", TestFragment.class);
        creator.add("SmartView", SmartViewFragment.class);
        creator.add("AVLFragment", AVLFragment.class);
        creator.add("Banner", BannerFragment.class);
        creator.add("PhotoFragment", PhotoFragment.class);
        creator.add("WebFragment", WebFragment.class);
        viewPager.setAdapter(new FragmentPagerItemAdapter(getSupportFragmentManager(), creator.create()));
        tabLayout.setViewPager(viewPager);
        viewPager.setOffscreenPageLimit(viewPager.getAdapter().getCount());
        for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
            ((TextView) tabLayout.getTabAt(i)).setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_one, 0, 0);
            tabLayout.getTabAt(i).setPadding(0, ScreenUtils.dip2px(4), 0, ScreenUtils.dip2px(4));
        }
    }
}
