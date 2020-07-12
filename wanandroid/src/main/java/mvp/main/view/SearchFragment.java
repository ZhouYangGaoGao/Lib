package mvp.main.view;

import base.BSmartFragment;
import mvp.chapter.model.Article;

public class SearchFragment extends BSmartFragment<Article> {
    @Override
    public void beforeView() {
        isCard = 10;
    }

    @Override
    public void afterView() {
        mSmartView.search();
    }
}
