package mvp.home.view;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.zhy.wanandroid.R;

import anylayer.AnyLayer;
import anylayer.Layer;
import base.BConfig;
import base.BListDataFragment;
import base.BSub;
import base.BWebFragment;
import base.Manager;
import bean.Smart;
import butterknife.BindView;
import custom.FlowLayout;
import custom.PopView;
import custom.SmartView;
import custom.TagAdapter;
import custom.TagFlowLayout;
import custom.TextView;
import mvp.chapter.model.Article;
import rx.Observable;
import util.GoTo;
import util.MIntent;

public class FriendFragment extends BListDataFragment<Article> {

    @BindView(R.id.mTabFlowLayout)
    TagFlowLayout mTabFlowLayout;
    @BindView(R.id.mSmartView)
    SmartView mSmartView;

    @Override
    public void beforeView() {
        contentViewId = R.layout.fragment_frends;
    }

    @Override
    protected void upData() {
        mTabFlowLayout.setAdapter(new TagAdapter<Article>(mData) {
            @Override
            public View getView(FlowLayout parent, int position, Article i) {
                return new TextView("  " + i.getTitle() + "  ").tagStyle(BConfig.get().getColorTheme88(),
                        BConfig.get().getColorTheme(), 20, 14);
            }
        });

        mTabFlowLayout.setOnTagClickListener((view, position, parent) -> {
            Article i = mData.get(position);

            new Smart(2) {
                @Override
                protected void init() {
                    res[2][2] = R.drawable.ic_more_vert;
                    res[2][0] = i.isCollect() ? R.drawable.ic_favorite_white : R.drawable.ic_favorite_white_border;
                    GoTo.start(BWebFragment.class, new MIntent(BConfig.URL, i.getLink()).putExtra(BConfig.TITLE, i.getTitle()));
                    sendSticky();
                }

                @Override
                public void onClick(SmartView sv, int viewIndex, int resIndex) {
                    switch (viewIndex + "" + resIndex) {
                        case "22":
                            showPop(i, sv.getTVs()[viewIndex]);
                            break;
                        case "20":
                            actionFavorite(i, position, sv.getTVs()[2], R.drawable.ic_favorite_white, R.drawable.ic_favorite_white_border);
                            break;
                    }
                }
            };
            return false;
        });
    }


    private void actionFavorite(Article i, int position, TextView tv, int trueRes, int falseRes) {
        presenter.sub(new BSub<Object>(mData.get(position).isCollect() ? Manager.getApi().unCollect(i.getId())
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
