package com.fanqi.succulent.network;

import android.os.Bundle;

import com.fanqi.succulent.BuildConfig;
import com.fanqi.succulent.network.callback.MediaPageResolveCallback;
import com.fanqi.succulent.viewmodel.SucculentDailyViewModel;
import com.fanqi.succulent.viewmodel.listener.ViewModelCallback;

import java.util.List;

import okhttp3.ResponseBody;

public class MediaInfoRequester extends Requester implements MediaPageResolveCallback {


    private ViewModelCallback mViewModelCallback;

    public void doGetMediaInfo(ViewModelCallback viewModelCallback, String pageName) {
        mViewModelCallback = viewModelCallback;
        mExecutor.setBaiduImageServer();
        mExecutor.initRequester();
        mCallback.setViewModelCallBack(viewModelCallback);
        try {
            mCallback.setValue(getResponseBody());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mThreadPool.addResolveTextPageTask(pageName, viewModelCallback);
        mThreadPool.addResolveMediaPageTask(pageName, this);
    }

    @Override
    public void onResolved(List<String> urls) {
        for (String url : urls) {
            //todo 使用Glide

//            mExecutor.setServerName(url);
//            mExecutor.initRequester();
//            mThreadPool.addGetMediaInfoTask();
            Bundle bundle = new Bundle();
            bundle.putString(SucculentDailyViewModel.IMAGE, url);
            mViewModelCallback.onSuccessed(bundle);
        }
    }
}
