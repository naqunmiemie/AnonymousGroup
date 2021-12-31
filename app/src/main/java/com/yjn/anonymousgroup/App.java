package com.yjn.anonymousgroup;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import com.tcl.common.Common;
import com.yjn.anonymousgroup.db.Database;

public class App extends Application implements ViewModelStoreOwner {
    public static App mInstance;
    private ViewModelStore mAppViewModelStore;
    private static Database database;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        init();
    }

    private void init() {
        mAppViewModelStore = new ViewModelStore();
        database = Database.getInstance(this);
        Common.init(mInstance);
    }

    public static App getInstance() {
        return mInstance;
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return mAppViewModelStore;
    }

    public static Database getDatabase(){
        return database;
    }
}
