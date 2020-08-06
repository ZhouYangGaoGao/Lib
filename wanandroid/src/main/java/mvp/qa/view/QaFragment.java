package mvp.qa.view;

import base.Manager;
import mvp.chapter.view.ArticleFragment;
import rx.Observable;

public class QaFragment extends ArticleFragment {
    @Override
    public void beforeView() {
        page.setStartPage(1);
        info.showTop = true;
    }

    @Override
    protected Observable<?> get() {
        return Manager.getApi().qa(page.page);
    }
}
