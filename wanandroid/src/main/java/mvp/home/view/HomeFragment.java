package mvp.home.view;

import android.content.Intent;
import android.widget.RelativeLayout;

import com.scwang.smart.refresh.header.ClassicsHeader;
import com.zhy.android.adapter.CommonAdapter;
import com.zhy.wanandroid.R;

import base.BConfig;
import base.BSmartFragment;
import base.BWebFragment;
import base.Manager;
import base.Subs;
import custom.SmartView;
import custom.TextView;
import listener.OnSmartClickListener;
import mvp.chapter.model.Article;
import mvp.main.view.SearchFragment;
import rx.Subscription;
import util.GoTo;
import util.ScreenUtils;

public class HomeFragment extends BSmartFragment<Article> implements OnSmartClickListener {
    @Override
    public void beforeView() {
        heardView = getView(R.layout.fragment_banner);
        itemLayoutId = R.layout.item_text;
        isCard = 10;
        showTopBar = true;
    }

    @Override
    public void afterView() {
        mSmartView.setBack(false);
        mSmartView.rightTextView.setRightRes(R.drawable.ic_friend);
        mSmartView.leftTextView.setLeftRes(R.drawable.ic_list_more);
        mSmartView.rightTextView.setLeftRes(R.drawable.ic_search);
        mSmartView.rightTextView.setDrawablePadding(8);
        mSmartView.setListener(this);
    }

    @Override
    protected void convert(CommonAdapter.ViewHolder h, Article i) {
        TextView title = h.getView(R.id.title);
        title.setText(i.getTitle());
        title.setLeftRes(R.drawable.ic_favorite_border);
        title.setRightRes(R.drawable.ic_favorite_24);
        title.setOnClickListener(v -> {
            if (title.drawableIndex == -1)
                GoTo.start(BWebFragment.class, new Intent().putExtra(BConfig.URL, i.getLink()));
        });
    }

    @Override
    protected Subscription get() {
        return Subs.get(this, Manager.getApi().list(page));
    }

    @Override
    public void onClick(SmartView smartView, int textViewIndex, int drawableIndex) {
        switch (textViewIndex) {
            case 0:
                break;
            case 2:
                if (drawableIndex == 0)
                    GoTo.start(SearchFragment.class, new Intent().putExtra(BConfig.TITLE, "搜索"));
                else
                    GoTo.start(FriendFragment.class, new Intent().putExtra(BConfig.TITLE, "常用网站"));
                break;
        }
    }
}
