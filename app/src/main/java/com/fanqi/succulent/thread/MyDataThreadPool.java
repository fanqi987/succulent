package com.fanqi.succulent.thread;

import android.os.Bundle;

import com.fanqi.succulent.activity.adapter.SucculentListAdapter;
import com.fanqi.succulent.network.RequestInterface;
import com.fanqi.succulent.network.RetrofitExecutor;
import com.fanqi.succulent.network.callback.ImageUrlCallback;
import com.fanqi.succulent.network.page.PageResolver;
import com.fanqi.succulent.network.page.PagesBaseDataResolver;
import com.fanqi.succulent.util.NetworkUtil;
import com.fanqi.succulent.util.constant.Constant;
import com.fanqi.succulent.viewmodel.listener.ViewModelCallback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyDataThreadPool {


    private ExecutorService mThreadPool;
    private RetrofitExecutor mRequester;

    private Runnable mRunnable;

    public MyDataThreadPool(RetrofitExecutor requester) {
        mThreadPool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors());
        mRequester = requester;
    }

    public MyDataThreadPool() {
    }

    public void addTask(Runnable runable) {
        mThreadPool.submit(runable);
    }

    //第1次进入时的网络请求，参数是将被调用的网络请求方法名
    public void addFirstEnterTasks(final String requestName) {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                mRequester.request(requestName);
            }
        };
        addTask(mRunnable);
    }

    //爬虫时进入时的网络请求，参数是将被调用的，网络请求方法名，网页后缀

    /**
     * 增加页面爬虫的任务
     */
    public void addPullPageTasks(final String requestName, final String pageName) {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                mRequester.request(requestName, pageName);
            }
        };
        addTask(mRunnable);
    }

    /**
     * 增加页面解析的任务
     */
    public void addPageResolveTask(final PagesBaseDataResolver pagesBaseDataResolver, final String response) {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                pagesBaseDataResolver.resolve(response);
            }
        };
        addTask(mRunnable);
    }

    /**
     * 增加解析的数据保存的任务
     *
     * @param pagesBaseDataResolver
     */
    public void addSaveResolved(final PagesBaseDataResolver pagesBaseDataResolver) {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                pagesBaseDataResolver.saveToDB();
            }
        };
        addTask(mRunnable);
    }

    /**
     * 增加把完整数据上传至服务器的任务
     *
     * @param pagesBaseDataResolver
     * @param networkUtil
     */
    public void addPostToServerTask(final PagesBaseDataResolver pagesBaseDataResolver, final NetworkUtil networkUtil) {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                networkUtil.postFullDataToServer(pagesBaseDataResolver, MyDataThreadPool.this);
            }
        };
        addTask(mRunnable);
    }

    /**
     * 增加post请求任务
     */
    public void addPostItemTask(final String family, final Map<String, Object> postMap) {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                mRequester.request(family, postMap);
            }
        };
        addTask(mRunnable);
    }

    public ExecutorService getThreadPool() {
        return mThreadPool;
    }


    public void addResolveMediaPageTask(final String pageName, final ImageUrlCallback callback) {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                Document document = Jsoup.parse(RequestInterface.baseUrlBaiduPic +
                        pageName + "/0");
                List<String> urls = PageResolver.resolveImageUrls(document);
                callback.onResolvedImageUrls(urls);
            }
        };
        addTask(mRunnable);
    }

    public void addResolveSingleImagePageTask(final String pageName,
                                              final ImageUrlCallback callback,
                                              final Serializable holder) {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                Document document = Jsoup.parse(RequestInterface.baseUrlBaiduPic +
                        pageName + "/0");
                List<String> urls = PageResolver.resolveImageUrl(document);
                callback.onResolvedSingleImageUrl(urls, holder);
            }
        };
        addTask(mRunnable);
    }

    public void addResolveTextPageTask(final String pageName, final ViewModelCallback viewModelCallback) {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                Document document = Jsoup.parse(RequestInterface.baseUrlBaidu +
                        pageName);
                Bundle bundle = new Bundle();
                String summary = PageResolver.resolveItemSummary(document);
                bundle.putString(Constant.ViewModel.SUMMARY, summary);
                viewModelCallback.onSuccessed(bundle);
            }
        };
        addTask(mRunnable);
    }

    public void addGetMediaInfoTask() {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                mRequester.request();
            }
        };
        addTask(mRunnable);
    }


}
