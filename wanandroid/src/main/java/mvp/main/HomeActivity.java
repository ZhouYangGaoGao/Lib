package mvp.main;

import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.zhy.wanandroid.R;

import base.BConfig;
import base.BHomeActivity;
import base.BWebFragment;
import mvp.chapter.view.ChapterFragment;
import mvp.home.view.HomeFragment;
import mvp.main.view.MyFragment;
import mvp.project.view.ProjectFragment;
import util.BundleCreator;

public class HomeActivity extends BHomeActivity {
    @Override
    public void beforeView() {
        setIcons(R.drawable.ic_tab_home, R.drawable.ic_tab_tree, R.drawable.ic_tab_navigation,
                R.drawable.ic_tab_public, R.drawable.ic_tab_project);
    }

    @Override
    protected FragmentPagerItems.Creator initFragments(FragmentPagerItems.Creator creator) {
        return creator.add("主页", HomeFragment.class, BundleCreator.create(BConfig.TITLE,"主页"))
                .add("体系", BWebFragment.class, BundleCreator.create(BConfig.TITLE,"体系"))
                .add("导航", MyFragment.class, BundleCreator.create(BConfig.TITLE,"导航"))
                .add("公号", ChapterFragment.class, BundleCreator.create(BConfig.TITLE,"公号"))
                .add("项目", ProjectFragment.class, BundleCreator.create(BConfig.TITLE,"项目"));
    }

}
