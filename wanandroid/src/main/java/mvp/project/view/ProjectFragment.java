package mvp.project.view;

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
    }

    @Override
    protected void convert(ViewHolder h, Article i) {
        h.setImage(R.id.iv_cover, i.getEnvelopePic());
        h.setText(R.id.tv_title, i.getTitle());
        h.setText(R.id.tv_description, i.getDesc());
        TextView time=h.getTextView(R.id.tv_time);
        time.setText(i.getNiceShareDate() + "            " + i.getAuthor());
        time.setLeftRes(i.isCollect() ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
        initClick(h,i,h.getTextView(R.id.tv_time));
    }

    @Override
    protected Observable<?> get() {
        return getArguments() != null ? Manager.getApi().projects(page, getArguments().getInt(BConfig.ID)) : null;
    }
}
