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
        creator.add("SmartView", SmartViewFragment.class);
        creator.add("SmartList", TestFragment.class);
        creator.add("Banner", BannerFragment.class);
        creator.add("picSelector", PhotoFragment.class);
        viewPager.setAdapter(new FragmentPagerItemAdapter(getSupportFragmentManager(), creator.create()));
        tabLayout.setViewPager(viewPager);
        viewPager.setOffscreenPageLimit(4);
        ((TextView) tabLayout.getTabAt(0)).setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_one, 0, 0);
        ((TextView) tabLayout.getTabAt(1)).setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_one, 0, 0);
        ((TextView) tabLayout.getTabAt(2)).setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_one, 0, 0);
        ((TextView) tabLayout.getTabAt(3)).setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_one, 0, 0);
        tabLayout.getTabAt(0).setPadding(0, ScreenUtils.dip2px(4), 0, ScreenUtils.dip2px(4));
        tabLayout.getTabAt(1).setPadding(0, ScreenUtils.dip2px(4), 0, ScreenUtils.dip2px(4));
        tabLayout.getTabAt(2).setPadding(0, ScreenUtils.dip2px(4), 0, ScreenUtils.dip2px(4));
        tabLayout.getTabAt(3).setPadding(0, ScreenUtils.dip2px(4), 0, ScreenUtils.dip2px(4));
    }
}
