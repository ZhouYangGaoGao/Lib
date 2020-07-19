package mvp.square.view;

import base.Manager;
import mvp.chapter.view.ArticleFragment;
import rx.Observable;

public class SquareFragment extends ArticleFragment {
    @Override
    protected Observable<?> get() {
        return Manager.getApi().square(page);
    }
}
