package com.fanqi.succulent.presenter.listener;

import okhttp3.ResponseBody;

public interface InitializeByPullListener {
    void onPullSuccess(String response);

    void onPullFailed();

}
