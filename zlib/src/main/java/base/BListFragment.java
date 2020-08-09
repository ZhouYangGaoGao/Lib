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
import bean.Fresh;
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

public abstract class BListFragment<M> extends BFragment<Object, BPresenter<BView<?>>> implements OnRefreshLoadMoreListener {

    private CommonAdapter<M> adapter;
    protected HeaderGridView gridView;
    protected SmartRefreshLayout refreshLayout;
    protected SmartView mSmartView;//根布局
    protected StatusView mStatusView;//状态视图
    protected List<M> mData = new ArrayList<>();//主列表数据
    protected View heardView, footView, topView, bottomView;//其他视图
    protected Card card = new Card();//卡片信息
    protected Grid grid = new Grid();//网格列表信息 间隔/背景/列数/布局
    protected Page page = new Page();//页码参数
    protected Fresh fresh = new Fresh();//刷新参数

    {
        info.levelCache = LevelCache.refresh;
    }

    @Override
    public void initView() {
        gridView = (HeaderGridView) findViewById(R.id.gridView);
        mSmartView = (SmartView) findViewById(R.id.mTopView);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshLoadMoreListener(this);
        refreshLayout.setEnableAutoLoadMore(fresh.autoLoadMore);
        refreshLayout.setEnableLoadMoreWhenContentNotFull(fresh.loadNotFull);
        refreshLayout.setBackgroundColor(fresh.bgColor);
        refreshLayout.setEnableLoadMore(!grid.expand && fresh.loadMore);
        refreshLayout.setEnableRefresh(!grid.expand && fresh.refresh);
        gridView.setExpand(grid.expand);
        gridView.setNumColumns(grid.numColumns);
        gridView.setBackgroundColor(grid.bgColor);
        mSmartView.topContent.setVisibility(info.showTop ? View.VISIBLE : View.GONE);
        if (mStatusView == null) {
            mStatusView = new StatusView(getContext()) {
                @Override
                public void onClick(View v) {
                    super.onClick(v);
                    onRefresh(refreshLayout);
                }
            };
        }
        if (levelDataTime == LevelDataTime.never) mStatusView.empty();
        ((LinearLayout) findViewById(R.id.content)).addView(mStatusView, 0, LayoutUtil.zoomVLp());
        gridView.setEmptyView(mStatusView);
        if (heardView != null) gridView.addHeaderView(heardView);
        if (card.card) initCard();
        if (footView != null) gridView.addFooterView(footView);
        if (topView != null) mSmartView.addView(topView, 1);
        if (bottomView != null) mSmartView.addView(bottomView);
        gridView.setAdapter(adapter = initAdapter());
        gridView.setHorizontalSpacing(ScreenUtils.dip2px(grid.horizontalSpacing));
        gridView.setVerticalSpacing(ScreenUtils.dip2px(grid.verticalSpacing));
        mSmartView.centerTextView.setText(info.title);
    }

    private void initCard() {
        grid.horizontalSpacing = grid.verticalSpacing = 0;
        if (card.needHeardSpace)
            gridView.addHeaderView(new EmptySizeView(card.cardElevation));
        if (card.needFootSpace)
            gridView.addFooterView(new EmptySizeView(card.cardElevation));
    }

    private CommonAdapter<M> initAdapter() {//初始化主列表适配器
        return new CommonAdapter<M>(getContext(), mData, card.card ? R.layout.item_card : grid.itemLayoutId) {

            @Override
            public boolean isEmpty() {//当列表有头部天加时 列表为空要显示头部
                int headerCount = gridView.getHeaderViewCount();
                int footCount = gridView.getFooterViewCount();
                if (card.card) {
                    if (card.needHeardSpace) headerCount--;
                    if (card.needFootSpace) footCount--;
                }
                if (headerCount + footCount > 0 && mData.size() == 0) return false;
                return mData.size() == 0;
            }

            @Override
            public void convert(ViewHolder h, M i) {//列表item的初始化 回调
                if (card.card) initCardItem(h);
                else {
                    h.getConvertView().setBackground(new DrawableCreator.Builder()
                            .setRipple(true, card.rippleColor)
                            .setPressedSolidColor(card.cardColorPress, card.cardColor)
                            .build());
                }
                h.setClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClick(h, i);
                    }
                });
                BListFragment.this.convert(h, i);
            }
        };
    }

    protected void onItemClick(ViewHolder h, M i) {//列表点击监听
    }

    private void initCardItem(ViewHolder h) {//初始化卡片
        View contentView = getView(grid.itemLayoutId);
        CardView cardView = h.getView(R.id.cardView);
        cardView.setShadowColor(card.cardElevationStart, card.cardElevationEnd);
        contentView.setBackground(new DrawableCreator.Builder()
                .setRipple(true, card.rippleColor)
                .setPressedSolidColor(card.cardColorPress, card.cardColor)
                .setCornersRadius(ScreenUtils.dip2px(card.cardRadius))
                .build());
        cardView.addView(contentView);
        cardView.setMaxCardElevation(ScreenUtils.dip2px(card.cardElevation));
        cardView.setRadius(ScreenUtils.dip2px(card.cardRadius));
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) cardView.getLayoutParams();
        params.setMargins(getMargin(h.getPosition()), 0, getMargin(h.getPosition() + 1), 0);
        cardView.setLayoutParams(params);
    }

    private int getMargin(int point) {
        return ScreenUtils.dip2px(point % grid.numColumns == 0 ? card.cardElevation : 0);
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
        mStatusView.empty("数据加载完成 进行处理");
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
        if (info.needNew(new Info.DataListener<List<M>>() {
            @Override
            public void onData(List<M> t) {
                BListFragment.this.onData(t);
            }
        })) getNew();
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
        info.save(mData);
        super.onStop();
    }

    public void total(int total) {//列表总数量
    }

    @Override
    public void fail(String message) {//数据加载失败 吐司提示
        super.fail(message);
        mStatusView.error(message + "\n点击重试");
        upData();
    }

    @Override
    public void completed() {//数据加载完成 结束loading
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
    }

    public HeaderGridView getGridView() {
        return gridView;
    }
}
