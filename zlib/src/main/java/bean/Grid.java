package bean;

import com.zhy.android.R;

public class Grid {
    public int horizontalSpacing = 1, verticalSpacing = 1;
    public int numColumns = 1;//列数
    public int bgColor = 0xffeeeeee;//列表背景色
    public int itemLayoutId = R.layout.item_text;//item布局
    public boolean expand = false;//列表内嵌套撑开
}
