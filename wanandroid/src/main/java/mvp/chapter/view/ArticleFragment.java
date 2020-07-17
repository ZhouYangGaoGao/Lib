package mvp.chapter.view;

import android.content.Intent;
import android.net.Uri;

import adapter.CommonAdapter;

import com.necer.ndialog.ChoiceDialog;
import com.zhy.wanandroid.R;

import adapter.ViewHolder;
import base.BConfig;
import base.BSmartFragment;
import base.BWebFragment;
import base.Manager;
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
        title.setLeftRes( R.drawable.ic_favorite_24,R.drawable.ic_favorite_border);
    }

    @Override
    protected void onItemClick(ViewHolder h, Article i) {
        GoTo.start(BWebFragment.class, new Intent().putExtra(BConfig.URL, i.getLink()));
        new SmartModel(R.drawable.ic_list_more, R.drawable.ic_favorite_white) {
            @Override
            public void onClick(SmartView sv, int viewIndex, int resIndex) {
                if (viewIndex == 2 && resIndex == 2) {
                    showDialog(i);
                } else if (viewIndex == 2 && resIndex == 0) {
                    toast("请先登录");
                }
            }
        };
    }

    private void showDialog(Article article) {
        Dialogs.show(new ChoiceDialog.OnItemClickListener() {
            @Override
            public void onItemClick(android.widget.TextView onClickView, int position) {
                if (position == 0) {
                    startActivity(Intent.createChooser(new Intent()
                            .setAction(Intent.ACTION_SEND).setType("text/plain")
                            .putExtra(Intent.EXTRA_SUBJECT, article.getTitle())
                            .putExtra(Intent.EXTRA_TEXT, article.getLink()), "分享到"));
                } else if (position == 1) {
                    Uri uri = Uri.parse(article.getLink());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            }
        }, "分享", "用浏览器打开");
    }
    @Override
    protected Observable<?> get() {
        return getArguments() != null ? Manager.getApi().article(getArguments().getInt(BConfig.ID), page) : null;
    }
}