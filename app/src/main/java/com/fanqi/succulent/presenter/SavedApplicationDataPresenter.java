package com.fanqi.succulent.presenter;

import android.os.Bundle;

import com.fanqi.succulent.bean.SucculentFull;
import com.fanqi.succulent.util.constant.Constant;

import java.io.Serializable;

public class SavedApplicationDataPresenter {

    private Bundle mBundle = new Bundle();


    public void setDailyFragmentData(SucculentFull succulentFull) {
        mBundle.putSerializable(Constant.SaveInstance.DAILY_FRAGMENT, succulentFull);
    }

    public Serializable getDailyFragmentData() {
        return mBundle.getSerializable(Constant.SaveInstance.DAILY_FRAGMENT);
    }

    public void setListFragmentData() {
    }

    public Serializable getListFragmentData() {
        return mBundle.getSerializable(Constant.SaveInstance.LIST_FRAGMENT);

    }

    public void setFavoriteFragmentData() {
    }

    public Serializable getFavoriteFragmentData() {
        return mBundle.getSerializable(Constant.SaveInstance.FAVORITE_FRAGMENT);
    }
}
