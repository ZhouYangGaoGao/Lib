package mvp.main.view;

import android.view.Gravity;

import com.zhy.wanandroid.R;

import adapter.ViewHolder;
import base.BSmartFragment;
import base.Manager;
import custom.SmartView;
import mvp.chapter.model.Article;
import rx.Observable;

public class HotKeyFragment extends BSmartFragment<Article> {

    private SmartView histSmartView;

    public HotKeyFragment setHistSmartView(SmartView histSmartView) {
        this.histSmartView = histSmartView;
        return this;
    }

    @Override
    public void beforeView() {
        isCard = 5;
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
        h.setText(R.id.title, i.getTitle());
        h.getTextView(R.id.title).setGravity(Gravity.CENTER);
    }

    @Override
    protected void onItemClick(ViewHolder h, Article i) {
        if (histSmartView!=null)
        histSmartView.getListener().onClick(histSmartView,3,0);
    }
}
