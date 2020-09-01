package com.psa.android;

import base.BApp;
import base.BConfig;

public class PSAApp extends BApp {

    @Override
    protected void initApp() {
        BConfig.get().setFullScreen(true);
    }
}
