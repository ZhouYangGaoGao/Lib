package mvp.main.view;

import android.view.Gravity;

import com.zhy.wanandroid.R;

import org.greenrobot.eventbus.EventBus;

import adapter.ViewHolder;
import base.BSmartFragment;
import base.Manager;
import custom.HistoryFragment;
import custom.TextView;
import mvp.chapter.model.Article;
import rx.Observable;

public class HotKeyFragment extends BSmartFragment<Article> {

    @Override
    public void beforeView() {
        numColumns = 3;
        showTopBar = false;
        bgColor = 0xffeeeeee;
    }

    @Override
    public void afterView() {
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadMore(false);
    }

    @Override
    protected Observable<?> get() {
        return Manager.getApi().hotKey();
    }

    @Override
    protected void convert(ViewHolder h, Article i) {
        TextView textView = h.getTextView(R.id.title);
        textView.setText(i.getTitle());
        textView.setAutoZoom(true);
        textView.setGravity(Gravity.CENTER);
    }

    @Override
    protected void onItemClick(ViewHolder h, Article i) {
        EventBus.getDefault().post(new HistoryFragment.SearchBean(i.getTitle()));
    }
}
