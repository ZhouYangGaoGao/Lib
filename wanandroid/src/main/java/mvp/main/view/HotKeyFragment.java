package mvp.main.view;

import com.zhy.wanandroid.R;

import adapter.ViewHolder;
import base.BSmartFragment;
import base.Manager;
import mvp.chapter.model.Article;
import rx.Observable;

public class HotKeyFragment extends BSmartFragment<Article> {
    @Override
    public void beforeView() {
        isCard = 5;
        numColumns = 3;
        showTopBar = false;
        scrollAble = false;
    }

    @Override
    public void afterView() {
        refreshLayout.setEnablePureScrollMode(true);
    }

    @Override
    protected Observable<?> get() {
        return Manager.getApi().hotKey();
    }

    @Override
    protected void convert(ViewHolder h, Article i) {
        h.setText(R.id.title, i.getTitle());
    }
}
