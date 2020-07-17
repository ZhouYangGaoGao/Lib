package mvp.home.view;

import android.content.Intent;
import android.text.TextUtils;

import com.zhy.wanandroid.R;

import adapter.ViewHolder;
import base.BConfig;
import base.BPagerFragment;
import base.BWebFragment;
import base.Manager;
import mvp.chapter.model.Article;
import rx.Observable;
import util.GoTo;

public class BannerFragment extends BPagerFragment<Article> {
    @Override
    public void beforeView() {
        itemLayoutId = R.layout.item_banner;
    }

    @Override
    public void convert(ViewHolder h, Article data) {
        h.setText(R.id.mTextView, TextUtils.isEmpty(data.getTitle()) ? data.getDesc() : data.getTitle());
        h.setImage(R.id.mImageView, data.getImagePath());
        h.setClick(v -> GoTo.start(BWebFragment.class, new Intent().putExtra(BConfig.URL, data.getUrl())));
    }

    @Override
    protected Observable<?> get() {
        return Manager.getApi().banner();
    }
}
