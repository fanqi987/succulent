package com.fanqi.succulent.network;

import com.fanqi.succulent.thread.MyDataThreadPool;

import java.lang.reflect.Constructor;

import okhttp3.ResponseBody;

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

    protected ResponseBody getResponseBody() throws Exception {
        Constructor constructor = ResponseBody.class.getConstructor();
        constructor.setAccessible(true);
        return (ResponseBody) constructor.newInstance();
    }
}
