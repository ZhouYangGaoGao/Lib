package mvp.tree.view;

import android.view.View;

import java.util.List;

import base.BConfig;
import base.BTabsFragment;
import base.Manager;
import mvp.chapter.view.ArticleFragment;
import mvp.tree.model.Tree;
import rx.Observable;
import util.MBundle;

public class TreeTabFragment extends BTabsFragment<Tree> {

    @Override
    public void afterView() {
        mSmartView.setBack(true);
        mSmartView.topContent.setVisibility(View.VISIBLE);
        List<Tree> trees = getIntent().getParcelableArrayListExtra(BConfig.TABS);
        if (trees == null || trees.size() == 0) return;
        for (Tree tree : trees) {
            creator.add(tree.getName(), TreeArticleFragment.class, MBundle.create(BConfig.ID, tree.getId()));
        }
        initFragments();
    }

    public static class TreeArticleFragment extends ArticleFragment {

        @Override
        protected Observable<?> get() {
            return getArguments() != null ? Manager.getApi().treeList(page, getArguments().getInt(BConfig.ID)) : null;
        }
    }
}
