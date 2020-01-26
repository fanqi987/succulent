package com.fanqi.succulent.presenter;

import android.content.Context;

import com.fanqi.succulent.bean.Succulent;
import com.fanqi.succulent.presenter.listener.InitializeByPullListener;
import com.fanqi.succulent.presenter.listener.InitializeDataListener;
import com.fanqi.succulent.presenter.listener.ProgressBarCallback;
import com.fanqi.succulent.util.LocalDataUtil;
import com.fanqi.succulent.util.NetworkUtil;
import com.fanqi.succulent.util.local.BeanSaver;


public class InitializeDataPresenter implements
        InitializeDataListener, InitializeByPullListener {


    public static final int FIRST_INIT_TABLE_NUMBER = 3;
    private int mRequestSuccessCount;
    private static Context mContext;
    private NetworkUtil mNetworkDataUtil;
    private LocalDataUtil mLocalDataUtil;

    private ProgressBarCallback mProgressBarCallback;

    private InitializeDataPresenter() {
        mNetworkDataUtil = new NetworkUtil();
        mLocalDataUtil = new LocalDataUtil();
        mRequestSuccessCount = 0;
    }


    private static class InitializeDataPresenterCreator {
        private static InitializeDataPresenter sPresenter = new InitializeDataPresenter();
    }

    public static InitializeDataPresenter getInstance() {
        return InitializeDataPresenterCreator.sPresenter;
    }


    public void setProgressBarListener(ProgressBarCallback callback) {
        this.mProgressBarCallback = callback;

    }

    public void initFirstData(Context context) {
        mContext = context;
        mNetworkDataUtil.setInitializeDataListener(this);
        //第1次进入,尝试从服务器获取所有植物文字数据，
        mNetworkDataUtil.initFirstEnterData();

    }

    private void initLocalData() {
        Succulent[] succulents = LocalDataUtil.getAssetsPlantInfo(mContext);
        //todo 读取完成，保存到本地数据库
        BeanSaver beanSaver = new BeanSaver();
        beanSaver.addValue(succulents);
        beanSaver.saveToLocal();
        //开始从网上爬虫文字数据
        mNetworkDataUtil.pullDataFromPage(beanSaver.getValues());
        //开始爬虫
        //爬虫结束
    }

    @Override
    public void onNetDataSuccess(BeanSaver beanDataSaver, Object value) {
        mRequestSuccessCount++;
        beanDataSaver.addValue(value);
        if (mRequestSuccessCount == FIRST_INIT_TABLE_NUMBER) {
            //若成功
            //保存到本地数据库中，最后初始化页面，
            //数据库现在full
            beanDataSaver.saveToLocal();
            beanDataSaver.clean();
            mProgressBarCallback.onCompleteFirstNetwork();
        }
    }

    @Override
    public void onNetDataFailed() {
        //失败的话，从assets读取植物信息文件
        initLocalData();


        //爬虫成功
        //保存到本地数据库
        //再上传到服务器数据库
        //数据库现在full


        mProgressBarCallback.onCompleteFirstNetwork();
        //就是说爬虫失败，再调用onFailedNetwork,之后重新爬虫
        //爬虫失败，才调用onFailedNetwork
        //提示开启网络后，重新尝试，或退出
    }

    @Override
    public void onPullSuccess(String response) {
        //开始处理爬虫数据
        //再保存到数据库
    }

    @Override
    public void onPullFailed() {

    }

    @Override
    public void onLocalDataSuccess() {

    }

    @Override
    public void onLocalDataFailed() {

    }


}
