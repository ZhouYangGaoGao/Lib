package base;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhy.android.R;

import java.util.ArrayList;
import java.util.List;

import adapter.CommonAdapter;
import adapter.ViewHolder;
import background.drawable.DrawableCreator;
import bean.Grid;
import bean.Info;
import bean.Page;
import custom.EmptySizeView;
import custom.HeaderGridView;
import custom.SmartView;
import custom.StatusView;
import bean.Card;
import custom.card.CardView;
import enums.LevelCache;
import enums.LevelDataTime;
import util.LayoutUtil;
import util.ScreenUtils;

public abstract class BSmartFragment<M> extends BFragment<Object, BPresenter<BView<?>>> implements OnRefreshLoadMoreListener {

    private CommonAdapter<M> adapter;
    protected HeaderGridView gridView;
    protected SmartRefreshLayout refreshLayout;
    protected SmartView mSmartView;//根布局
    protected StatusView mStatusView;//状态视图
    protected List<M> mData = new ArrayList<>();//主列表数据
    protected View heardView, footView, topView, bottomView;//其他视图
    protected Grid grid = new Grid();//网格列表信息 间隔/背景/列数/布局
    protected Card card = new Card();//卡片信息
    protected Page page = new Page();//页码参数
    protected Info info = new Info();//其他参数

    {
        levelCache = LevelCache.refresh;
    }

    @Override
    public void initView() {
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshLoadMoreListener(this);
        refreshLayout.setEnableAutoLoadMore(false);
        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        gridView = (HeaderGridView) findViewById(R.id.gridView);
        mSmartView = (SmartView) findViewById(R.id.mTopView);
        if (grid.expand) {
            gridView.setExpand(true);
            refreshLayout.setEnableLoadMore(false);
            refreshLayout.setEnableRefresh(false);
        }
        gridView.setNumColumns(grid.numColumns);
        gridView.setBackgroundColor(grid.bgColor);
        mSmartView.topContent.setVisibility(info.showTop ? View.VISIBLE : View.GONE);
        if (mStatusView == null) {
            mStatusView = new StatusView(getContext());
            if (levelDataTime == LevelDataTime.never) mStatusView.empty();
            mStatusView.getTv().setOnClickListener(v -> {
                mStatusView.loading();
                onRefresh(refreshLayout);
            });
        }
        ((LinearLayout) findViewById(R.id.content)).addView(mStatusView, LayoutUtil.zoomVLp());
        gridView.setEmptyView(mStatusView);
        if (heardView != null) gridView.addHeaderView(heardView);
        if (card != null && card.cardDiver > 0) initCard();
        if (footView != null) gridView.addFooterView(footView);
        if (topView != null) mSmartView.addView(topView, 1);
        if (bottomView != null) mSmartView.addView(bottomView);
        gridView.setAdapter(adapter = initAdapter());
        gridView.setHorizontalSpacing(ScreenUtils.dip2px(grid.horizontalSpacing));
        gridView.setVerticalSpacing(ScreenUtils.dip2px(grid.verticalSpacing));
        mSmartView.centerTextView.setText(title);
    }

    private void initCard() {
        gridView.addHeaderView(new EmptySizeView(card.cardDiver * 4 / 5));
        gridView.addFooterView(new EmptySizeView(card.cardDiver / 5));
        grid.verticalSpacing = card.cardDiver - 10;
        grid.horizontalSpacing = grid.verticalSpacing - (card.cardDiver / 5);
    }

    private CommonAdapter<M> initAdapter() {//初始化主列表适配器
        return new CommonAdapter<M>(getContext(), mData, card.cardDiver > 0 ? R.layout.item_card : grid.itemLayoutId) {

            @Override
            public boolean isEmpty() {//当列表有头部天加时 列表为空要显示头部
                int count = gridView.getHeaderViewCount() + gridView.getFooterViewCount();
                if (count > 2) return false;
                if (card.cardDiver == 0 && count > 0) return false;
                return super.isEmpty();
            }

            @Override
            public void convert(ViewHolder h, M i) {//列表item的初始化 回调
                if (card.cardDiver > 0) initCardItem(h);
                else {
                    h.getConvertView().setBackground(background(new DrawableCreator.Builder()
                            .setRipple(true, card.rippleColor))
                            .build());
                }
                h.setClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClick(h, i);
                    }
                });
                BSmartFragment.this.convert(h, i);
            }
        };
    }

    protected void onItemClick(ViewHolder h, M i) {//列表点击监听
    }

    private void initCardItem(ViewHolder h) {//初始化卡片
        View contentView = getView(grid.itemLayoutId);
        CardView cardView = h.getView(R.id.cardView);
        contentView.setBackground(background(new DrawableCreator.Builder()
                .setRipple(true, card.rippleColor)
                .setCornersRadius(ScreenUtils.dip2px(card.cardRadius)))
                .build());
        cardView.addView(contentView);
        cardView.setRadius(ScreenUtils.dip2px(card.cardRadius));
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) cardView.getLayoutParams();
        params.width = -1;
        params.height = -2;
        params.setMargins(ScreenUtils.dip2px(card.cardDiver), 0, (h.getPosition() + 1)
                % grid.numColumns == 0 ? ScreenUtils.dip2px(card.cardDiver) : 0, 0);
        cardView.setLayoutParams(params);
    }

    protected DrawableCreator.Builder background(DrawableCreator.Builder drawableBuilder) {//设置item 的背景
        return drawableBuilder.setPressedSolidColor(card.cardColorPress, card.cardColor);
    }

    protected void convert(ViewHolder h, M i) {//item回调
        if (h.getView(R.id.title) != null)
            h.setText(R.id.title, new Gson().toJson(i));
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {//加载更多
        info.isRefresh = false;
        page.page++;
        super.getData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {//刷新列表 重新获取数据
        info.isRefresh = true;
        page.resetPage();
        super.getData();
    }

    public void onData(List<M> datas) {//数据处理完成 更新页面
        if (info.isRefresh) mData.clear();
        mData.addAll(datas);
        upData();
    }

    @Override
    public void success(Object data) {//数据加载完成 进行处理
        if (data == null) return;
        if (BList.class.isAssignableFrom(data.getClass())) {
            onData(((BList<M>) data).getList());
            total(((BList<M>) data).getTotal());
        } else if (List.class.isAssignableFrom(data.getClass())) {
            onData((List<M>) data);
        } else fail(new Gson().toJson(data));
    }

    @Override
    public void getData() {
        if (levelCache != null) {
            List<M> data = levelCache.get(TAG);
            if (data != null)
                switch (levelCache) {
                    case only:
                        onData(data);
                        break;
                    case replace:
                        onData(data);
                        getNew();
                        break;
                    case refresh:
                        if (info.isRefresh) getNew();
                        else onData(data);
                        break;
                }
            else getNew();
        } else getNew();
    }

    /**
     * 发起网络请求数据
     */
    private void getNew() {
        mStatusView.loading();
        super.getData();
    }

    protected void upData() {//通知适配器更新数据
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        if (mData != null && levelCache != null) levelCache.cache(TAG, mData);
        super.onStop();
    }

    public void total(int total) {//列表总数量
    }

    @Override
    public void fail(String message) {//数据加载失败 吐司提示
        super.fail(message);
        upData();
    }

    @Override
    public void completed() {//数据加载完成 结束loading
        mStatusView.empty();
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
    }

    public HeaderGridView getGridView() {
        return gridView;
    }
}
