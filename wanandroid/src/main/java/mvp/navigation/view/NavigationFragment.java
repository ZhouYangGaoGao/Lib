package mvp.navigation.view;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.necer.ndialog.ChoiceDialog;
import com.zhy.wanandroid.R;

import java.util.List;

import adapter.CommonAdapter;
import adapter.ViewHolder;
import adapter.group.BaseViewHolder;
import adapter.group.GridLayoutManager;
import adapter.group.GroupAdapter;
import base.BConfig;
import base.BFragment;
import base.BListDataFragment;
import base.BPresenter;
import base.BSub;
import base.BView;
import base.BWebFragment;
import base.Manager;
import bean.Info;
import butterknife.BindView;
import custom.SmartView;
import bean.Smart;
import mvp.chapter.model.Article;
import mvp.navigation.model.Navigation;
//import photopicker.lib.decoration.GridSpacingItemDecoration;
import rx.Observable;
import util.Dialogs;
import util.GoTo;
import util.MDrawable;
import util.MIntent;
import util.ScreenUtils;

public class NavigationFragment extends BListDataFragment<Navigation> {
    @BindView(R.id.tabListView)
    ListView tabListView;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private GroupAdapter<Navigation> adapter;
    private GridLayoutManager layoutManager;

    @Override
    public void beforeView() {
        contentViewId = R.layout.fragment_navigation;
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
        return Manager.getApi().navigation();
    }

    @Override
    protected void upData() {
//        tabListView.setAdapter(new CommonAdapter<Navigation>(getContext(), mData, R.layout.item_text) {
//            View selectedView;
//
//            @Override
//            public void convert(ViewHolder h, Navigation i) {
//                h.getTextView(R.id.title)
//                        .setAutoZoom(true)
//                        .setText(i.getName())
//                        .setTextColor(getResources().getColor(R.color.clo_big_title));
//                h.getConvertView().setBackground(MDrawable.select(getResources().getColor(R.color.clo_theme), getResources().getColor(R.color.clo_theme_88)));
//                h.setClick(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (selectedView != null) selectedView.setSelected(false);
//                        h.getConvertView().setSelected(true);
//                        selectedView = h.getConvertView();
//                        layoutManager.scrollToPositionWithOffset(adapter.getPositionForGroupHeader(
//                                h.getPosition()), ScreenUtils.dip2px(-1));
//                    }
//                });
//            }
//        });

        mRecyclerView.setAdapter(adapter = new GroupAdapter<Navigation>(mData) {

            @Override
            public int getChildrenCount(Navigation navigation) {
                return navigation.getArticles() == null ? 0 : navigation.getArticles().size();
            }

            @Override
            public void onBindHeaderViewHolder(BaseViewHolder holder, Navigation navigation) {
                holder.setText(R.id.title, navigation.getName());
                holder.getTextView(R.id.title).setGravity(Gravity.CENTER);
                holder.setTextColor(R.id.title, getResources().getColor(R.color.clo_big_title));
                holder.setBackgroundColor(R.id.title, getResources().getColor(R.color.clo_theme));
            }

            @Override
            public void onBindChildViewHolder(BaseViewHolder holder, Navigation navigation, int childPosition) {
                Article article = navigation.getArticles().get(childPosition);
                holder.getTextView(R.id.title).setEllipsize(TextUtils.TruncateAt.START, 1)
                        .setMBackground(MDrawable.press(0x11000000, 0x33000000))
                        .setText(article.getTitle())
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                jump(article, navigation, childPosition);
                            }
                        });
            }
        });
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayout.HORIZONTAL));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayout.VERTICAL));
        mRecyclerView.setLayoutManager(layoutManager = new GridLayoutManager(getContext(), 2, adapter));
    }

    private void jump(Article i, Navigation navigation, int childPosition) {
        new Smart(2) {
            @Override
            protected void init() {
                res[2][2] = R.drawable.ic_more_vert;
                res[2][0] = i.isCollect() ? R.drawable.ic_favorite_white : R.drawable.ic_favorite_white_border;
                GoTo.start(BWebFragment.class, new MIntent(BConfig.URL, i.getLink()));
                sendSticky();
            }

            @Override
            public void onClick(SmartView sv, int viewIndex, int resIndex) {
                if (viewIndex == 2 && resIndex == 2) {
                    showDialog(i);
                } else if (viewIndex == 2 && resIndex == 0) {
                    presenter.sub(new BSub<Object>(i.isCollect() ? Manager.getApi().unCollect(i.getId())
                            : Manager.getApi().collect(i.getId())) {
                        @Override
                        public void onSuccess(Object o) {
                            navigation.getArticles().get(childPosition).setCollect(!i.isCollect());
                            sv.getTVs()[2].setLeftRes(navigation.getArticles().get(childPosition).isCollect() ?
                                    R.drawable.ic_favorite_white : R.drawable.ic_favorite_white_border);
                        }
                    });
                }
            }
        };
    }
}
