package mvp.home.view;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.zhy.wanandroid.R;

import java.util.List;

import base.Manager;
import base.Subs;
import hawk.Hawk;
import mvp.chapter.model.Article;
import mvp.chapter.view.ArticleFragment;
import rx.Observable;

public class HomeFragment extends ArticleFragment {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void beforeView() {
        heardView = getView(R.layout.fragment_banner);
        isRefresh = false;
    }

    @Override
    protected Observable<?> get() {
        return Manager.getApi().list(page);
    }

    @Override
    public void onData(List<Article> datas) {
        if (isRefresh) mData.clear();
        mData.addAll(datas);
        if (isRefresh) presenter.sub(new Subs<List<Article>>(Manager.getApi().top()) {
            @Override
            public void onSuccess(List<Article> articles) {
                for (Article article : articles) {
                    article.setTop(true);
                }
                mData.addAll(0, articles);
            }

            @Override
            public void onCompleted() {
                upData();
            }
        });
    }
}
