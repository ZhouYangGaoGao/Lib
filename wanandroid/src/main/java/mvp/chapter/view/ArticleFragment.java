package mvp.chapter.view;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.necer.ndialog.ChoiceDialog;
import com.zhy.wanandroid.R;

import adapter.ViewHolder;
import base.BConfig;
import base.BSmartFragment;
import base.BWebFragment;
import base.Manager;
import base.Subs;
import custom.SmartView;
import custom.TextView;
import listener.SmartModel;
import mvp.chapter.model.Article;
import rx.Observable;
import util.Dialogs;
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
        title.setLeftRes(i.isCollect() ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
        initClick(h, i, title);
    }


    @Override
    protected void onItemClick(ViewHolder h, Article i) {
        GoTo.start(BWebFragment.class, new Intent().putExtra(BConfig.URL, i.getLink()));
        new SmartModel(R.drawable.ic_list_more, i.isCollect()?R.drawable.ic_favorite_white: R.drawable.ic_favorite_white_border) {
            @Override
            public void onClick(SmartView sv, int viewIndex, int resIndex) {
                if (viewIndex == 2 && resIndex == 2) {
                    showDialog(i);
                } else if (viewIndex == 2 && resIndex == 0) {
                    actionFavorite(i, h, sv.getTVs()[2], R.drawable.ic_favorite_white, R.drawable.ic_favorite_white_border);
                }
            }
        };
    }

    protected void initClick(ViewHolder h, Article i, TextView title) {
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.drawableIndex == 0) {
                    actionFavorite(i, h, null, R.drawable.ic_favorite, R.drawable.ic_favorite_border);
                } else {
                    onItemClick(h,i);
                }
            }
        });
    }

    private void actionFavorite(Article i, ViewHolder h, TextView tv, int trueRes, int falseRes) {
        presenter.sub(new Subs<Object>(i.isCollect() ? Manager.getApi().unCollect(i.getId())
                : Manager.getApi().collect(i.getId())) {
            @Override
            public void onSuccess(Object o) {
                mData.get(h.getPosition()).setCollect(!i.isCollect());
                if (tv != null)
                    tv.setLeftRes(mData.get(h.getPosition()).isCollect() ? trueRes : falseRes);
                upData();
            }
        });
    }

    private void showDialog(Article article) {
        Dialogs.show(new ChoiceDialog.OnItemClickListener() {
            @Override
            public void onItemClick(android.widget.TextView onClickView, int position) {
                if (position == 0) {
                    share(article);
                } else if (position == 1) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(article.getLink())));
                }
            }
        }, "分享", "用浏览器打开");
    }

    private void share(Article article) {
        startActivity(Intent.createChooser(new Intent()
                .setAction(Intent.ACTION_SEND).setType("text/plain")
                .putExtra(Intent.EXTRA_SUBJECT, article.getTitle())
                .putExtra(Intent.EXTRA_TEXT, article.getLink()), "分享到"));
    }

    @Override
    protected Observable<?> get() {
        return getArguments() != null ? Manager.getApi().article(getArguments().getInt(BConfig.ID), page) : null;
    }
}