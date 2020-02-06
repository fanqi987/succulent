package com.fanqi.succulent.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import com.fanqi.succulent.presenter.listener.ProgressBarCallback;
import com.fanqi.succulent.thread.MyDataThreadPool;
import com.fanqi.succulent.util.PreferenceUtil;

public class ProgressUIPresenter implements
        ProgressBarCallback,
        DialogInterface.OnCancelListener,
        DialogInterface.OnClickListener {

    private static final String FIRST_ENTER = "首次加载中，请您稍等下哦~ \n";

    private Activity mActivity;
    private ProgressDialog mProgressDialog;
    private NavigationPresenter mNavigationPresenter;

    public ProgressUIPresenter(Activity activity, NavigationPresenter navigationPresenter) {
        this.mActivity = activity;
        this.mNavigationPresenter = navigationPresenter;
        mProgressDialog = new ProgressDialog(activity);
    }

    public void showProgressDialog() {
        mProgressDialog.setTitle(FIRST_ENTER);
//        mProgressDialog.setOnCancelListener(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "退出", this);
        mProgressDialog.show();
    }

    private void setProgressDialogContent(String content) {
        mProgressDialog.setTitle(FIRST_ENTER + content);
    }

    @Override
    public void onCompleteFirstWork() {
        //  初始化页面
        addTask(UIRunnable.COMPLETE_FIRST_WORK);
    }

    @Override
    public void onGetFirstDataByNet(int nowCount, int tableCount) {
        addTask(UIRunnable.GET_FIRST_DATA_BY_NET, nowCount, tableCount);
    }

    @Override
    public void onPullFirstData(int nowCount, int succulentCount) {
        addTask(UIRunnable.PULL_FIRST_DATA, nowCount, succulentCount);

    }

    @Override
    public void onCancel(DialogInterface dialog) {
        //不获取数据 退出
        dialog.dismiss();
        mActivity.finish();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_NEGATIVE) {
            MyDataThreadPool.stopTasksAndReset();
            dialog.dismiss();
            mActivity.finish();
            System.exit(0);
        }
    }
//    @Override
//    public void onFailedPullNetwork() {
//        //数据爬虫获取失败，对话框提示重新获取或退出
//
//        //现在打算自动重新尝试了，所以不提示失败了
//    }

    private void addTask(int action, Object... parameters) {
        mActivity.runOnUiThread(new UIRunnable(action, parameters));
    }

    class UIRunnable implements Runnable {

        static final int GET_FIRST_DATA_BY_NET = 0;
        static final int PULL_FIRST_DATA = 1;
        static final int COMPLETE_FIRST_WORK = 2;

        private Object[] mParameters;
        private int mAction;

        UIRunnable(int action, Object... parameters) {
            mAction = action;
            mParameters = parameters;
        }

        @Override
        public void run() {
            switch (mAction) {
                case GET_FIRST_DATA_BY_NET:
                    setProgressDialogContent("正在从服务器获取爬好的数据：" + mParameters[0] + "/" + mParameters[1]);
                    break;
                case PULL_FIRST_DATA:
                    setProgressDialogContent("正在爬虫网页数据：" + mParameters[0] + "/" + mParameters[1]);
                    break;
                case COMPLETE_FIRST_WORK:
                    setProgressDialogContent("初始化任务：" + "完成");
                    mNavigationPresenter.firstNavigate();
                    //数据获取成功，取消对话框
                    mProgressDialog.dismiss();
                    //取消第1次进入标志
                    PreferenceUtil.setFirstEnterFlag(false);
                    break;
            }
        }
    }

}
