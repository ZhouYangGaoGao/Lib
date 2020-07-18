package mvp.main.view;

import android.content.Intent;

import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.zhy.wanandroid.R;

import org.greenrobot.eventbus.EventBus;

import base.BConfig;
import base.BHomeActivity;
import custom.SmartView;
import listener.SmartListener;
import mvp.chapter.view.ChapterFragment;
import mvp.home.view.FriendFragment;
import mvp.home.view.HomeFragment;
import mvp.navigation.view.NavigationFragment;
import mvp.project.view.ProjectTabFragment;
import mvp.qa.view.QaFragment;
import mvp.square.view.SquareFragment;
import mvp.tree.view.TreeFragment;
import util.GoTo;

public class HomeActivity extends BHomeActivity implements SmartListener {
    @Override
    public void beforeView() {
        drawerContentView = getView(R.layout.layout_fragment_my);
        icons = new int[]{R.drawable.ic_tab_home, R.drawable.ic_tab_square,
                R.drawable.ic_tab_navigation, R.drawable.ic_tab_tree,
                R.drawable.ic_tab_project,R.drawable.ic_tab_public};
    }

    @Override
    public void afterView() {
        mSmartView.setBack(false);
        mSmartView.rightTextView.setRightRes(R.drawable.ic_commonly);
        mSmartView.leftTextView.setLeftRes(R.drawable.ic_menu);
        mSmartView.leftTextView.setRightRes(R.drawable.ic_qa);
        mSmartView.rightTextView.setLeftRes(R.drawable.ic_search);
        mSmartView.setListener(this, 0, 2);
    }

    @Override
    protected FragmentPagerItems.Creator initFragments(FragmentPagerItems.Creator creator) {
        return creator.add("主页", HomeFragment.class)
                .add("广场", SquareFragment.class)
                .add("导航", NavigationFragment.class)
                .add("体系", TreeFragment.class)
                .add("项目", ProjectTabFragment.class)
                .add("公众号", ChapterFragment.class);
    }

    @Override
    public void onClick(SmartView smartView, int textViewIndex, int drawableIndex) {
        switch (textViewIndex) {
            case 0:
                if (drawableIndex == 0)
                    EventBus.getDefault().post(new HomeActivity.DrawerEvent(true));
                else GoTo.start(QaFragment.class, new Intent().putExtra(BConfig.TITLE, "问答"));
                break;
            case 2:
                if (drawableIndex == 0)
                    GoTo.start(SearchFragment.class, new Intent().putExtra(BConfig.TITLE, "搜索"));
                else
                    GoTo.start(FriendFragment.class, new Intent().putExtra(BConfig.TITLE, "常用网站"));
                break;
        }
    }
}
