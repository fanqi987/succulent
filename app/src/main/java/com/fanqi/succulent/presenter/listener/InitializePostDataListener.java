package com.fanqi.succulent.presenter.listener;

public interface InitializePostDataListener {

    void onPostSuccess(int initializePostDataCount);

    void onPostFailed(Throwable throwable);

    void onPostComplete();
}
