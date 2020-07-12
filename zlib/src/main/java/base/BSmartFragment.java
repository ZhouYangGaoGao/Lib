package base;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.google.gson.Gson;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhy.android.R;
import com.zhy.android.adapter.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import annotation.Presenter;
import background.drawable.DrawableCreator;
import custom.EmptySizeView;
import custom.HeaderGridView;
import custom.SmartView;
import rx.Subscription;
import util.CardUtils;
import util.ScreenUtils;

public abstract class BSmartFragment<M> extends BFragment implements OnRefreshLoadMoreListener {

    @Presenter
    public BPresenter<BView<M>> presenter;
    private HeaderGridView gridView;
    private boolean isRefresh = true;//是否是刷新
    private CommonAdapter<M> adapter;//GridView 的适配器
    protected SmartRefreshLayout refreshLayout;
    protected SmartView mSmartView;
    protected int horizontalSpacing = 1, verticalSpacing = 1;
    protected View heardView, footView, emptyView, topView, bottomView, refreshHeard, refreshFoot;
    protected boolean scrollAble = false;//* 可滑动
    protected boolean showTopBar = true;
    protected int isCard = 0;//是否卡片模式 0:否  >0:间距
    protected int cardRadius = 10;//卡片圆角
    protected int cardColor = 0x00ffffff;//卡片背景色 正常
    protected int cardPressedColor = 0x11000000;//卡片背景色 按压
    protected int page = 1;//页码
    protected List<M> mData = new ArrayList<>();//主列表数据
    protected int numColumns = 1;//列数
    protected String type = "";//预留参数 类型
    protected int index = 0;//预留参数 下标
    protected int itemLayoutId = R.layout.item_text;//item布局
    protected int rippleColor = 0x33000000;//item水波纹颜色 card模式有效

    @Override
    public void initView() {
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshLoadMoreListener(this);
        refreshLayout.setEnableAutoLoadMore(false);
        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        gridView = (HeaderGridView) findViewById(R.id.gridView);
        mSmartView = (SmartView) findViewById(R.id.mTopView);
        gridView.scrollAble = scrollAble;
        refreshLayout.setEnableLoadMore(!scrollAble);
        refreshLayout.setEnableRefresh(!scrollAble);
        gridView.setNumColumns(numColumns);
        mSmartView.topContent.setVisibility(showTopBar ? View.VISIBLE : View.GONE);
        if (emptyView == null) emptyView = getView(R.layout.layout_empty);
        ((LinearLayout) findViewById(R.id.content)).addView(emptyView,
                new LinearLayout.LayoutParams(-1, 0, 1));
        gridView.setEmptyView(emptyView);
        if (heardView != null) gridView.addHeaderView(heardView);
        if (isCard > 0) initCard();
        if (footView != null) gridView.addFooterView(footView);
        if (topView != null) mSmartView.addView(topView, 1);
        if (bottomView != null) mSmartView.addView(bottomView);
        if (refreshHeard != null)
            ((RelativeLayout) findViewById(R.id.refreshHeader)).addView(refreshHeard,0);
        if (refreshFoot != null)
            ((RelativeLayout) findViewById(R.id.refreshFooter)).addView(refreshFoot);

        gridView.setAdapter(adapter = initAdapter());
        gridView.setHorizontalSpacing(ScreenUtils.dip2px(horizontalSpacing));
        gridView.setVerticalSpacing(ScreenUtils.dip2px(verticalSpacing));
        mSmartView.centerTextView.setText(title);
    }

    private void initCard() {
        gridView.addHeaderView(new EmptySizeView(isCard * 4 / 5));
        gridView.addFooterView(new EmptySizeView(isCard / 5));
        verticalSpacing = isCard - 10;
        horizontalSpacing = verticalSpacing - (isCard / 5);
    }

    @Override
    public void getData() {
        if (presenter != null) {
            if (!presenter.sub(get())) {
                completed();
            }
        } else {
            completed();
        }
    }

    protected Subscription get() {
        return null;
    }//数据来源

    private CommonAdapter<M> initAdapter() {//初始化主列表适配器
        return new CommonAdapter<M>(getContext(), mData, isCard > 0 ? R.layout.item_card : itemLayoutId) {

            @Override
            public boolean isEmpty() {//当列表有头部天加时 列表为空要显示头部
                int count = gridView.getHeaderViewCount() + gridView.getFooterViewCount();
                if (count > 2) return false;
                if (isCard == 0 && count > 0) return false;
                return super.isEmpty();
            }


            @Override
            public void convert(ViewHolder h, M i) {//列表item的初始化 回调
                if (isCard > 0) initCardItem(h);
                else setBg(h.getConvertView());
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

    protected void onItemClick(CommonAdapter.ViewHolder h, M i) {//列表点击监听
    }

    private void initCardItem(CommonAdapter.ViewHolder h) {//初始化卡片
        CardView cardView = h.getView(R.id.cardView);
        cardView.addView(setBg(getView(itemLayoutId)));
        cardView.setRadius(ScreenUtils.dip2px(cardRadius));
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) cardView.getLayoutParams();
        params.width = -1;
        params.height = -2;
        params.setMargins(ScreenUtils.dip2px(isCard), 0, (h.getmPosition() + 1) % numColumns == 0 ? ScreenUtils.dip2px(isCard) : 0, 0);
        cardView.setLayoutParams(params);
        CardUtils.setCardShadowColor(cardView, getResources().getColor(R.color.start_color), getResources().getColor(R.color.end_color));
    }

    @SuppressLint("NewApi")
    protected View setBg(View view) {
        view.setForeground(new DrawableCreator.Builder()
                .setRipple(true, rippleColor)
                .setCornersRadius(ScreenUtils.dip2px(isCard > 0 ? cardRadius : 0))
                .setPressedSolidColor(cardPressedColor, cardColor)
                .build());
        return view;
    }

    /**
     * item回调
     */
    protected void convert(CommonAdapter.ViewHolder h, M i) {
        if (h.getView(R.id.title) != null)
            h.setText(R.id.title, new Gson().toJson(i));
    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        isRefresh = false;
        page++;
        getData();
    }

    /**
     * 刷新列表 重新获取数据
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        isRefresh = true;
        page = 1;
        getData();
    }

    //    /**
//     * 数据加载成功 更新UI
//     */
//    @Override
    public void onData(List<M> datas) {
        if (isRefresh) mData.clear();
        mData.addAll(datas);
        upData();
    }

    @Override
    public void success(Object data) {
        if (data == null) return;
        if (BList.class.isAssignableFrom(data.getClass())) {
            onData(((BList<M>) data).getList());
            total(((BList<M>) data).getTotal());
        } else if (List.class.isAssignableFrom(data.getClass())) {
            onData((List<M>) data);
        } else fail(new Gson().toJson(data));
    }

    /**
     * 通知适配器更新数据
     */
    protected void upData() {
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    /**
     * 列表总数量
     */
    public void total(int total) {
    }

    /**
     * 数据加载失败 吐司提示
     */
    @Override
    public void fail(String message) {
        super.fail(message);
        upData();
    }

    /**
     * 数据加载完成 结束loading
     */
    @Override
    public void completed() {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
    }
}
