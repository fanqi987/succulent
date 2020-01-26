package com.fanqi.succulent.network;

import com.fanqi.succulent.presenter.listener.InitializeByPullListener;
import com.fanqi.succulent.presenter.listener.InitializeDataListener;
import com.fanqi.succulent.util.local.BeanSaver;
import com.fanqi.succulent.util.local.Saver;
import com.fanqi.succulent.util.provider.SaverProvider;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class RetrofitCallback<T> implements Observer<T> {

    public static final String RETROFIT_CALLBACK_FLAG = "retrofit_callback";

    private T mValue;
    private Saver mBeanDataSaver;

    private InitializeDataListener mInitializeDataListener;
    private InitializeByPullListener mInitializeByPullListener;

    private Disposable mDisposable;

    private ExecutorService mThreadPool;

    public RetrofitCallback() {
    }

    public void setValue(T value) {
        mValue = value;
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
    }

    @Override
    public void onNext(T value) {
        initializeDataProcess(value);
        initializeByPullProcess(value);
    }


    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        //初始化数据监听非空时，则通知数据失败
        if (mInitializeDataListener != null) {
            mInitializeDataListener.onNetDataFailed();
        }
    }

    private void initializeDataProcess(T value) {
        //初始化数据监听非空时，
        // 判断是否数据大小正确
        // 再通知数据成功
        if (mInitializeDataListener != null) {
            mBeanDataSaver = new SaverProvider(value).getSaver();
            if (((BeanSaver) mBeanDataSaver).checkWrongCount(value)) {
                mThreadPool.shutdownNow();
                mInitializeDataListener.onNetDataFailed();
                return;
            }
            mValue = value;
            mInitializeDataListener.onNetDataSuccess((BeanSaver) mBeanDataSaver, mValue);
        }
    }

    private void initializeByPullProcess(T value) {
        //爬虫数据监听非空时，则通知数据成功
        if (mInitializeByPullListener != null) {
            try {
                mInitializeByPullListener.onPullSuccess(((ResponseBody) value).string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onComplete() {
        mDisposable.dispose();
    }

    public void setInitializeDataListener(InitializeDataListener initializeDataListener) {
        this.mInitializeDataListener = initializeDataListener;
    }

    public void setInitializeByPullListener(InitializeByPullListener byPullListener) {
        this.mInitializeByPullListener = byPullListener;
    }

    public void setThreadPool(ExecutorService threadPool) {
        mThreadPool = threadPool;

    }
}
