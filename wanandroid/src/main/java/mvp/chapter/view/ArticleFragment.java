package mvp.chapter.view;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.necer.ndialog.ChoiceDialog;
import com.zhy.wanandroid.R;

import adapter.ViewHolder;
import anylayer.AnyLayer;
import anylayer.Layer;
import base.BConfig;
import base.BSmartFragment;
import base.BWebFragment;
import base.BaseBean;
import base.Manager;
import base.Subs;
import custom.PopView;
import custom.SmartView;
import custom.TextView;
import listener.SmartModel;
import listener.WebEvent;
import mvp.chapter.model.Article;
import rx.Observable;
import util.GoTo;
import util.MLayoutParams;

public class ArticleFragment extends BSmartFragment<Article> {
    protected int cid = 0;

    {
        showTopBar = false;
        isCard = 7;
        startPage = 0;
        itemLayoutId = R.layout.item_article;
    }

    @Override
    public void beforeView() {
        cid = getArguments() != null ? getArguments().getInt(BConfig.ID) : 0;
        cacheKey = TAG + cid;
    }

    @Override
    protected void convert(ViewHolder h, Article i) {

        LinearLayout tagLayout = h.getView(R.id.tagLayout);

        initDate(i, tagLayout);

        initChapter(i, tagLayout);

        initShare(i, tagLayout);

        initAuthor(i, tagLayout);

        initTags(i, tagLayout);

        initFresh(i, tagLayout);

        initTop(i, tagLayout);

        initClick(h, i, initTitle(h, i));
    }

    private TextView initTitle(ViewHolder h, Article i) {
        TextView title = h.getView(R.id.title);
        title.setText(i.getTitle());
        title.setTextClickable(false);
        title.setLeftRes(i.isCollect() ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
        return title;
    }

    private void initDate(Article i, LinearLayout tagLayout) {
        if (!TextUtils.isEmpty(i.getNiceShareDate()))
            tagLayout.addView(new TextView(getActivity()).setText(i.getNiceShareDate())
                    .setLayout(MLayoutParams.marginLLP(0, 0))
                    .tagStyle(0x00000000, getResources().getColor(R.color.clo_source), 10));
    }

    protected void initChapter(Article i, LinearLayout tagLayout) {
        if (!TextUtils.isEmpty(i.getSuperChapterName()) && !TextUtils.isEmpty(i.getChapterName()))
            tagLayout.addView(new TextView(getActivity()).setText(i.getSuperChapterName() + "/" + i.getChapterName())
                    .setLayout(MLayoutParams.marginLLP(0, 0))
                    .tagStyle(0x00000000, getResources().getColor(R.color.clo_source), 10), 0);
    }

    private void initShare(Article i, LinearLayout tagLayout) {
        if (!TextUtils.isEmpty(i.getShareUser()))
            tagLayout.addView(new TextView(getActivity()).setText("分享人:" + i.getShareUser())
                    .setLayout(MLayoutParams.marginLLP(0, 8))
                    .tagStyle(0x00000000, getResources().getColor(R.color.clo_source), 10), 0);
    }

    private void initAuthor(Article i, LinearLayout tagLayout) {
        if (!TextUtils.isEmpty(i.getAuthor()))
            tagLayout.addView(new TextView(getActivity()).setText("作者:" + i.getAuthor())
                    .setLayout(MLayoutParams.marginLLP(0, 8))
                    .tagStyle(0x00000000, getResources().getColor(R.color.clo_source), 10), 0);
    }

    private void initTop(Article i, LinearLayout tagLayout) {
        if (i.isTop()) tagLayout.addView(new TextView(getActivity()).setText("置顶")
                .setLayout(MLayoutParams.marginLLP(0, 5))
                .tagStyle(0xffff0000, 8), 0);
    }

    private void initFresh(Article i, LinearLayout tagLayout) {
        if (i.isFresh()) tagLayout.addView(new TextView(getActivity()).setText("新")
                .setLayout(MLayoutParams.marginLLP(0, 5))
                .tagStyle(0xffff0000, 8), 0);
    }

    protected void initTags(Article i, LinearLayout tagLayout) {
        if (i.getTags() != null && i.getTags().size() > 0) {
            for (Article.Tag tag : i.getTags()) {
                tagLayout.addView(new TextView(getContext())
                        .setText(tag.getName())
                        .setLayout(MLayoutParams.marginLLP(0, 5))
                        .tagStyle(BConfig.get().getColorTheme(), 8), 0);
            }
        }
    }

    @Override
    protected void onItemClick(ViewHolder h, Article i) {
        GoTo.start(BWebFragment.class, new Intent().putExtra(BConfig.URL, i.getLink()));
        new SmartModel(R.drawable.ic_more_vert, i.isCollect() ? R.drawable.ic_favorite_white : R.drawable.ic_favorite_white_border) {
            @Override
            public void onClick(SmartView sv, int viewIndex, int resIndex) {
                if (viewIndex == 2 && resIndex == 2) {
                    showPop(i, sv.getTVs()[viewIndex]);
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

    protected Observable<BaseBean<Object>> unCollect(Article i) {
        return Manager.getApi().unCollect(i.getId());
    }

    private void actionFavorite(Article i, ViewHolder h, TextView tv, int trueRes, int falseRes) {
        presenter.sub(new Subs<Object>(i.isCollect() ? unCollect(i)
                : Manager.getApi().collect(i.getId())) {
            @Override
            public void onSuccess(Object o) {
                mData.get(h.getPosition()).setCollect(!i.isCollect());
                if (tv != null)
                    tv.setLeftRes(mData.get(h.getPosition()).isCollect() ? trueRes : falseRes);
                TextView listFavoriteView = h.getTagView("favorite");
                if (listFavoriteView != null)
                    listFavoriteView.setLeftRes(mData.get(h.getPosition()).isCollect() ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
            }
        });
    }

    private void showPop(Article article, TextView tv) {
        AnyLayer.popup(tv)
                .contentView(new PopView("分享", "浏览器打开", "刷新"))
                .onClickToDismiss(new Layer.OnClickListener() {
                    @Override
                    public void onClick(Layer layer, View v) {
                        switch (v.getId()) {
                            case 0:
                                share(article);
                                break;
                            case 1:
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(article.getLink())));
                                break;
                            case 2:
                                new WebEvent(WebEvent.REFRESH);
                                break;
                        }
                    }
                }, 0, 1, 2)
                .show();
    }

    private void share(Article article) {
        startActivity(Intent.createChooser(new Intent()
                .setAction(Intent.ACTION_SEND).setType("text/plain")
                .putExtra(Intent.EXTRA_SUBJECT, article.getTitle())
                .putExtra(Intent.EXTRA_TEXT, article.getLink()), "分享到"));
    }

    @Override
    protected Observable<?> get() {
        return Manager.getApi().article(cid, page);
    }
}

