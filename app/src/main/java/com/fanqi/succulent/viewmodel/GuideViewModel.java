package com.fanqi.succulent.viewmodel;

import android.view.View;

import com.fanqi.succulent.viewmodel.listener.GuideListener;

public class GuideViewModel {
    /*
     * 进入主页面的监听
     */
    private GuideListener mListener;


    public GuideViewModel(GuideListener listener) {
        mListener = listener;
    }

    public void onClickEnterHome(View v) {
        mListener.onEnterHome();
    }
}
