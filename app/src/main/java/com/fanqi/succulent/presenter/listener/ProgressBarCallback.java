package com.fanqi.succulent.presenter.listener;

public interface ProgressBarCallback {

    //当异步数据结束，需要消失进度框
    void onCompleteFirstNetwork();

    //当异步数据失败，需要改变进度框
    void onFailedPullNetwork();

    //当本地数据结束
    void onCompleteLocal();

    //当本地数据失败
    void onFailedLocal();

}
