package com.fanqi.succulent.presenter.listener;

import com.fanqi.succulent.util.local.BeanSaver;
import com.fanqi.succulent.util.local.Saver;

public interface InitializeDataListener {
    //初始化数据，从网上获取成功，实体类数组
    void onNetDataSuccess(BeanSaver localDataSaver, Object[] value);

    void onNetDataFailed();

//    void onLocalDataSuccess();
//
//    void onLocalDataFailed();

}
