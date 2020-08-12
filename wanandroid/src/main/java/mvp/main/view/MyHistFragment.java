package mvp.main.view;

import com.zhy.wanandroid.R;

import base.BHistoryFragment;

public class MyHistFragment extends BHistoryFragment {
    @Override
    public void beforeView() {
       heardView = getView(R.layout.layout_fragment_hot);
    }
}