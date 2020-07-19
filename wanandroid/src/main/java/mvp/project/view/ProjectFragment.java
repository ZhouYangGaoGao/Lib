package mvp.project.view;

import android.view.View;
import android.widget.LinearLayout;

import com.zhy.wanandroid.R;

import adapter.ViewHolder;
import base.BConfig;
import base.Manager;
import custom.TextView;
import mvp.chapter.model.Article;
import mvp.chapter.view.ArticleFragment;
import rx.Observable;

public class ProjectFragment extends ArticleFragment {


    @Override
    public void beforeView() {
        itemLayoutId = R.layout.item_project;
        startPage = 1;
        super.beforeView();
    }

    @Override
    protected void convert(ViewHolder h, Article i) {
        h.setImage(R.id.iv_cover, i.getEnvelopePic());
        h.setText(R.id.tv_description, i.getDesc());
        h.setVisibility(R.id.tv_description, View.VISIBLE);
        super.convert(h, i);
    }

    @Override
    protected void initChapter(Article i, LinearLayout tagLayout) {
    }

    @Override
    protected Observable<?> get() {
        return Manager.getApi().projects(page, cid);
    }
}
