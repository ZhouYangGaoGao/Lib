package base;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhy.android.BuildConfig;
import com.zhy.android.R;
import com.zhy.android.adapter.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import annotation.InjectPresenter;
import background.drawable.DrawableCreator;
import custom.EmptySizeView;
import custom.HeaderGridView;
import custom.ShadowLayout;
import custom.SmartView;
import rx.Observable;
import util.ScreenUtils;

public abstract class SmartFragment<M> extends BFragment<M, SmartPresenter<M>> implements ISmartContract.View<M>, OnRefreshLoadMoreListener {
    @InjectPresenter
    public SmartPresenter presenter;
    private HeaderGridView gridView;
    protected SmartRefreshLayout refreshLayout;
    protected SmartView mTopView;

    @Override
    public void initView() {
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshLoadMoreListener(this);
        refreshLayout.setEnableAutoLoadMore(autoLoad);
        LinearLayout content = (LinearLayout) findViewById(R.id.content);
        gridView = (HeaderGridView) findViewById(R.id.gridView);
        mTopView = (SmartView) findViewById(R.id.mTopView);
        gridView.scrollAble = scrollAble;
        refreshLayout.setEnableLoadMore(!scrollAble);
        refreshLayout.setEnableRefresh(!scrollAble);
        gridView.setNumColumns(numColumns);
        if (emptyView == null) {
            emptyView = getView(R.layout.layout_empty);
        }
        content.addView(emptyView, new LinearLayout.LayoutParams(-1, 0, 1));
        gridView.setEmptyView(emptyView);
        if (heardView != null) gridView.addHeaderView(heardView);
        if (isCard > 0) {
            horizontalSpacing = 0;
            verticalSpacing = 0;
            gridView.addHeaderView(new EmptySizeView(isCard / 2));
            gridView.addFooterView(new EmptySizeView(isCard / 2));
        }
        if (footView != null) gridView.addFooterView(footView);
        gridView.setAdapter(adapter = initAdapter());
        gridView.setHorizontalSpacing(ScreenUtils.dip2px(horizontalSpacing));
        gridView.setVerticalSpacing(ScreenUtils.dip2px(verticalSpacing));
        if (BuildConfig.DEBUG) mTopView.centerTextView.setText(getClass().getSimpleName());
    }

    private int horizontalSpacing = 1, verticalSpacing = 1;
    protected View heardView, footView, emptyView;

    /**
     * 可滑动
     */
    protected boolean scrollAble = false;

    /**
     * 是否卡片模式 0:否  >0:间距
     */
    protected int isCard = 0;

    /**
     * 卡片圆角
     */
    protected int cardRadius = 10;

    /**
     * 卡片背景色 正常
     */
    protected int cardColor = 0xffffffff;

    /**
     * 卡片背景色 按压
     */
    protected int cardPressedColor = 0x88cccccc;

    /**
     * 自动加载更多
     */
    private boolean autoLoad = false;

    /**
     * listView 和 GridView 的适配器
     */
    private CommonAdapter adapter;
    /**
     * 页码
     */
    private int page = 1;

    /**
     * 主列表数据
     */
    protected List<M> mData = new ArrayList<>();

    /**
     * 列数
     */
    protected int numColumns = 1;

    /**
     * 预留参数 类型
     */
    protected String type = "";

    /**
     * 预留参数 下标
     */
    protected int index = 0;

    /**
     * 是否是刷新
     */
    private boolean isRefresh = true;

    /**
     * item布局
     */
    protected int itemLayoutId = R.layout.item_avl;

    @Override
    public void getData() {
        if (presenter != null) {
            presenter.getDatas();
        } else {
            log("presenter=null");
            completed();
        }
    }

    /**
     * 初始化主列表适配器
     *
     * @return
     */
    private CommonAdapter initAdapter() {
        return new CommonAdapter<M>(getContext(), mData, isCard > 0 ? R.layout.item_card : itemLayoutId) {
            /**
             * 当列表有头部天加时 列表为空要显示头部
             * @return
             */
            @Override
            public boolean isEmpty() {
                int count = gridView.getHeaderViewCount() + gridView.getFooterViewCount();
                if (count > 2) return false;
                if (isCard == 0 && count > 0) return false;
                return super.isEmpty();
            }

            /**
             * 列表item的初始化 回调
             * @param h
             * @param i
             */
            @Override
            public void convert(ViewHolder h, M i) {
                initCard(h);
                SmartFragment.this.convert(h, i);
            }
        };
    }

    private void initCard(CommonAdapter.ViewHolder h) {
        if (isCard > 0) {
            ShadowLayout card = (ShadowLayout) h.getConvertView();
            card.setShadowRadius(ScreenUtils.dip2px(isCard / 2f));
            View view = View.inflate(getContext(), itemLayoutId, null);
            view.setBackground(itemBg());
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-1, -1);
            params.setMargins(ScreenUtils.dip2px(isCard / 2f), 0, ScreenUtils.dip2px(isCard / 2f), 0);
            card.addView(view, params);
        }
    }

    protected Drawable itemBg() {
        return new DrawableCreator.Builder()
                .setRipple(true, 0x88888888)
                .setCornersRadius(ScreenUtils.dip2px(cardRadius))
                .setPressedSolidColor(cardPressedColor, cardColor)
                .build();
    }

    /**
     * item回调
     *
     * @param h
     * @param i 数据源
     */
    protected void convert(CommonAdapter.ViewHolder h, M i) {
    }

    /**
     * 加载不可分页列表数据
     *
     * @return
     */
    @Override
    public Observable<BResponse<List<M>>> getList() {
        return null;
    }

    /**
     * 加载可分页列表数据
     *
     * @return
     */
    @Override
    public Observable<BResponse<BList<M>>> getPageList() {
        return null;
    }

    /**
     * 加载更多
     *
     * @param refreshLayout
     */
    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        isRefresh = false;
        page++;
        getData();
    }

    /**
     * 刷新列表 重新获取数据
     *
     * @param refreshLayout
     */
    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        isRefresh = true;
        page = 1;
        getData();
    }

    /**
     * 数据加载成功 更新UI
     *
     * @param datas
     */
    @Override
    public void onDatas(List<M> datas) {
        if (isRefresh) mData.clear();
        mData.addAll(datas);
        upData();
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
     *
     * @param total
     */
    @Override
    public void total(int total) {

    }

    /**
     * 数据加载失败 吐司提示
     *
     * @param message
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
