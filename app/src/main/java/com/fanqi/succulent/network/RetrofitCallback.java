package com.fanqi.succulent.network;

import com.fanqi.succulent.presenter.listener.InitializeByPullListener;
import com.fanqi.succulent.presenter.listener.InitializeDataListener;
import com.fanqi.succulent.presenter.listener.InitializePostDataListener;
import com.fanqi.succulent.thread.MyDataThreadPool;
import com.fanqi.succulent.util.local.BeanSaver;
import com.fanqi.succulent.util.local.Saver;
import com.fanqi.succulent.util.provider.SaverProvider;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class RetrofitCallback<T> implements Observer<T> {

    public static final String RETROFIT_CALLBACK_FLAG = "retrofit_callback";

    private T mValue;
    private Saver mBeanDataSaver;

    private InitializeDataListener mInitializeDataListener;
    private InitializeByPullListener mInitializeByPullListener;
    private InitializePostDataListener mInitializePostDataListener;

    private Disposable mDisposable;

    private MyDataThreadPool mThreadPool;

    private int mInitializePostDataCount;

    public RetrofitCallback() {
        mInitializePostDataCount = 0;
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
        initializePostDataProcess(value);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        //初始化数据监听非空时，则通知数据失败
        if (mInitializeDataListener != null) {
            mInitializeDataListener.onNetDataFailed();
        }
        if(mInitializeByPullListener!=null){
            mInitializeByPullListener.onPullFailed();
        }
        if (mInitializePostDataListener != null) {
            mInitializePostDataListener.onPostFailed(e);
        }
    }

    private void initializeDataProcess(T value) {
        //初始化数据监听非空时，
        // 判断是否数据大小正确
        // 再通知数据成功,3个不同实体类的数组
        if (mInitializeDataListener != null) {
            mBeanDataSaver = new SaverProvider(value).getSaver();
            if (((BeanSaver) mBeanDataSaver).checkWrongCount(value)) {
                //立即关闭线程池，并通知网页api获取失败
                mThreadPool.getThreadPool().shutdownNow();
                mInitializeDataListener.onNetDataFailed();
                return;
            }
            mValue = value;
            mInitializeDataListener.onNetDataSuccess((BeanSaver) mBeanDataSaver, (Object[]) mValue);
        }
    }

    private void initializeByPullProcess(T value) {
        //爬虫数据监听非空时，则通知数据成功
        mValue = value;
        if (mInitializeByPullListener != null) {
            try {
                mInitializeByPullListener.onPullSuccess(((ResponseBody) mValue).string(), mThreadPool);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initializePostDataProcess(T value) {
        if (mInitializePostDataListener != null) {
            mInitializePostDataCount++;
            mInitializePostDataListener.onPostSuccess(mInitializePostDataCount);
            if (mInitializePostDataCount ==
                    FirstEnterRequester.FAMILY_COUNT +
                            FirstEnterRequester.GENERAS_COUNT +
                            FirstEnterRequester.SUCCULENT_COUNT) {
                mInitializePostDataListener.onPostComplete(mThreadPool);
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

    public void setInitializePostDataListener(InitializePostDataListener postDataListener) {
        this.mInitializePostDataListener = postDataListener;
    }

    public void setThreadPool(MyDataThreadPool threadPool) {
        mThreadPool = threadPool;
    }


}
