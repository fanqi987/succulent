package com.fanqi.succulent.presenter.listener;

import com.fanqi.succulent.thread.MyDataThreadPool;

import java.util.concurrent.ExecutorService;

public interface InitializeByPullListener {
    void onPullSuccess(String response);
    void onPullFailed();
}
