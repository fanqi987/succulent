package com.fanqi.succulent.presenter.listener;

public interface ProgressBarCallback {

    //当异步数据结束，需要消失进度框
    void onCompleteFirstWork();

    //服务器获取数据的通知
    void onGetFirstDataByNet(int nowCount, int tableCount);

    //爬虫获取数据的通知
    void onPullFirstData(int nowCount, int succulentCount);

//    //当异步数据失败，需要改变进度框
//    void onFailedPullNetwork();


//    //当本地数据结束
//    void onCompleteLocal();
//
//    //当本地数据失败
//    void onFailedLocal();

}
