package base;

import androidx.fragment.app.FragmentManager;

import com.zhy.android.R;

/**
 * @author ZhouYang
 * @describe 通用网页
 * @date 2020/3/10  9:15 AM
 * - generate by MvpAutoCodePlus plugin.
 */

public class WebActivity extends BActivity<Object, BPresenter> {

    WebFragment mWebFragment;
    FragmentManager manager;

    @Override
    public void beforeView() {
        contentView = R.layout.activity_web;
    }


    @Override
    public void initView() {
        manager = getSupportFragmentManager();
        mWebFragment = (WebFragment) manager.findFragmentById(R.id.webFragment);
    }


}

