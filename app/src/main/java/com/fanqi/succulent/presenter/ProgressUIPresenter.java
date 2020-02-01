package com.fanqi.succulent.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import com.fanqi.succulent.presenter.listener.ProgressBarCallback;
import com.fanqi.succulent.util.PreferenceUtil;

public class ProgressUIPresenter implements
        ProgressBarCallback,
        DialogInterface.OnCancelListener {
    private Activity mActivity;
    private ProgressDialog mProgressDialog;
    private NavigationPresenter mNavigationPresenter;

    public ProgressUIPresenter(Activity activity, NavigationPresenter navigationPresenter) {
        this.mActivity = activity;
        this.mNavigationPresenter = navigationPresenter;
        mProgressDialog = new ProgressDialog(activity);
    }

    public void showProgressDialog(String title) {
        mProgressDialog.setTitle(title);
        mProgressDialog.setOnCancelListener(this);
        mProgressDialog.show();
    }


    @Override
    public void onCompleteFirstWork() {
        //数据获取成功，取消对话框
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        //取消第1次进入标志
        PreferenceUtil.setFirstEnterFlag(false);

        //  初始化页面
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mNavigationPresenter.firstNavigate();
            }
        });
    }

    @Override
    public void onFailedPullNetwork() {

        //数据爬虫获取失败，对话框提示重新获取或退出
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        //不获取数据 退出
        dialog.dismiss();
        mActivity.finish();
    }
//    @Override
//    public void onCompleteLocal() {
//
//    }
//
//    @Override
//    public void onFailedLocal() {
//
//    }


}
