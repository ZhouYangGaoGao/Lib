package mvp.home.view;

import com.zhy.wanandroid.R;

import base.Manager;
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


}
