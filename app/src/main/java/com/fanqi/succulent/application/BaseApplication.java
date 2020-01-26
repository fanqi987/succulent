package com.fanqi.succulent.application;

import android.app.Application;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;
import org.litepal.LitePalDB;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
    }
}
