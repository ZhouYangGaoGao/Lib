package mvp.home.view;

import android.content.Intent;
import android.text.TextUtils;

import com.zhy.wanandroid.R;

import adapter.group.BaseViewHolder;
import base.BConfig;
import base.BBannerFragment;
import base.BWebFragment;
import base.Manager;
import custom.page.ScaleInTransformer;
import mvp.chapter.model.Article;
import rx.Observable;
import util.GoTo;

public class BannerFragment extends BBannerFragment<Article> {
    @Override
    public void beforeView() {
        banner.itemLayoutId = R.layout.item_banner;
        transformer = new ScaleInTransformer();
        banner.useIndicate = false;
    }

    @Override
    public void convert(BaseViewHolder h, Article data) {
        h.setText(R.id.mTextView, TextUtils.isEmpty(data.getTitle()) ? data.getDesc() : data.getTitle());
        h.setImage(R.id.mImageView, data.getImagePath());
        h.setClick(v -> GoTo.start(BWebFragment.class, new Intent().putExtra(BConfig.URL, data.getUrl())));
    }

    @Override
    protected Observable<?> get() {
        return Manager.getApi().banner();
    }
}
