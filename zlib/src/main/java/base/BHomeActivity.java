package base;

import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.zhy.android.R;

import org.greenrobot.eventbus.Subscribe;

import custom.SmartView;
import util.ScreenUtils;
import util.StatusBarUtil;

public class BHomeActivity extends BActivity<Object,BPresenter<BView<?>>> implements ViewPager.OnPageChangeListener {
    protected DrawerLayout mDrawerLayout;
    protected ViewPager mViewPager;
    protected SmartTabLayout mTabLayout;
    protected SmartView mSmartView;
    protected View drawerContentView;
    protected int textSize = 11;
    protected int tPadding = 4;
    protected int bPadding = 2;
    protected int[] icons = {R.drawable.ic_baidu, R.drawable.ic_baidu};
    private FrameLayout mDrawerContentLayout;

    {
        contentViewId = R.layout.activity_home;
        useEventBus = true;
    }

    @Override
    public void initView() {
        mDrawerLayout = findViewById(R.id.mDrawerLayout);
        mDrawerContentLayout = findViewById(R.id.drawerContent);
        mViewPager = findViewById(R.id.mViewPager);
        mTabLayout = findViewById(R.id.mTabLayout);
        mSmartView = findViewById(R.id.mSmartView);
        if (drawerContentView != null) mDrawerContentLayout.addView(drawerContentView);
        mViewPager.setAdapter(new FragmentPagerItemAdapter(getSupportFragmentManager(),
                initFragments(FragmentPagerItems.with(this)).create()));
        mTabLayout.setViewPager(mViewPager);
        mTabLayout.setOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(mViewPager.getAdapter().getCount());
        mSmartView.centerTextView.setText(mViewPager.getAdapter().getPageTitle(0));
        for (int i = 0; i < mViewPager.getAdapter().getCount(); i++) {
            TextView textView = (TextView) mTabLayout.getTabAt(i);
            textView.setCompoundDrawablesWithIntrinsicBounds(0, icons[i], 0, 0);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenUtils.sp2px(textSize));
            textView.setPadding(0, ScreenUtils.dip2px(tPadding), 0, ScreenUtils.dip2px(bPadding));
        }
    }

    protected FragmentPagerItems.Creator initFragments(FragmentPagerItems.Creator creator) {
        return creator.add("百1度", BWebFragment.class).add("百2度", BWebFragment.class);
    }

    @Subscribe
    public void actionDrawer(DrawerEvent event) {
        if (event.open) mDrawerLayout.openDrawer(mDrawerContentLayout);
        else mDrawerLayout.closeDrawer(mDrawerContentLayout);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mSmartView.centerTextView.setText(mViewPager.getAdapter().getPageTitle(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public static class DrawerEvent {
        public boolean open;
        public DrawerEvent(boolean open) {
            this.open = open;
        }
    }
}
