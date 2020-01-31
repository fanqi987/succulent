package com.fanqi.succulent.presenter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.fanqi.succulent.bean.Family;
import com.fanqi.succulent.bean.Genera;
import com.fanqi.succulent.bean.Succulent;
import com.fanqi.succulent.bean.SucculentFull;
import com.fanqi.succulent.network.page.PagesBaseDataResolver;
import com.fanqi.succulent.presenter.listener.InitializeByPullListener;
import com.fanqi.succulent.presenter.listener.InitializeDataListener;
import com.fanqi.succulent.presenter.listener.InitializePostDataListener;
import com.fanqi.succulent.presenter.listener.ProgressBarCallback;
import com.fanqi.succulent.thread.MyDataThreadPool;
import com.fanqi.succulent.util.LocalDataUtil;
import com.fanqi.succulent.util.NetworkUtil;
import com.fanqi.succulent.util.local.BeanSaver;

import org.litepal.LitePal;

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
    private ProgressBarCallback mProgressBarCallback;
    private BeanSaver mBeanSaver;
    private PagesBaseDataResolver mPagesBaseDataResolver;

    private List<SucculentFull> mSucculentFulls;
    private int mPulledNumber;
    private int mSucculentsCount;

    private int mInitLocalDataCount = 0;

    private InitializeDataPresenter() {
        mPagesBaseDataResolver = new PagesBaseDataResolver();
        mNetworkUtil = new NetworkUtil();
        mRequestSuccessCount = 0;
        mPulledNumber = 0;
        mSucculentFulls = new ArrayList<>();

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

    private synchronized void initLocalData() {
        ++mInitLocalDataCount;
        Log.e("init local data", String.valueOf(mInitLocalDataCount));
        LitePal.deleteAll(Succulent.class);
        LitePal.deleteAll(Family.class);
        LitePal.deleteAll(Genera.class);

        SucculentFull[] succulentFulls = LocalDataUtil.getAssetsPlantInfo(mContext);
        mSucculentFulls = Arrays.asList(succulentFulls);
        mSucculentsCount = mSucculentFulls.size();
        //把从文件获取的初始数据列表设置到页面解析者中
        mPagesBaseDataResolver.initDataList(mSucculentFulls);
        //读取完成，保存到 beanSaver
        Succulent[] succulents = new Succulent[succulentFulls.length];
        for (int i = 0; i < succulentFulls.length; i++) {
            succulents[i] = succulentFulls[i].getSucculent();
        }
        mBeanSaver = new BeanSaver();
        mBeanSaver.addValue(succulents);
//        mBeanSaver.saveToLocal();
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
            Log.e("服务器初始化数据成功", "服务器初始化数据成功");
            beanDataSaver.saveToLocal();
            beanDataSaver.clean();
            mProgressBarCallback.onCompleteFirstWork();
        }
    }

    @Override
    public void onNetDataFailed() {
        //失败的话，从assets读取植物信息文件
        Log.e("服务器初始化数据失败", "服务器获取初始化数据失败");
        if (mInitLocalDataCount == 0) {
            initLocalData();
        }
    }

    @Override
    public void onPullSuccess(String response) {
        //爬虫成功
        //开始处理爬虫数据
        mPagesBaseDataResolver.resolve(response);
        //再保存到数据库
        //到达了获取的值那么就增加新任务，保存到db，然后现在这个任务结束
        mPulledNumber++;
        Log.e("爬虫成功", "爬虫成功" + mPulledNumber + "条");

        if (mPulledNumber == mSucculentsCount) {
            Log.e("爬虫全部成功", "爬虫全部成功");
            //  保存到本地数据库
            mPagesBaseDataResolver.saveToDB();
            //爬虫结束
            //接在其它回调后面
            ((Activity) mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mProgressBarCallback.onCompleteFirstWork();
                }
            });
            //再上传到服务器数据库
            //上传完整的数据到服务器
            mNetworkUtil.setInitializePostDataListener(this);
            mNetworkUtil.postFullDataToServer(mPagesBaseDataResolver);
        }
    }

    @Override
    public void onPullFailed() {
        //就是说爬虫失败，再调用onFailedNetwork,之后重新爬虫
        //爬虫失败，才调用onFailedNetwork
        //提示开启网络后，重新尝试，或退出
        Log.e("爬虫失败", "爬虫失败");

        mProgressBarCallback.onFailedPullNetwork();
    }


    @Override
    public void onPostSuccess(int initializePostDataCount) {
        Log.e("提交初始化数据已完成", initializePostDataCount + "条");
    }

    @Override
    public void onPostFailed(Throwable throwable) {
        Log.e("提交初始化数据发生错误", throwable.getMessage());
    }

    @Override
    public void onPostComplete(MyDataThreadPool threadPool) {
        Log.e("提交初始化数据已全部完成", "<已经全部完成！>");
        threadPool.getThreadPool().shutdown();
    }
//    @Override
//    public void onLocalDataSuccess() {
//
//    }
//
//    @Override
//    public void onLocalDataFailed() {
//
//    }

}
