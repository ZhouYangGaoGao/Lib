package mvp.home.view;

import com.zhy.wanandroid.R;

import java.util.List;

import base.Manager;
import base.Subs;
import mvp.chapter.model.Article;
import mvp.chapter.view.ArticleFragment;
import rx.Observable;

public class HomeFragment extends ArticleFragment {
    @Override
    public void beforeView() {
        heardView = getView(R.layout.fragment_banner);
    }

    @Override
    protected Observable<?> get() {
        return Manager.getApi().list(page);
    }

    @Override
    public void onData(List<Article> datas) {
        super.onData(datas);
        if (isRefresh) presenter.sub(new Subs<List<Article>>(Manager.getApi().top()) {
            @Override
            public void onSuccess(List<Article> articles) {
                for (Article article : articles) {
                    article.setTop(true);
                }
                mData.addAll(0, articles);
                upData();
            }
        });
    }
}
