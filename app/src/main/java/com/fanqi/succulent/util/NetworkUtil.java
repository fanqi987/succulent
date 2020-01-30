package com.fanqi.succulent.util;

import com.fanqi.succulent.activity.adapter.SucculentListAdapter;
import com.fanqi.succulent.network.FirstEnterRequester;
import com.fanqi.succulent.network.MediaInfoRequester;
import com.fanqi.succulent.network.SucculentPostRequester;
import com.fanqi.succulent.network.page.PagesRequester;
import com.fanqi.succulent.network.page.PagesBaseDataResolver;
import com.fanqi.succulent.presenter.listener.InitializeByPullListener;
import com.fanqi.succulent.presenter.listener.InitializeDataListener;
import com.fanqi.succulent.presenter.listener.InitializePostDataListener;
import com.fanqi.succulent.thread.MyDataThreadPool;
import com.fanqi.succulent.viewmodel.listener.ViewModelCallback;

import java.io.Serializable;

public class NetworkUtil {

    private InitializeDataListener mDataListener;
    private InitializeByPullListener mByPullListener;
    private InitializePostDataListener mPostDataListener;
    private ViewModelCallback mViewModelCallback;

    public void initFirstEnterData() {
        FirstEnterRequester requester = new FirstEnterRequester();
        requester.doFirstInfoRequest(mDataListener);
    }

    public void pullDataFromPage(Object[] values) {
        PagesRequester requester = new PagesRequester();
        requester.doPullDataFromPage(mByPullListener, values);
    }

    public void postFullDataToServer(PagesBaseDataResolver pagesBaseDataResolver, MyDataThreadPool myDataThreadPool) {
        SucculentPostRequester requester = new SucculentPostRequester();
        requester.doPostDataToServer(mPostDataListener, pagesBaseDataResolver, myDataThreadPool);
    }

    public void requestGetMediaInfo(String pageName) {
        MediaInfoRequester requester = new MediaInfoRequester();
        requester.doGetMediaInfo(mViewModelCallback, pageName);
    }

    public void requestGetSingleImage(String pageName, Serializable holder) {
        MediaInfoRequester requester = new MediaInfoRequester();
        requester.doGetSingleImageUrl(mViewModelCallback, pageName,  holder);
    }

    public void setInitializePostDataListener(InitializePostDataListener postDataListener) {
        mPostDataListener = postDataListener;
    }

    public void setInitializeByPullListener(InitializeByPullListener pullListener) {
        mByPullListener = pullListener;
    }

    public void setInitializeDataListener(InitializeDataListener dataListener) {
        mDataListener = dataListener;
    }


    public void setViewModelCallback(ViewModelCallback viewModelCallback) {
        mViewModelCallback = viewModelCallback;
    }


}
