package com.zhy.app.modules.test;

import com.zhy.app.R;
import com.zhy.app.base.Manager;

import adapter.ViewHolder;
import base.BSmartFragment;
import rx.Observable;

public class NewsFragment extends BSmartFragment<NewsModel> {
    @Override
    public void beforeView() {
        card.cardDiver = 10;
        grid.itemLayoutId = R.layout.item_text;
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
