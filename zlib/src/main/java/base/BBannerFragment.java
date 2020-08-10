package base;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback;

import com.zhy.android.R;

import java.util.ArrayList;
import java.util.List;

import adapter.group.BaseViewHolder;
import bean.Banner;
import custom.page.AutoPager;
import custom.page.IndicatorView;
import util.ScreenUtils;

public abstract class BBannerFragment<M> extends BFragment {

    protected AutoPager mViewPager;
    protected OnPageChangeCallback pageChangeCallback;
    protected RelativeLayout mRootView;
    protected RecyclerView.Adapter<BaseViewHolder> adapter;
    protected List<M> mData = new ArrayList<>();
    protected Banner banner = new Banner();

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

    @SuppressLint("WrongConstant")
    @Override
    public void initView() {
        mRootView = (RelativeLayout) findViewById(R.id.mRootView);
        mViewPager = (AutoPager) findViewById(R.id.mViewPager);
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
        if (banner.transformer!=null)
        mViewPager.addPageTransformer(banner.transformer);
        ScreenUtils.setHight(mViewPager, banner.height / 360d);
        initAdapter();
    }

    private void initAdapter() {
        mViewPager.setAdapter(adapter = new RecyclerView.Adapter<BaseViewHolder>() {
            @NonNull
            @Override
            public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new BaseViewHolder(LayoutInflater.from(BApp.app()).inflate(banner.itemLayoutId, parent, false));
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

    protected void upDate() {
        adapter.notifyDataSetChanged();
        if (!banner.noDataHide) return;
        if (mData.size() > 0) {
            mRootView.setVisibility(View.VISIBLE);
        } else mRootView.setVisibility(View.GONE);
    }

    protected abstract void convert(BaseViewHolder h, M i);

    protected void total(int total) {
    }
}
