package base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback;

import com.zhy.android.R;

import adapter.group.BaseViewHolder;
import bean.Banner;
import custom.page.AutoPager;
import custom.page.IndicatorView;
import util.ScreenUtils;

public abstract class BBannerFragment<M> extends BListDataFragment<M> {

    protected AutoPager mViewPager = new AutoPager(BApp.app().act());
    protected OnPageChangeCallback pageChangeCallback;
    protected RelativeLayout mRootView;
    protected RecyclerView.Adapter<BaseViewHolder> adapter;
    protected Banner banner = new Banner();

    {
        contentView = mViewPager;
    }

    @Override
    public void initView() {
        if (banner.useIndicate) {
            if (banner.indicator == null) {
                banner.indicator = new IndicatorView(getContext())
                        .setIndicatorColor(banner.indicatorColor)
                        .setIndicatorSpacing(banner.indicatorSpacing)
                        .setIndicatorRadius(banner.indicatorRadius)
                        .setIndicatorRatio(banner.indicatorRatio)
                        .setIndicatorSelectedRadius(banner.indicatorRadiusSelect)
                        .setIndicatorSelectedRatio(banner.indicatorRatioSelect)
                        .setIndicatorSelectorColor(banner.indicatorSelectorColor)
                        .setIndicatorStyle(banner.indicatorStyle);
            }
            mViewPager.setIndicator(banner.indicator);
        }
        mViewPager.setOrientation(banner.orientation);
        mViewPager.setAutoTurningTime(banner.turningTime);
        mViewPager.setPagerScrollDuration(banner.duration);
        mViewPager.setAutoPlay(banner.autoScroll);
        mViewPager.setPageMargin(banner.multiWidth, banner.pageMargin);
        mViewPager.setRoundCorners(banner.radius);
        mViewPager.setOnPageChangeListener(pageChangeCallback);
        if (banner.transformer != null)
            mViewPager.addPageTransformer(banner.transformer);
        ScreenUtils.setHeight(mViewPager, banner.height / 360d);
        initAdapter();
    }

    private void initAdapter() {
        mViewPager.setAdapter(adapter = new RecyclerView.Adapter<BaseViewHolder>() {
            @NonNull
            @Override
            public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new BaseViewHolder(LayoutInflater.from(BApp.app().act())
                        .inflate(banner.itemLayoutId, parent, false));
            }

            @Override
            public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
                convert(holder.setGroupPosition(position), mData.get(position));
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        });
    }

    @Override
    protected void upData() {
        adapter.notifyDataSetChanged();
        if (!banner.noDataHide) return;
        if (mData.size() > 0) {
            mViewPager.setVisibility(View.VISIBLE);
        } else mViewPager.setVisibility(View.GONE);
    }

    protected abstract void convert(BaseViewHolder h, M i);
}
