package mvp.home.view;

import com.zhy.wanandroid.R;

import adapter.ViewHolder;
import base.Manager;
import custom.TextView;
import mvp.chapter.model.Article;
import mvp.chapter.view.ArticleFragment;
import rx.Observable;

public class FriendFragment extends ArticleFragment {

    @Override
    public void beforeView() {
        numColumns = 2;
        showTopBar = true;
    }

    @Override
    protected void convert(ViewHolder h, Article i) {
        TextView title = h.getView(R.id.title);
        title.setSingleLine();
        title.setAutoZoom(true);
        title.setText(i.getTitle());
    }

    @Override
    public void afterView() {
        refreshLayout.setEnablePureScrollMode(true);
    }

    @Override
    protected Observable<?> get() {
        return Manager.getApi().friend();
    }

}
