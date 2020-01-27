package com.fanqi.succulent.thread;

import com.fanqi.succulent.network.RetrofitExecutor;
import com.fanqi.succulent.network.page.PagesResolver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyDataThreadPool {


    private ExecutorService mThreadPool;
    private RetrofitExecutor mRequester;
    private Object[] mTasksParameter;

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
    public void addFirstEnterTasks(Object[] tasksParameter) {
        mTasksParameter = tasksParameter;
        mRunnable = new Runnable() {
            @Override
            public void run() {
                mRequester.requestGets((String) mTasksParameter[0]);
            }
        };
        addTask(mRunnable);
    }

    //爬虫时进入时的网络请求，参数是将被调用的，网络请求方法名，网页后缀
    public void addPullPageTasks(final Object[] tasksParameter) {
        mTasksParameter = tasksParameter;
         mRunnable = new Runnable() {
            @Override
            public void run() {
                mRequester.requestGets((String) mTasksParameter[0], mTasksParameter[1]);
            }
        };
        addTask(mRunnable);
    }

    public ExecutorService getThreadPool() {
        return mThreadPool;
    }


    public void addPageResolveTask(final PagesResolver pagesResolver, final String response) {
        mRunnable=new Runnable() {
            @Override
            public void run() {
                pagesResolver.resolve(response);
            }
        };
        addTask(mRunnable);
    }
}
