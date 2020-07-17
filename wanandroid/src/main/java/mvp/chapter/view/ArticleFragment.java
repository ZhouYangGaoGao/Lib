package mvp.chapter.view;

import android.content.Intent;

import adapter.CommonAdapter;

import com.zhy.wanandroid.R;

import adapter.ViewHolder;
import base.BConfig;
import base.BSmartFragment;
import base.BWebFragment;
import base.Manager;
import custom.TextView;
import mvp.chapter.model.Article;
import rx.Observable;
import util.GoTo;

public class ArticleFragment extends BSmartFragment<Article> {
    {
        showTopBar = false;
        isCard = 10;
        startPage = 0;
    }

    @Override
    protected void convert(ViewHolder h, Article i) {
        TextView title = h.getView(R.id.title);
        title.setText(i.getTitle());
        title.setLeftRes( R.drawable.ic_favorite_24,R.drawable.ic_favorite_border);
        title.setOnClickListener(v -> {
            if (title.drawableIndex == -1)
                GoTo.start(BWebFragment.class, new Intent().putExtra(BConfig.URL, i.getLink()));
            else toast("请先登录-->" + title.drawableIndex);
        });
    }

    @Override
    protected Observable<?> get() {
        return getArguments() != null ? Manager.getApi().article(getArguments().getInt(BConfig.ID), page) : null;
    }
}