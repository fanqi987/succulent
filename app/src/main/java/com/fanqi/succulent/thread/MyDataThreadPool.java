package com.fanqi.succulent.thread;

import com.fanqi.succulent.network.RetrofitExecutor;
import com.fanqi.succulent.network.page.PagesResolver;
import com.fanqi.succulent.util.NetworkUtil;

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
     * @param tasksParameter
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
    public void addPageResolveTask(final PagesResolver pagesResolver, final String response) {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                pagesResolver.resolve(response);
            }
        };
        addTask(mRunnable);
    }

    /**
     * 增加解析的数据保存的任务
     * @param pagesResolver
     */
    public void addSaveResolved(final PagesResolver pagesResolver) {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                pagesResolver.saveToDB();
            }
        };
        addTask(mRunnable);
    }

    /**
     * 增加把完整数据上传至服务器的任务
     * @param pagesResolver
     * @param networkUtil
     */
    public void addPostToServerTask(final PagesResolver pagesResolver, final NetworkUtil networkUtil) {
        mRunnable=new Runnable() {
            @Override
            public void run() {
                networkUtil.postFullDataToServer(pagesResolver,MyDataThreadPool.this);
            }
        };
        addTask(mRunnable);
    }

    /**
     * 增加post请求任务
     */
    public void addPostItemTask(final String family, final Map<String, Object> postMap) {
        mRunnable=new Runnable() {
            @Override
            public void run() {
                mRequester.request(family,postMap);
            }
        };
        addTask(mRunnable);
    }
    public ExecutorService getThreadPool() {
        return mThreadPool;
    }


}
