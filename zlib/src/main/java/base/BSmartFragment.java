package base;

import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.google.gson.Gson;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhy.android.R;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import adapter.CommonAdapter;
import adapter.ViewHolder;
import background.drawable.DrawableCreator;
import custom.EmptySizeView;
import custom.HeaderGridView;
import custom.SmartView;
import hawk.Hawk;
import util.CardUtils;
import util.LayoutUtil;
import util.ScreenUtils;

public abstract class BSmartFragment<M> extends BFragment<Object, BPresenter<BView<?>>> implements OnRefreshLoadMoreListener {

    private CommonAdapter<M> adapter;
    protected HeaderGridView gridView;
    protected SmartRefreshLayout refreshLayout;
    protected SmartView mSmartView;
    protected String type = "";//预留参数 类型
    protected List<M> mData = new ArrayList<>();//主列表数据
    protected View heardView, footView, emptyView, topView, bottomView;
    protected int index = 0;//预留参数 下标
    protected int horizontalSpacing = 1, verticalSpacing = 1;
    protected int numColumns = 1;//列数
    protected int isCard = 0;//是否卡片模式 0:否  >0:间距
    protected int cardRadius = 10;//卡片圆角
    protected int cardColor = 0xffffffff;//卡片背景色 正常
    protected int cardColorPress = 0x11000000;//卡片背景色 按压
    protected int bgColor = 0xffffffff;//列表背景色
    protected int startPage = 1;//起始页码
    protected int page = 1;//页码
    protected int itemLayoutId = R.layout.item_text;//item布局
    protected int rippleColor = 0x33000000;//item水波纹颜色
    protected boolean isRefresh = false;//是否是刷新
    protected boolean scrollAble = false;//* 可滑动
    protected boolean showTopBar = true;

    @Override
    public void initView() {
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshLoadMoreListener(this);
        refreshLayout.setEnableAutoLoadMore(false);
        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        gridView = (HeaderGridView) findViewById(R.id.gridView);
        mSmartView = (SmartView) findViewById(R.id.mTopView);
        gridView.scrollAble = scrollAble;
        refreshLayout.setEnablePureScrollMode(scrollAble);
        gridView.setNumColumns(numColumns);
        gridView.setBackgroundColor(bgColor);
        mSmartView.topContent.setVisibility(showTopBar ? View.VISIBLE : View.GONE);
        if (emptyView == null) emptyView = getView(R.layout.layout_empty);
        ((LinearLayout) findViewById(R.id.content)).addView(emptyView, LayoutUtil.zoomVLp());
        gridView.setEmptyView(emptyView);
        if (heardView != null) gridView.addHeaderView(heardView);
        if (isCard > 0) initCard();
        if (footView != null) gridView.addFooterView(footView);
        if (topView != null) mSmartView.addView(topView, 1);
        if (bottomView != null) mSmartView.addView(bottomView);
        gridView.setAdapter(adapter = initAdapter());
        gridView.setHorizontalSpacing(ScreenUtils.dip2px(horizontalSpacing));
        gridView.setVerticalSpacing(ScreenUtils.dip2px(verticalSpacing));
        mSmartView.centerTextView.setText(title);
        page = startPage;
    }

    private void initCard() {
        gridView.addHeaderView(new EmptySizeView(isCard * 4 / 5));
        gridView.addFooterView(new EmptySizeView(isCard / 5));
        verticalSpacing = isCard - 10;
        horizontalSpacing = verticalSpacing - (isCard / 5);
    }

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
                else {
                    h.getConvertView().setBackground(background(new DrawableCreator.Builder()
                            .setRipple(true, rippleColor))
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

    public Object getCache() {

        return null;
    }


    protected void onItemClick(ViewHolder h, M i) {//列表点击监听
    }

    private void initCardItem(ViewHolder h) {//初始化卡片
        View contentView = getView(itemLayoutId);
        CardView cardView = h.getView(R.id.cardView);
        contentView.setBackground(background(new DrawableCreator.Builder()
                .setRipple(true, rippleColor)
                .setCornersRadius(ScreenUtils.dip2px(cardRadius)))
                .build());
        cardView.addView(contentView);
        cardView.setRadius(ScreenUtils.dip2px(cardRadius));
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) cardView.getLayoutParams();
        params.width = -1;
        params.height = -2;
        params.setMargins(ScreenUtils.dip2px(isCard), 0, (h.getPosition() + 1) % numColumns == 0 ? ScreenUtils.dip2px(isCard) : 0, 0);
        cardView.setLayoutParams(params);
        CardUtils.setCardShadowColor(cardView, getResources().getColor(R.color.clo_card_shadow_start), getResources().getColor(R.color.clo_card_shadow_end));
    }

    protected DrawableCreator.Builder background(DrawableCreator.Builder drawableBuilder) {//设置item 的背景
        return drawableBuilder.setPressedSolidColor(cardColorPress, cardColor);
    }

    protected void convert(ViewHolder h, M i) {//item回调
        if (h.getView(R.id.title) != null)
            h.setText(R.id.title, new Gson().toJson(i));
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {//加载更多
        isRefresh = false;
        page++;
        super.getData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {//刷新列表 重新获取数据
        isRefresh = true;
        page = startPage;
        super.getData();
    }

    public void onData(List<M> datas) {//数据处理完成 更新页面
        if (isRefresh) mData.clear();
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
        if (useCache && !TextUtils.isEmpty(cacheKey) && Hawk.contains(cacheKey)) {
            onData(Hawk.get(cacheKey, new ArrayList<M>()));
        } else
            super.getData();
    }

    protected void upData() {//通知适配器更新数据
        if (adapter != null)
            adapter.notifyDataSetChanged();
        if (mData != null && useCache && !TextUtils.isEmpty(cacheKey)) Hawk.put(cacheKey, mData);
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
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
    }

    public HeaderGridView getGridView() {
        return gridView;
    }
}
