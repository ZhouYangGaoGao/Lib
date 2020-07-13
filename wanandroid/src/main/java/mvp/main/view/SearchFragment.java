package mvp.main.view;

import com.zhy.wanandroid.R;

import base.BSmartFragment;
import custom.SmartView;
import listener.OnSmartClickListener;
import mvp.chapter.model.Article;

public class SearchFragment extends BSmartFragment<Article> implements OnSmartClickListener {
    @Override
    public void beforeView() {
        isCard = 10;
    }

    @Override
    public void afterView() {
        mSmartView.search().initHistory(true, R.layout.fragment_pop);
        mSmartView.rightTextView.setText("搜索");
        mSmartView.setListener(this);
    }

    @Override
    public void onClick(SmartView smartView, int textViewIndex, int drawableIndex) {
        toast("smartView=" + "clickView" + "\ntextView=" + textViewIndex + "\ndrawable=" + drawableIndex);
    }
}
