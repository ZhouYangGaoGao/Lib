package mvp.navigation.view;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import androidx.recyclerview.widget.RecyclerView;

import com.necer.ndialog.ChoiceDialog;
import com.zhy.wanandroid.R;

import java.util.List;

import adapter.CommonAdapter;
import adapter.ViewHolder;
import adapter.group.BaseViewHolder;
import adapter.group.GridLayoutManager;
import adapter.group.GroupAdapter;
import background.drawable.DrawableCreator;
import base.BConfig;
import base.BFragment;
import base.BPresenter;
import base.BView;
import base.BWebFragment;
import base.Manager;
import butterknife.BindView;
import custom.SmartView;
import custom.TextView;
import listener.SmartModel;
import mvp.chapter.model.Article;
import mvp.navigation.model.Navigation;
import photopicker.lib.decoration.GridSpacingItemDecoration;
import rx.Observable;
import util.Dialogs;
import util.GoTo;
import util.ScreenUtils;

public class NavigationFragment extends BFragment<List<Navigation>, BPresenter<BView<?>>> {
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

    @Override
    public void success(List<Navigation> data) {
        tabListView.setAdapter(new CommonAdapter<Navigation>(getContext(), data, R.layout.item_text) {
            View selectedView;

            @Override
            public void convert(ViewHolder h, Navigation i) {
                TextView title = h.getView(R.id.title);
                title.setText(i.getName());
                title.setAutoZoom(true);
                title.setSingleLine();
                h.getConvertView().setBackground(new DrawableCreator.Builder().setSelectedSolidColor(
                        0xffeeeeee, 0x33000000).build());
                h.setClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (selectedView != null) selectedView.setSelected(false);
                        h.getConvertView().setSelected(true);
                        selectedView = h.getConvertView();
                        layoutManager.scrollToPositionWithOffset(adapter.getPositionForGroupHeader(
                                h.getPosition()), ScreenUtils.dip2px(-1));
                    }
                });
            }
        });


        mRecyclerView.setAdapter(adapter = new GroupAdapter<Navigation>(data) {

            @Override
            public int getChildrenCount(Navigation navigation) {
                return navigation.getArticles() == null ? 0 : navigation.getArticles().size();
            }

            @Override
            public void onBindHeaderViewHolder(BaseViewHolder holder, Navigation navigation) {
                holder.setText(R.id.title, navigation.getName());
                holder.setBackgroundColor(R.id.title, 0x33000000);
            }

            @Override
            public void onBindChildViewHolder(BaseViewHolder holder, Navigation navigation, int childPosition) {
                Article article = navigation.getArticles().get(childPosition);
                TextView textView = holder.get(R.id.title);
                textView.setAutoZoom(true);
                textView.setSingleLine();
                textView.setBackground(new DrawableCreator.Builder().setPressedSolidColor(
                        0x33000000, 0x11000000).build());
                textView.setText(article.getTitle());
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GoTo.start(BWebFragment.class, new Intent().putExtra(BConfig.URL, article.getLink()));
                        new SmartModel(R.drawable.ic_list_more, R.drawable.ic_favorite_white) {
                            @Override
                            public void onClick(SmartView sv, int viewIndex, int resIndex) {
                                if (viewIndex == 2 && resIndex == 2) {
                                    showDialog(article);
                                } else if (viewIndex == 2 && resIndex == 0) {
                                    toast("请先登录");
                                }
                            }
                        };
                    }
                });
            }
        });
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, ScreenUtils.dip2px(1), false));
        mRecyclerView.setLayoutManager(layoutManager = new GridLayoutManager(getContext(), 2, adapter));

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

}
