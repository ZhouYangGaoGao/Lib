package com.zhy.test.base;

import base.BList;
import base.BPresenter;
import base.BView;
import util.Suber;

public class MainPresenter extends BPresenter< BView<BList<Object>>> {
    public void getList(){
        subscribe(new Suber<BList<Object>>(mView,Manager.getManager().getNewsList()));
    }
}
