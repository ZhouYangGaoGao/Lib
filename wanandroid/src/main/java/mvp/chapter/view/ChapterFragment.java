package mvp.chapter.view;

import base.BConfig;
import base.BTabsFragment;
import base.Manager;
import mvp.tree.model.Tree;
import rx.Observable;
import util.MBundle;

/**
 * @author ZhouYang
 * @describe
 * @date 2020/7/11  2:18 PM
 * - generate by MvpAutoCodePlus plugin.
 */

public class ChapterFragment extends BTabsFragment<Tree> {

    @Override
    protected Observable<?> get() {
        return Manager.getApi().chapters();
    }

    @Override
    protected void upData() {
        for (Tree chapter: mData) {
            creator.add(chapter.getName(), ArticleFragment.class, MBundle.create(BConfig.ID,chapter.getId()));
        }
        initFragments();

    }
}

