package mvp.home.view;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.zhy.wanandroid.R;

import java.util.List;

import base.BSub;
import base.Manager;
import hawk.Hawk;
import mvp.chapter.model.Article;
import mvp.chapter.view.ArticleFragment;
import rx.Observable;

public class HomeFragment extends ArticleFragment {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void beforeView() {
        heardView = getView(R.layout.fragment_banner);
        info.isRefresh = !Hawk.contains(TAG);
    }

    @Override
    protected Observable<?> get() {
        return Manager.getApi().list(page.page);
    }

    @Override
    public void onData(List<Article> datas) {
        if (info.isRefresh) mData.clear();
        mData.addAll(datas);
        if (info.isRefresh) presenter.sub(new BSub<List<Article>>(Manager.getApi().top()) {
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
        else upData();
    }
}
