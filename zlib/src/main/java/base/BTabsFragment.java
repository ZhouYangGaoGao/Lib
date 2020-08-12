package base;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;
import com.zhy.android.R;

import annotation.Presenter;
import custom.SmartView;

public abstract class BTabsFragment<M> extends BListDataFragment<M> {

    protected ViewPager mViewPager;
    protected SmartTabLayout mTabView;
    protected SmartView mSmartView;
    protected FragmentPagerItems.Creator creator;
    protected int textSize = 11;

    {
        contentViewId = R.layout.fragment_tabs;
    }

    @Override
    public void initView() {
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        mTabView = (SmartTabLayout) findViewById(R.id.mTabView);
        mSmartView = (SmartView) findViewById(R.id.mSmartView);
        mSmartView.centerTextView.setText(info.title);
        mSmartView.topContent.setVisibility(View.GONE);
        creator = FragmentPagerItems.with(getContext());
    }

    protected void initFragments() {
        mViewPager.setAdapter(new FragmentStatePagerItemAdapter(getChildFragmentManager(), creator.create()));
        mTabView.setViewPager(mViewPager);
    }

}
