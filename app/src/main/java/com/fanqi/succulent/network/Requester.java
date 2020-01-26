package com.fanqi.succulent.network;

import thread.NetWorkThreadPool;

public abstract class Requester {
    protected RetrofitCallback mCallback;
    protected RetrofitExecutor mExecutor;
    protected NetWorkThreadPool mThreadPool;

    public Requester() {
        mCallback = new RetrofitCallback<>();
        mExecutor = new RetrofitExecutor(mCallback);
        mThreadPool = new NetWorkThreadPool(mExecutor);
        mCallback.setThreadPool(mThreadPool.getThreadPool());
    }
}
