package com.fanqi.succulent.presenter.listener;

import com.fanqi.succulent.thread.MyDataThreadPool;

public interface InitializePostDataListener {

    void onPostSuccess(int initializePostDataCount);

    void onPostFailed(Throwable throwable);

    void onPostComplete(MyDataThreadPool threadPool);
}
