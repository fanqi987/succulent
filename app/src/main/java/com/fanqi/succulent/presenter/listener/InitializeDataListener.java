package com.fanqi.succulent.presenter.listener;

import com.fanqi.succulent.util.local.BeanSaver;
import com.fanqi.succulent.util.local.Saver;

public interface InitializeDataListener {
    void onNetDataSuccess(BeanSaver localDataSaver, Object value);

    void onNetDataFailed();

    void onLocalDataSuccess();

    void onLocalDataFailed();

}
