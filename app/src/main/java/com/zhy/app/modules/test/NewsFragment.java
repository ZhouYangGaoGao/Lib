package com.zhy.app.modules.test;

import adapter.CommonAdapter;
import com.zhy.app.R;
import com.zhy.app.base.Manager;
import com.zhy.app.base.Subs;

import adapter.ViewHolder;
import base.BSmartFragment;
import rx.Observable;
import rx.Subscription;

public class NewsFragment extends BSmartFragment<NewsModel> {
    @Override
    public void beforeView() {
        isCard = 10;
        itemLayoutId = R.layout.item_text;
    }

    @Override
    protected Observable<?> get() {
        return  Manager.get().getNewsList();
    }

    @Override
    protected void convert(ViewHolder h, NewsModel i) {
        h.setText(R.id.title, i.getIntro());
    }
}
