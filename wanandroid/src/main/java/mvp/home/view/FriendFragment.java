package mvp.home.view;

import android.content.Intent;

import com.zhy.wanandroid.R;

import java.util.List;

import adapter.ViewHolder;
import base.BConfig;
import base.BSmartFragment;
import base.BWebFragment;
import base.Manager;
import custom.TextView;
import mvp.home.model.Friend;
import rx.Observable;
import util.GoTo;

public class FriendFragment extends BSmartFragment<Friend> {

    @Override
    public void beforeView() {
        isCard = 10;
        numColumns = 2;
    }

    @Override
    public void afterView() {
        refreshLayout.setEnablePureScrollMode(true);
    }

    @Override
    public void onData(List<Friend> datas) {
        super.onData(datas);
        Friend friend = new Friend();
        friend.setName("custom.TextView:文字超过宽度时,自动缩小文字");
        mData.add(0,friend);
        upData();
    }

    @Override
    protected Observable<?> get() {
        return Manager.getApi().friend();
    }

    @Override
    protected void convert(ViewHolder h, Friend i) {
        TextView textView = h.getView(R.id.title);
        textView.setSingleLine();
        textView.setAutoZoom(true);
        textView.setText(i.getName());
    }

    @Override
    protected void onItemClick(ViewHolder h, Friend i) {
        GoTo.start(BWebFragment.class, new Intent().putExtra(BConfig.URL, i.getLink()));
    }
}
