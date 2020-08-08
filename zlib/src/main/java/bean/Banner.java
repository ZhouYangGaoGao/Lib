package bean;

import android.graphics.Color;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.zhy.android.R;

import custom.page.Indicator;
import custom.page.IndicatorView;

public class Banner {
    public int height = 158;//总高度
    public int turningTime = 3000;//滚动间隔
    public int duration = 1000;//滚动时间
    public int indicatorSpacing = 10;//指示标间距
    public int multiWidth = 0;//露出部分宽度
    public int pageMargin = 0;//页间距
    public float radius = 0f;//页圆角
    public float indicatorRadius = 3.5f;
    public float indicatorRadiusSelect = 3.5f;//指示标的圆角
    public float indicatorRatio = 1f;
    public float indicatorRatioSelect = 1.5f;//指示标拉伸比例
    public int itemLayoutId = R.layout.item_avl;
    public int orientation = RecyclerView.HORIZONTAL;//方向
    public int indicatorColor = Color.WHITE;//指示标颜色
    public int indicatorSelectorColor = Color.WHITE;//指示标颜色
    public int indicatorStyle = IndicatorView.IndicatorStyle.INDICATOR_BEZIER;//指示标样式
    public boolean useIndicate = true;//使用指示标
    public boolean autoScroll = true;//是否自动轮播
    public boolean noDataHide = true;//无数据时隐藏
    public Indicator indicator;//自定义指示器,为空时用默认的
    public ViewPager2.PageTransformer transformer;

}
