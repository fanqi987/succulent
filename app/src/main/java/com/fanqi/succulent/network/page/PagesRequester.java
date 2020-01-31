package com.fanqi.succulent.network.page;

import com.fanqi.succulent.bean.Succulent;
import com.fanqi.succulent.network.Requester;
import com.fanqi.succulent.presenter.listener.InitializeByPullListener;

import java.lang.reflect.Constructor;

import okhttp3.ResponseBody;
import okhttp3.internal.http.RealResponseBody;

public class PagesRequester extends Requester {

    public class Name {
        public static final String PAGE = "getPage";
    }

    public void doPullDataFromPage(InitializeByPullListener byPullListener, Object[] values) {
        mExecutor.setBaiduServer();
        mExecutor.initRequester();
        mCallback.setInitializeByPullListener(byPullListener);
        try {
            for (int i = 0; i < values.length; i++) {
                mThreadPool.addPullPageTasks(Name.PAGE,
                        ((Succulent[]) values)[i].getPage_name());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
