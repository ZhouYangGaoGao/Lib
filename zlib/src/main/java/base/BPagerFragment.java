package base;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zhy.android.R;

import java.util.ArrayList;
import java.util.List;

import adapter.ViewHolder;
import adapter.group.BaseViewHolder;
import custom.AutoScrollViewPager;
import custom.page.AutoPager;
import custom.page.IndicatorView;
import util.ScreenUtils;

public abstract class BPagerFragment<M> extends BFragment {

    protected AutoPager mViewPager;
    private View mRootView;
    protected IndicatorView indicator;
    protected RecyclerView.Adapter adapter;
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
    public void initView() {
        mRootView = findViewById(R.id.mRootView);
        mViewPager = (AutoPager) findViewById(R.id.mViewPager);
        indicator = (IndicatorView) findViewById(R.id.indicator);
        mViewPager.setIndicator(indicator,false);
        ScreenUtils.setHight(mViewPager, height / 360d);
        initAdapter();
    }

    private void initAdapter() {
        mViewPager.setAdapter(adapter = new RecyclerView.Adapter<BaseViewHolder>() {
            @NonNull
            @Override
            public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                return new BaseViewHolder(LayoutInflater.from(getContext()).inflate(itemLayoutId, parent, false));
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
        mViewPager.setAutoPlay(isAutoScroll);
        if (useIndicate && mData.size() > 1) {
            indicator.setVisibility(View.VISIBLE);
        } else {
            indicator.setVisibility(View.GONE);
        }
    }

    public void upDate() {
        adapter.notifyDataSetChanged();
        if (mData.size() > 0) {
            mRootView.setVisibility(View.VISIBLE);
        } else mRootView.setVisibility(View.GONE);
    }

    public abstract void convert(BaseViewHolder h, M i);

    public void total(int total) {
    }

//    new IndicatorView(getContext())
//            .setIndicatorRatio(1f)
//                .setIndicatorRadius(2f)
//                .setIndicatorSelectedRatio(3)
//                .setIndicatorSelectedRadius(2f)
//                .setIndicatorStyle(IndicatorView.IndicatorStyle.INDICATOR_BIG_CIRCLE)
//                .setIndicatorColor(Color.GRAY)
//                .setIndicatorSelectorColor(Color.WHITE)
}
