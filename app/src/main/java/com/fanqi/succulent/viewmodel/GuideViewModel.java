package com.fanqi.succulent.viewmodel;

import android.view.View;

import com.fanqi.succulent.viewmodel.bean.GuideBean;
import com.fanqi.succulent.viewmodel.listener.GuideListener;

public class GuideViewModel {
    /*
     * 进入主页面的监听
     */
    public GuideBean mBean;
    private GuideListener mListener;


    public GuideViewModel(GuideListener listener) {
        mListener = listener;
        mBean=new GuideBean();
    }

    public void onClickEnterHome(View v) {
        mListener.onEnterHome();
    }

    public void setGuideEnterText(String s) {
        mBean.setGuideEnterText(s);
    }
}
