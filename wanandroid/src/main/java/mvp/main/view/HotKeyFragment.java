package mvp.main.view;

import android.view.Gravity;

import com.zhy.wanandroid.R;

import org.greenrobot.eventbus.EventBus;

import adapter.ViewHolder;
import base.BListFragment;
import base.Manager;
import custom.HistoryFragment;
import custom.TextView;
import mvp.chapter.model.Article;
import rx.Observable;

public class HotKeyFragment extends BListFragment<Article> {

    @Override
    public void beforeView() {
        grid.numColumns = 3;
        info.showTop = false;
        grid.bgColor = 0xffeeeeee;
        grid.expand = true;
        card.card = false;
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
