package com.fanqi.succulent.network;

import com.fanqi.succulent.thread.MyDataThreadPool;

public abstract class Requester {
    protected RetrofitCallback mCallback;
    protected RetrofitExecutor mExecutor;
    protected MyDataThreadPool mThreadPool;

    public Requester() {
        mCallback = new RetrofitCallback<>();
        mExecutor = new RetrofitExecutor(mCallback);
        mThreadPool = new MyDataThreadPool(mExecutor);
        mCallback.setThreadPool(mThreadPool);
    }
}
