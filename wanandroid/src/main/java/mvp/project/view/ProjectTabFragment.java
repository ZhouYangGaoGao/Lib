package mvp.project.view;

import base.BConfig;
import base.BTabsFragment;
import base.Manager;
import mvp.tree.model.Tree;
import rx.Observable;
import util.MBundle;

public class ProjectTabFragment extends BTabsFragment<Tree> {

    @Override
    protected Observable<?> get() {
        return Manager.getApi().projectTree();
    }

    @Override
    protected void upData() {
        for (Tree chapter: mData) {
            creator.add(chapter.getName(), ProjectFragment.class, MBundle.create(BConfig.ID,chapter.getId()));
        }
        initFragments();
    }
}
