package com.fanqi.succulent.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.fanqi.succulent.presenter.listener.InitializeByPullListener;
import com.fanqi.succulent.presenter.listener.InitializeDataListener;
import com.fanqi.succulent.presenter.listener.InitializePostDataListener;
import com.fanqi.succulent.thread.MyDataThreadPool;
import com.fanqi.succulent.util.constant.Constant;
import com.fanqi.succulent.util.local.BeanSaver;
import com.fanqi.succulent.util.local.Saver;
import com.fanqi.succulent.util.provider.SaverProvider;
import com.fanqi.succulent.viewmodel.listener.ViewModelCallback;

import java.io.IOException;
import java.io.InputStream;

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
    private ViewModelCallback mViewModelCallback;

    private Disposable mDisposable;

    private MyDataThreadPool mThreadPool;

    private int mInitializePostDataCount;

    private int mPullPagesCount = 0;

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
        mediaDataProcess(value);
    }

    private void mediaDataProcess(T value) {
        if (mViewModelCallback != null) {
            InputStream is;
            ResponseBody responseBody = (ResponseBody) value;
            is = responseBody.byteStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constant.ViewModel.IMAGE, bitmap);
            mViewModelCallback.onSuccessed(bundle);
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e("onError", e.toString());
        e.printStackTrace();
        if (MyDataThreadPool.getThreadPool() == null) {
            return;
        }
        //初始化数据监听非空时，则通知数据失败
        if (mInitializeDataListener != null) {
            MyDataThreadPool.getThreadPool().shutdownNow();
            mInitializeDataListener.onNetDataFailed();
        }
        if (mInitializeByPullListener != null) {
            //爬虫失败的话，立刻关闭线程池
            MyDataThreadPool.getThreadPool().shutdownNow();
            mInitializeByPullListener.onPullFailed();
        }
        if (mInitializePostDataListener != null) {
            mInitializePostDataListener.onPostFailed(e);
        }
        //todo 因为使用glide而这里没有调用了
        if (mViewModelCallback != null) {
            mViewModelCallback.onFailed(e);
        }
    }

    private void initializeDataProcess(T value) {
        //初始化数据监听非空时，
        // 判断是否数据大小正确
        // 再通知数据成功,3个不同实体类的数组
        if (mInitializeDataListener != null) {
            Log.e("onNext DataProcess", value.toString());
            Log.e("onNext Data size", "Size:  " + ((Object[]) value).length);

            mBeanDataSaver = new SaverProvider(value).getSaver();
            if (!((BeanSaver) mBeanDataSaver).checkCount(value)) {
                //立即关闭线程池，并通知网页api获取失败
                mInitializeDataListener.onNetDataFailed();
                return;
            }
            mValue = value;
            mInitializeDataListener.onNetDataSuccess((Object[]) mValue);
        }
    }

    private void initializeByPullProcess(T value) {
        //爬虫数据监听非空时，则通知数据成功
        mValue = value;
        if (mInitializeByPullListener != null) {
            Log.e("onNext PullProcess", value.toString());
            Log.e("onNext PullProcess", String.valueOf(++mPullPagesCount));

            try {
                mInitializeByPullListener.onPullSuccess(((ResponseBody) mValue).string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initializePostDataProcess(T value) {
        if (mInitializePostDataListener != null) {
            Log.e("onNext PostDataProcess", value.toString());
            mInitializePostDataCount++;
            mInitializePostDataListener.onPostSuccess(mInitializePostDataCount);
            if (mInitializePostDataCount ==
                    Constant.FAMILY_COUNT +
                            Constant.GENERAS_COUNT +
                            Constant.SUCCULENT_COUNT) {
                mInitializePostDataListener.onPostComplete(mThreadPool);
            }
        }
    }

    @Override
    public void onComplete() {
        Log.e("onComplete", "onComplete");
        mDisposable.dispose();
    }

    public void setThreadPool(MyDataThreadPool threadPool) {
        mThreadPool = threadPool;
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

    public void setViewModelCallBack(ViewModelCallback viewModelCallback) {
        this.mViewModelCallback = viewModelCallback;
    }
}
