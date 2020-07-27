package mvp.main.view;

import com.zhy.wanandroid.R;

import custom.HistoryFragment;

public class MyHistFragment extends HistoryFragment {
    @Override
    public void beforeView() {
       topView = getView(R.layout.layout_fragment_hot);
    }
}