package mvp.main.view;

import com.zhy.wanandroid.R;

import adapter.ViewHolder;
import base.BConfig;
import base.Manager;
import custom.SmartView;
import listener.SmartListener;
import mvp.chapter.model.Article;
import mvp.chapter.view.ArticleFragment;
import rx.Observable;

public class SearchFragment extends ArticleFragment implements SmartListener {
    @Override
    public void beforeView() {
        showTopBar = true;
        preData = BConfig.GET_DATA_NEVER;
    }

    @Override
    public void afterView() {
        mSmartView.search().initHistory(true, R.layout.fragment_pop);
        mSmartView.rightTextView.setText("搜索");
        mSmartView.setListener(this, 2);
    }

    @Override
    protected void convert(ViewHolder h, Article i) {
        super.convert(h, i);
        h.setTextHtml(R.id.title, i.getTitle());
    }

    @Override
    public void onClick(SmartView smartView, int textViewIndex, int drawableIndex) {
        if (textViewIndex > 1 && !mSmartView.actionErrorCheck()) {
            type = mSmartView.getText();
            getData();
        }
    }

    @Override
    protected Observable<?> get() {
        return Manager.getApi().query(page, type);
    }
}