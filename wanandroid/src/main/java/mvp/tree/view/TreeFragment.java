package mvp.tree.view;

import android.content.Intent;

import com.zhy.wanandroid.R;

import adapter.ViewHolder;
import base.BConfig;
import base.BSmartFragment;
import base.Manager;
import custom.TextView;
import mvp.tree.model.Tree;
import rx.Observable;
import util.GoTo;

public class TreeFragment extends BSmartFragment<Tree> {
    @Override
    public void beforeView() {
        card.cardDiver = 8;
        info.showTop = false;
    }

    @Override
    public void afterView() {
        refreshLayout.setEnableLoadMore(false);
    }

    @Override
    protected void convert(ViewHolder h, Tree i) {
        TextView textView = h.getView(R.id.title);
        StringBuilder stringBuilder = new StringBuilder(i.getName());
        if (i.getChildren() != null && i.getChildren().size() > 0) {
            for (Tree child : i.getChildren()) {
                stringBuilder.append("  ·  ").append(child.getName());
            }
            textView.setRightRes(R.drawable.ic_arrow_forward_black);
        }
        textView.setText(stringBuilder.toString());
        h.setIncludeTextColor(textView, getResources().getColor(R.color.clo_theme_88), i.getName());
    }

    @Override
    protected void onItemClick(ViewHolder h, Tree i) {
        GoTo.start(TreeTabFragment.class, new Intent()
                .putExtra(BConfig.TITLE, i.getName())
                .putParcelableArrayListExtra(BConfig.TABS, i.getChildren()));
    }

    @Override
    protected Observable<?> get() {
        return Manager.getApi().tree();
    }
}
