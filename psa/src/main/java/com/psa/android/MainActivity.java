package com.psa.android;

import base.BConfig;
import base.BFragmentActivity;
import util.MIntent;

public class MainActivity extends BFragmentActivity {

    @Override
    public void beforeView() {
        super.beforeView();
        setIntent(new MIntent(BConfig.URL,"http://192.168.21.217:8090/home")
                .putExtra(BConfig.TOP_SHOW,false));
    }
}
