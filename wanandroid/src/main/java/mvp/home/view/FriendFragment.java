package mvp.home.view;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.zhy.wanandroid.R;

import java.util.List;

import adapter.ViewHolder;
import anylayer.AnyLayer;
import anylayer.Layer;
import base.BConfig;
import base.BFragment;
import base.BPresenter;
import base.BView;
import base.BWebFragment;
import base.Manager;
import base.Subs;
import butterknife.BindView;
import custom.FlowLayout;
import custom.PopView;
import custom.SmartView;
import custom.TagAdapter;
import custom.TagFlowLayout;
import custom.TextView;
import listener.SmartModel;
import mvp.chapter.model.Article;
import rx.Observable;
import util.GoTo;

public class FriendFragment extends BFragment<List<Article>, BPresenter<BView<?>>> {

    @BindView(R.id.mTabFlowLayout)
    TagFlowLayout mTabFlowLayout;
    @BindView(R.id.mSmartView)
    SmartView mSmartView;
    private List<Article> mData;

    @Override
    public void beforeView() {
        contentViewId = R.layout.fragment_frends;
    }


    @Override
    public void success(List<Article> data) {
        this.mData = data;
        mTabFlowLayout.setAdapter(new TagAdapter<Article>(mData) {
            @Override
            public View getView(FlowLayout parent, int position, Article i) {
                return new TextView("  " + i.getTitle() + "  ").tagStyle(BConfig.get().getColorTheme88(),
                        BConfig.get().getColorTheme(), 20, 14);
            }
        });

        mTabFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Article article = mData.get(position);
                GoTo.start(BWebFragment.class, new Intent()
                        .putExtra(BConfig.URL, article.getLink())
                        .putExtra(BConfig.TITLE, article.getTitle()));
                new SmartModel(R.drawable.ic_more_vert, article.isCollect() ? R.drawable.ic_favorite_white : R.drawable.ic_favorite_white_border) {
                    @Override
                    public void onClick(SmartView sv, int viewIndex, int resIndex) {
                        if (viewIndex == 2 && resIndex == 2) {
                            showPop(article, sv.getTVs()[viewIndex]);
                        } else if (viewIndex == 2 && resIndex == 0) {
                            actionFavorite(article, position,sv.getTVs()[2], R.drawable.ic_favorite_white, R.drawable.ic_favorite_white_border);
                        }
                    }
                };
                return false;
            }
        });
    }

    private void actionFavorite(Article i,int position, TextView tv, int trueRes, int falseRes) {
        presenter.sub(new Subs<Object>(mData.get(position).isCollect() ? Manager.getApi().unCollect(i.getId())
                : Manager.getApi().collect(i.getId())) {
            @Override
            public void onSuccess(Object o) {
                mData.get(position).setCollect(!mData.get(position).isCollect());
                tv.setLeftRes(mData.get(position).isCollect() ? trueRes : falseRes);
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
        return Manager.getApi().friend();
    }

}
