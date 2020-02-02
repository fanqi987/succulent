package com.fanqi.succulent.application;

import android.app.Application;
import android.os.Bundle;

import com.fanqi.succulent.bean.SucculentFull;
import com.fanqi.succulent.presenter.SavedApplicationDataPresenter;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;
import org.litepal.LitePalDB;

public class BaseApplication extends Application {



    private SavedApplicationDataPresenter mDataPresenter;

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        mDataPresenter = new SavedApplicationDataPresenter();
    }

    public SavedApplicationDataPresenter getDataPresenter() {
        return mDataPresenter;
    }


}
