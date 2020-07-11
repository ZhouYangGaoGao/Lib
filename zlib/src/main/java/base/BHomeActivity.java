package base;

import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.zhy.android.R;

import annotation.Presenter;
import util.ScreenUtils;

public class BHomeActivity extends BActivity {
    protected ViewPager viewPager;
    protected SmartTabLayout tabLayout;
    protected LinearLayout mRootView;
    protected int textSize = 11;
    protected int tPadding = 4;
    protected int bPadding = 2;
    private int[] icons = {R.drawable.ic_baidu, R.drawable.ic_baidu};

    {
        contentViewId = R.layout.activity_home;
    }

    @Override
    public void initView() {
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        mRootView = findViewById(R.id.mRootView);
        viewPager.setAdapter(new FragmentPagerItemAdapter(getSupportFragmentManager(), initFragments(FragmentPagerItems.with(this)).create()));
        tabLayout.setViewPager(viewPager);
        viewPager.setOffscreenPageLimit(viewPager.getAdapter().getCount());
        for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
            TextView textView = (TextView) tabLayout.getTabAt(i);
            textView.setCompoundDrawablesWithIntrinsicBounds(0, icons[i], 0, 0);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenUtils.sp2px(textSize));
            textView.setPadding(0, ScreenUtils.dip2px(tPadding), 0, ScreenUtils.dip2px(bPadding));
        }
    }

    protected FragmentPagerItems.Creator initFragments(FragmentPagerItems.Creator creator) {
        return creator.add("百1度", BWebFragment.class).add("百2度", BWebFragment.class);
    }

    protected void setIcons(int... icons) {
        this.icons = icons;
    }

}
