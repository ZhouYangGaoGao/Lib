package mvp.chapter.view;

import java.util.List;

import base.BConfig;
import base.BTabsFragment;
import base.Manager;
import mvp.tree.model.Tree;
import rx.Observable;
import util.BundleCreator;

/**
 * @author ZhouYang
 * @describe
 * @date 2020/7/11  2:18 PM
 * - generate by MvpAutoCodePlus plugin.
 */

public class ChapterFragment extends BTabsFragment<List<Tree>> {

    @Override
    protected Observable<?> get() {
        return Manager.getApi().chapters();
    }

    @Override
    public void success(List<Tree> data) {
        for (Tree chapter: data) {
            creator.add(chapter.getName(), ArticleFragment.class, BundleCreator.create(BConfig.ID,chapter.getId()));
        }
        initFragments();
    }
}
