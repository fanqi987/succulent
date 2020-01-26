package com.fanqi.succulent.util;

import com.fanqi.succulent.network.FirstEnterRequester;
import com.fanqi.succulent.network.page.PagesRequester;
import com.fanqi.succulent.presenter.InitializeDataPresenter;
import com.fanqi.succulent.presenter.listener.InitializeByPullListener;
import com.fanqi.succulent.presenter.listener.InitializeDataListener;

public class NetworkUtil {

    private InitializeDataListener mDataListener;
    private InitializeByPullListener mByPullListener;

    public void setInitializeByPullListener(InitializeByPullListener pullListener) {
        mByPullListener = pullListener;
    }

    public void setInitializeDataListener(InitializeDataListener dataListener) {
        mDataListener = dataListener;
    }

    public void initFirstEnterData() {
        FirstEnterRequester requester = new FirstEnterRequester();
        requester.doFirstInfoRequest(mDataListener);
    }

    public void pullDataFromPage(Object[] values) {
        PagesRequester requester = new PagesRequester();
        requester.doPullDataFromPage(mByPullListener,values);
    }
}
