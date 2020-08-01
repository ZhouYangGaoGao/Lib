package base;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.zhy.android.R;

import java.util.ArrayList;
import java.util.List;

import adapter.ViewHolder;
import custom.AutoScrollViewPager;
import util.ScreenUtils;

public abstract class BPagerFragment<M> extends BFragment implements
        AutoScrollViewPager.OnPageClickListener {

    protected AutoScrollViewPager mViewPager;
    private View mRootView;
    protected SmartTabLayout indicator;
    protected PagerAdapter adapter;
    protected List<M> mData = new ArrayList<>();
    protected boolean useIndicate = true, isAutoScroll = true;
    protected int itemLayoutId = R.layout.item_avl;
    protected int height = 190;

    {
        contentViewId = R.layout.fragment_pager;
    }

    @Override
    public void success(Object data) {
        if (BList.class.isAssignableFrom(data.getClass())) {
            onData(((BList) data).getList());
            total(((BList) data).getTotal());
        } else if (List.class.isAssignableFrom(data.getClass())) {
            onData((List<M>) data);
        }
    }

    public void onData(List<M> datas) {
        mData.clear();
        mData.addAll(datas);
        upDate();
    }

    @Override
    public void onPageClick(AutoScrollViewPager pager, int position) {
    }

    @Override
    public void initView() {
        super.initView();
        mRootView = findViewById(R.id.mRootView);
        mViewPager = (AutoScrollViewPager) findViewById(R.id.mViewPager);
        indicator = (SmartTabLayout) findViewById(R.id.indicator);
        mViewPager.setScrollFactor(3);
        mViewPager.setOnPageClickListener(this);
        ScreenUtils.setHight(mViewPager, height / 360d);
        upDate();
    }

    private void initAdapter() {
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mData.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                final ViewHolder viewHolder = ViewHolder.get(null, itemLayoutId, position);
                convert(viewHolder, mData.get(position));
                View view = viewHolder.getConvertView();
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        indicator.setViewPager(mViewPager);
        if (isAutoScroll) mViewPager.startAutoScroll();
        if (useIndicate && mData.size() > 1) {
            indicator.setVisibility(View.VISIBLE);
        } else {
            indicator.setVisibility(View.GONE);
        }
    }

    public void upDate() {
        initAdapter();
        if (mData.size() > 0) {
            mRootView.setVisibility(View.VISIBLE);
        } else mRootView.setVisibility(View.GONE);
    }

    public abstract void convert(ViewHolder h, M i);

    @Override
    public void onResume() {
        super.onResume();
        if (isAutoScroll) mViewPager.startAutoScroll();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isAutoScroll) mViewPager.stopAutoScroll();
    }

    public void total(int total) {

    }
}
