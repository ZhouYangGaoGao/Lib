package mvp.home.view;

import java.util.List;

import base.BaseBean;
import base.Manager;
import mvp.chapter.model.Article;
import mvp.chapter.view.ArticleFragment;
import rx.Observable;

public class CollectFragment extends ArticleFragment {
    @Override
    public void beforeView() {
        showTopBar = true;
    }

    @Override
    protected Observable<?> get() {
        return Manager.getApi().collectList(page);
    }

    @Override
    public void onData(List<Article> datas) {
        for (Article article : datas) {
            article.setCollect(true);
        }
        super.onData(datas);
    }

    @Override
    protected Observable<BaseBean<Object>> unCollect(Article i) {
        return Manager.getApi().unMyCollect(i.getId(), i.getOriginId() == 0 ? -1 : i.getOriginId());
    }
}
