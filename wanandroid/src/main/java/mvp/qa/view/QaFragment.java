package mvp.qa.view;

import base.Manager;
import mvp.chapter.view.ArticleFragment;
import rx.Observable;

public class QaFragment extends ArticleFragment {
    @Override
    public void beforeView() {
        startPage=1;
        showTopBar=true;
    }

    @Override
    protected Observable<?> get() {
        return Manager.getApi().qa(page);
    }
}
