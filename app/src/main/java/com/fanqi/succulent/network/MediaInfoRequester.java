package com.fanqi.succulent.network;

import android.os.Bundle;

import com.fanqi.succulent.activity.adapter.SucculentListAdapter;
import com.fanqi.succulent.network.callback.ImageUrlCallback;
import com.fanqi.succulent.util.constant.Constant;
import com.fanqi.succulent.viewmodel.listener.ViewModelCallback;

import java.io.Serializable;
import java.util.List;

public class MediaInfoRequester extends Requester
        implements ImageUrlCallback {


    private ViewModelCallback mViewModelCallback;

    public void doGetMediaInfo(ViewModelCallback viewModelCallback, String pageName) {
        mViewModelCallback = viewModelCallback;
        mExecutor.setBaiduImageServer();
        mExecutor.initRequester();
        mThreadPool.addResolveTextPageTask(pageName, viewModelCallback);
        mThreadPool.addResolveMediaPageTask(pageName, this);
    }

    public void doGetSingleImageUrl(ViewModelCallback viewModelCallback, String pageName,
                                    Serializable holder) {
        mViewModelCallback = viewModelCallback;
        mExecutor.setBaiduImageServer();
        mExecutor.initRequester();
        mThreadPool.addResolveSingleImagePageTask(pageName, this, holder);
    }

    @Override
    public void onResolvedImageUrls(List<String> urls) {
        for (String url : urls) {
            //todo 使用Glide
//            mExecutor.setServerName(url);
//            mExecutor.initRequester();
//            mThreadPool.addGetMediaInfoTask();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.ViewModel.IMAGE, url);
            mViewModelCallback.onSuccessed(bundle);
        }
    }

    @Override
    public void onResolvedSingleImageUrl(List<String> urls, Object viewHolder) {
        for (String url : urls) {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.ViewModel.IMAGE, url);
            bundle.putSerializable(Constant.ViewModel.VIEW_HOLDER, (Serializable) viewHolder);
            mViewModelCallback.onSuccessed(bundle);
        }
    }
}
