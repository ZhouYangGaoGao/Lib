package mvp.chapter.view;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

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
import util.MLayoutParams;

public class ArticleFragment extends BSmartFragment<Article> {
    {
        showTopBar = false;
        isCard = 7;
        startPage = 0;
        itemLayoutId = R.layout.item_article;
    }

    @Override
    protected void convert(ViewHolder h, Article i) {
        TextView title = h.getView(R.id.title);
        title.setText(i.getTitle());
        title.setLeftRes(i.isCollect() ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
        LinearLayout tagLayout = h.getView(R.id.tagLayout);

        if (!TextUtils.isEmpty(i.getNiceShareDate()))
            tagLayout.addView(new TextView(getActivity()).setText(i.getNiceShareDate())
                    .setLayout(MLayoutParams.marginLLP(0, 0))
                    .tagStyle(0x00000000,getResources().getColor(R.color.clo_source), 10));

        if (!TextUtils.isEmpty(i.getSuperChapterName()) && !TextUtils.isEmpty(i.getChapterName()))
            tagLayout.addView(new TextView(getActivity()).setText(i.getSuperChapterName() + "/" + i.getChapterName())
                    .setLayout(MLayoutParams.marginLLP(0, 0))
                    .tagStyle(0x00000000,getResources().getColor(R.color.clo_source), 10), 0);

        if (!TextUtils.isEmpty(i.getShareUser()))
            tagLayout.addView(new TextView(getActivity()).setText("分享人:" + i.getShareUser())
                    .setLayout(MLayoutParams.marginLLP(0, 8))
                    .tagStyle(0x00000000,getResources().getColor(R.color.clo_source), 10), 0);

        if (!TextUtils.isEmpty(i.getAuthor()))
            tagLayout.addView(new TextView(getActivity()).setText("作者:" + i.getAuthor())
                    .setLayout(MLayoutParams.marginLLP(0, 8))
                    .tagStyle(0x00000000,getResources().getColor(R.color.clo_source), 10), 0);

        if (i.getTags().size() > 0) {
            for (Article.Tag tag : i.getTags()) {
                tagLayout.addView(new TextView(getContext())
                        .setText(tag.getName())
                        .setLayout(MLayoutParams.marginLLP(0, 5))
                        .tagStyle(BConfig.get().getColorTheme(), 8), 0);
            }
        }

        if (i.isFresh()) tagLayout.addView(new TextView(getActivity()).setText("新")
                .setLayout(MLayoutParams.marginLLP(0, 5))
                .tagStyle(0xffff0000, 8), 0);

        if (i.isTop()) tagLayout.addView(new TextView(getActivity()).setText("置顶")
                .setLayout(MLayoutParams.marginLLP(0, 5))
                .tagStyle(0xffff0000, 8), 0);

        initClick(h, i, title);
    }


    @Override
    protected void onItemClick(ViewHolder h, Article i) {
        GoTo.start(BWebFragment.class, new Intent().putExtra(BConfig.URL, i.getLink()));
        new SmartModel(R.drawable.ic_list_more, i.isCollect() ? R.drawable.ic_favorite_white : R.drawable.ic_favorite_white_border) {
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
                    onItemClick(h, i);
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