package com.fanqi.succulent.presenter;

import android.content.Context;
import android.util.Log;

import com.fanqi.succulent.bean.Succulent;
import com.fanqi.succulent.bean.SucculentFull;
import com.fanqi.succulent.network.page.PagesResolver;
import com.fanqi.succulent.presenter.listener.InitializeByPullListener;
import com.fanqi.succulent.presenter.listener.InitializeDataListener;
import com.fanqi.succulent.presenter.listener.InitializePostDataListener;
import com.fanqi.succulent.presenter.listener.ProgressBarCallback;
import com.fanqi.succulent.thread.MyDataThreadPool;
import com.fanqi.succulent.util.LocalDataUtil;
import com.fanqi.succulent.util.NetworkUtil;
import com.fanqi.succulent.util.local.BeanSaver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class InitializeDataPresenter implements
        InitializeDataListener,
        InitializeByPullListener,
        InitializePostDataListener {


    public static final int FIRST_INIT_TABLE_NUMBER = 3;

    private int mRequestSuccessCount;
    private static Context mContext;
    private NetworkUtil mNetworkUtil;
    private LocalDataUtil mLocalDataUtil;
    private ProgressBarCallback mProgressBarCallback;
    private BeanSaver mBeanSaver;
    private PagesResolver mPagesResolver;
    private MyDataThreadPool mMyDataThreadPool;

    private List<SucculentFull> mSucculents;
    private int mPulledNumber;
    private int mSucculentsCount;

    private InitializeDataPresenter() {
        mMyDataThreadPool = new MyDataThreadPool();
        mPagesResolver = new PagesResolver();
        mNetworkUtil = new NetworkUtil();
        mLocalDataUtil = new LocalDataUtil();
        mRequestSuccessCount = 0;
        mPulledNumber = 0;
        mSucculents = new ArrayList<>();

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
        mNetworkUtil.setInitializeDataListener(this);
        //第1次进入,尝试从服务器获取所有植物文字数据，
        mNetworkUtil.initFirstEnterData();

    }

    private void initLocalData() {
        Succulent[] succulents = LocalDataUtil.getAssetsPlantInfo(mContext);
        mSucculentsCount = succulents.length;
        mSucculents = Arrays.asList((SucculentFull[]) succulents);
        //把从文件获取的初始数据列表设置到页面解析者中
        mPagesResolver.initDataList(mSucculents);
        //todo 读取完成，保存到本地数据库
        mBeanSaver = new BeanSaver();
        mBeanSaver.addValue(succulents);
        mBeanSaver.saveToLocal();
        //开始从网上爬虫文字数据
        mNetworkUtil.setInitializeByPullListener(this);
        //开始爬虫
        mNetworkUtil.pullDataFromPage(mBeanSaver.getValue(0));

    }

    @Override
    public void onNetDataSuccess(BeanSaver beanDataSaver, Object[] value) {
        mRequestSuccessCount++;
        beanDataSaver.addValue(value);
        if (mRequestSuccessCount == FIRST_INIT_TABLE_NUMBER) {
            //若成功
            //保存到本地数据库中，最后初始化页面，
            //数据库现在full
            beanDataSaver.saveToLocal();
            beanDataSaver.clean();
            mProgressBarCallback.onCompleteFirstWork();
        }
    }

    @Override
    public void onNetDataFailed() {
        //失败的话，从assets读取植物信息文件
        initLocalData();
    }

    @Override
    public void onPullSuccess(String response, MyDataThreadPool threadPool) {
        //爬虫成功
        //开始处理爬虫数据
        //这里也可以加入线程任务中
        mMyDataThreadPool=threadPool;
        mMyDataThreadPool.addPageResolveTask(mPagesResolver, response);
        //再保存到数据库
        //到达了获取的值那么就增加新任务，保存到db，然后现在这个任务结束
        mPulledNumber++;
        if(mPulledNumber==mSucculentsCount){
            // todo add task 保存到本地数据库
            mMyDataThreadPool.addSaveResolved(mPagesResolver);
            //爬虫结束
            //todo 接在其它回调后面
            mProgressBarCallback.onCompleteFirstWork();
            //再上传到服务器数据库
            //todo 上传完整的数据到服务器
            mNetworkUtil.setPostDataListener(this);
            mMyDataThreadPool.addPostToServerTask(mPagesResolver, mNetworkUtil);
        }
    }

    @Override
    public void onPullFailed() {
        //就是说爬虫失败，再调用onFailedNetwork,之后重新爬虫
        //爬虫失败，才调用onFailedNetwork
        //提示开启网络后，重新尝试，或退出
        mProgressBarCallback.onFailedPullNetwork();
    }

    @Override
    public void onLocalDataSuccess() {

    }

    @Override
    public void onLocalDataFailed() {

    }

    @Override
    public void onPostSuccess(int initializePostDataCount) {
        Log.e("提交初始化数据给服务器已完成",initializePostDataCount+"条");
    }

    @Override
    public void onPostFailed(Throwable throwable) {
        Log.e("提交初始化数据给服务器发生错误",throwable.getMessage());
    }

    @Override
    public void onPostComplete() {
        Log.e("提交初始化数据给服务器已全部完成","<已经全部完成！>");

    }


}
