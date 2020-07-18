package mvp.project.view;

import java.util.List;

import base.BConfig;
import base.BTabsFragment;
import base.Manager;
import mvp.tree.model.Tree;
import rx.Observable;
import util.MBundle;

public class ProjectTabFragment extends BTabsFragment<List<Tree>> {
    @Override
    protected Observable<?> get() {
        return Manager.getApi().projectTree();
    }

    @Override
    public void success(List<Tree> data) {
        for (Tree chapter: data) {
            creator.add(chapter.getName(), ProjectFragment.class, MBundle.create(BConfig.ID,chapter.getId()));
        }
        initFragments();
    }
}
