package mvp.home.view;

import com.zhy.wanandroid.R;

import base.BPagerFragment;
import base.Manager;
import base.Subs;
import mvp.home.model.Banner;
import rx.Subscription;

public class BannerFragment extends BPagerFragment<Banner> {
    @Override
    public void beforeView() {
        itemLayoutId = R.layout.item_banner;
    }

    @Override
    public void convert(Viewholder h, Banner data) {
        h.setText(R.id.mTextView, data.getDesc());
        h.setImage(R.id.mImageView,data.getImagePath());
    }

    @Override
    protected Subscription get() {
        return Subs.get(this,Manager.getApi().banner());
    }
}
