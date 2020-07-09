package com.zhy.app.modules.test;

import com.zhy.android.adapter.CommonAdapter;
import com.zhy.app.R;
import com.zhy.app.base.Manager;
import com.zhy.app.base.Subs;

import base.BSmartFragment;
import rx.Subscription;

public class NewsFragment extends BSmartFragment<NewsModel> {
    @Override
    public void beforeView() {
        isCard = 10;
        itemLayoutId = R.layout.item_text;
    }

    @Override
    protected Subscription get() {
        return Subs.get(this, Manager.get().getNewsList());
    }

    @Override
    protected void convert(CommonAdapter.ViewHolder h, NewsModel i) {
        h.setText(R.id.title, i.getIntro());
    }
}
