package mvp.main;

import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.zhy.wanandroid.R;

import base.BHomeActivity;
import base.BWebFragment;
import mvp.main.view.MyFragment;

public class HomeActivity extends BHomeActivity {
    @Override
    public void beforeView() {
        setIcons(R.drawable.ic_tab_home, R.drawable.ic_tab_tree, R.drawable.ic_tab_navigation,
                R.drawable.ic_tab_public, R.drawable.ic_tab_project);
    }

    @Override
    protected FragmentPagerItems.Creator initFragments(FragmentPagerItems.Creator creator) {
        return creator.add("主页", MyFragment.class)
                .add("体系", BWebFragment.class)
                .add("导航", BWebFragment.class)
                .add("公号", BWebFragment.class)
                .add("项目", BWebFragment.class);
    }

}
