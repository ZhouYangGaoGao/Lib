package mvp.project.view;

import android.content.Intent;
import android.net.Uri;

import com.necer.ndialog.ChoiceDialog;
import com.zhy.wanandroid.R;

import org.greenrobot.eventbus.EventBus;

import adapter.ViewHolder;
import base.BConfig;
import base.BWebFragment;
import base.Manager;
import base.Subs;
import custom.SmartView;
import listener.SmartModel;
import mvp.chapter.model.Article;
import mvp.chapter.view.ArticleFragment;
import mvp.home.view.HomeFragment;
import rx.Observable;
import rx.Subscription;
import util.Dialogs;
import util.GoTo;

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
        h.setText(R.id.tv_time, i.getNiceShareDate() + "            " + i.getAuthor());
    }

    @Override
    protected void onItemClick(ViewHolder h, Article article) {
        GoTo.start(BWebFragment.class, new Intent().putExtra(BConfig.URL, article.getLink()));
        new SmartModel() {
            @Override
            protected void init() {
                drawableRes[2][2] = R.drawable.ic_list_more;
                drawableRes[2][0] = R.drawable.ic_favorite_top;
                indexes = new int[]{2};
                EventBus.getDefault().postSticky(this);
            }

            @Override
            public void onClick(SmartView smartView, int textViewIndex, int drawableIndex) {
                if (textViewIndex == 2 && drawableIndex == 2) {
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
                } else if (textViewIndex == 2 && drawableIndex == 0) {
                    toast("请先登录");
                }
            }
        };
    }

    @Override
    protected Observable<?> get() {
        return getArguments() != null ? Manager.getApi().projects(page, getArguments().getInt(BConfig.ID)) : null;
    }
}
