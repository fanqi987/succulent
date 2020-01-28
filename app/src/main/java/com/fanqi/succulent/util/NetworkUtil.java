package com.fanqi.succulent.util;

import com.fanqi.succulent.network.FirstEnterRequester;
import com.fanqi.succulent.network.SucculentPostRequester;
import com.fanqi.succulent.network.page.PagesRequester;
import com.fanqi.succulent.network.page.PagesResolver;
import com.fanqi.succulent.presenter.listener.InitializeByPullListener;
import com.fanqi.succulent.presenter.listener.InitializeDataListener;
import com.fanqi.succulent.presenter.listener.InitializePostDataListener;
import com.fanqi.succulent.thread.MyDataThreadPool;

public class NetworkUtil {

    private InitializeDataListener mDataListener;
    private InitializeByPullListener mByPullListener;
    private InitializePostDataListener mPostDataListener;

    public void initFirstEnterData() {
        FirstEnterRequester requester = new FirstEnterRequester();
        requester.doFirstInfoRequest(mDataListener);
    }

    public void pullDataFromPage(Object[] values) {
        PagesRequester requester = new PagesRequester();
        requester.doPullDataFromPage(mByPullListener,values);
    }

    public void postFullDataToServer(PagesResolver pagesResolver, MyDataThreadPool myDataThreadPool) {
        SucculentPostRequester requester=new SucculentPostRequester();
        requester.doPostDataToServer(mPostDataListener,pagesResolver,myDataThreadPool);
    }

    public void setPostDataListener(InitializePostDataListener postDataListener){
        mPostDataListener =postDataListener;
    }

    public void setInitializeByPullListener(InitializeByPullListener pullListener) {
        mByPullListener = pullListener;
    }

    public void setInitializeDataListener(InitializeDataListener dataListener) {
        mDataListener = dataListener;
    }

}
