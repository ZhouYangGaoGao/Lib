package mvp.project.view;

import android.content.Intent;

import com.zhy.android.adapter.CommonAdapter;
import com.zhy.wanandroid.R;

import base.BConfig;
import base.BSmartFragment;
import base.BWebFragment;
import base.Manager;
import base.Subs;
import custom.TextView;
import mvp.chapter.model.Article;
import rx.Subscription;
import util.GoTo;

public class ProjectFragment extends BSmartFragment<Article> {

    @Override
    public void beforeView() {
        itemLayoutId = R.layout.item_text;
        showTopBar = false;
        isCard = 10;
        showTopBar = true;
    }

    @Override
    public void afterView() {
        mSmartView.setBack(false);
        mSmartView.rightTextView.setRightRes(R.drawable.ic_list_more);
        mSmartView.leftTextView.setLeftRes(R.drawable.ic_list_more);
        mSmartView.rightTextView.setLeftRes(android.R.drawable.ic_menu_search);
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
            else toast("请先登录-->" + title.drawableIndex);
        });
    }

    @Override
    protected Subscription get() {
        return Subs.get(this, Manager.getApi().projects(page));
    }
}
