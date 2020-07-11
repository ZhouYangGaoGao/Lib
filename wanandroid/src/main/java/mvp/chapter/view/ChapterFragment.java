package mvp.chapter.view;

import java.util.List;

import base.BConfig;
import base.BTabsFragment;
import base.Manager;
import base.Subs;
import mvp.chapter.model.Chapter;
import util.BundleCreator;

/**
 * @author ZhouYang
 * @describe
 * @date 2020/7/11  2:18 PM
 * - generate by MvpAutoCodePlus plugin.
 */

public class ChapterFragment extends BTabsFragment<List<Chapter>> {

    @Override
    public void getData() {
        presenter.sub(Subs.get(this,Manager.getApi().chapters()));
    }

    @Override
    public void success(List<Chapter> data) {
        for (Chapter chapter: data) {
            creator.add(chapter.getName(), ArticleFragment.class, BundleCreator.create(BConfig.ID,chapter.getId()));
        }
        initFragments();
    }
}

