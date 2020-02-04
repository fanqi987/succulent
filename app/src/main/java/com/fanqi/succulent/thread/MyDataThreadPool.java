package com.fanqi.succulent.thread;

import android.os.Bundle;

import com.fanqi.succulent.bean.SucculentFull;
import com.fanqi.succulent.network.RetrofitExecutor;
import com.fanqi.succulent.network.callback.ImageUrlCallback;
import com.fanqi.succulent.network.page.PageResolver;
import com.fanqi.succulent.network.page.PagesBaseDataResolver;
import com.fanqi.succulent.network.page.PagesHtmlConstant;
import com.fanqi.succulent.util.NetworkUtil;
import com.fanqi.succulent.util.constant.Constant;
import com.fanqi.succulent.viewmodel.listener.ViewModelCallback;
import com.google.gson.JsonObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyDataThreadPool {

    private static ExecutorService mThreadPool;
    private RetrofitExecutor mExecutor;

    private Runnable mRunnable;

//    private MyDataThreadPool() {
////        mThreadPool = MyDataThreadPoolCreator.sExecutorService;
//    }

    public MyDataThreadPool(RetrofitExecutor executor) {
        if (mThreadPool == null) {
            mThreadPool = Executors.newFixedThreadPool(1);
        }
        mExecutor = executor;
    }

    public MyDataThreadPool(RetrofitExecutor executor, int processorCount) {
        if (mThreadPool == null) {
            mThreadPool = Executors.newFixedThreadPool(processorCount);
        }
        mExecutor = executor;
    }

    public void addTask(Runnable runable) {
        mThreadPool.submit(runable);
    }

    //第1次进入时的网络请求，参数是将被调用的网络请求方法名
    public void addFirstEnterTasks(final String requestName) {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                mExecutor.request(requestName);
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
                mExecutor.request(requestName, pageName);
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
                networkUtil.postFullDataToServer(pagesBaseDataResolver);
            }
        };
        addTask(mRunnable);
    }

    /**
     * 增加post请求任务
     */
    public void addPostItemTask(final String family, final JsonObject requestBody) {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                mExecutor.request(family, requestBody);
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
                Document document = null;
                try {
                    document = Jsoup.connect(Constant.baseUrlBaiduPic +
                            pageName + PagesHtmlConstant.IMAGE_PAGE_SUFFIX).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                List<String> urls = PageResolver.resolveImageUrls(document);
                callback.onResolvedImageUrls(urls);
            }
        };
        addTask(mRunnable);
    }

    public void addResolveSingleImagePageTask(final String pageName,
                                              final ImageUrlCallback callback,
                                              final Serializable holder, int position) {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                Document document = null;
                try {
                    document = Jsoup.connect(Constant.baseUrlBaiduPic +
                            pageName + PagesHtmlConstant.IMAGE_PAGE_SUFFIX).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                List<String> urls = PageResolver.resolveImageUrl(document);
                callback.onResolvedSingleImageUrl(urls, holder,position);
            }
        };
        addTask(mRunnable);
    }

    public void addResolvePageTextInfoTask(final String pageName, final ViewModelCallback viewModelCallback) {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                Document document = null;
                try {
                    document = Jsoup.connect(Constant.baseUrlBaidu +
                            pageName).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bundle bundle = new Bundle();
                SucculentFull succulentFull = new SucculentFull();
                List<String[]> infos = new ArrayList<>();
                infos.add(new String[]{"", PageResolver.resolveItemSummary(document)});
                succulentFull.setInfos(infos);
                succulentFull.setFamilyName(PageResolver.resolveItemFamily(document));
                succulentFull.setGeneraName(PageResolver.resolveItemGenera(document));
                bundle.putSerializable(Constant.ViewModel.SUCCULENT_FULL, succulentFull);
                viewModelCallback.onSuccessed(bundle);
            }
        };
        addTask(mRunnable);
    }

    public void addGetMediaInfoTask() {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                mExecutor.request();
            }
        };
        addTask(mRunnable);
    }


}
