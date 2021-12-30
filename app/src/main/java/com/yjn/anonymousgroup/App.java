package com.yjn.anonymousgroup;

import android.app.Application;

import com.tcl.common.Common;

public class App extends Application {
    public static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        init();
    }

    private void init() {
        Common.init(mInstance);
    }

    public static App getInstance() {
        return mInstance;
    }
}
