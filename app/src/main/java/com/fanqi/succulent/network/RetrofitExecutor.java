package com.fanqi.succulent.network;

import com.fanqi.succulent.util.constant.Constant;

import java.lang.reflect.Method;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class RetrofitExecutor<T> implements Executor {


    private String mServerName;

    private RetrofitInitializer mRetrofitInitializer;
    private Retrofit retrofit;
    private RequestInterface request;
    private RetrofitCallback<T> mRetrofitCallback;


    public RetrofitExecutor(RetrofitCallback<T> retrofitCallback) {
        mRetrofitInitializer = new RetrofitInitializer();
        mRetrofitCallback = retrofitCallback;
    }


    public void request(String requestName, Object... args) {
        Method method = null;
        Observable<T> observable = null;
        try {
            if (args.length == 0) {
                method = request.getClass().getMethod(requestName);
                observable = (Observable<T>) method.invoke(request);
            } else {
                Class[] classes = new Class[args.length];
                for (int i = 0; i < args.length; i++) {
                    classes[i] = args[i].getClass();
                }
                method = request.getClass().getMethod(requestName, classes);
                observable = (Observable<T>) method.invoke(request, args);
            }
            observable.subscribe(mRetrofitCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void request(String requestName) {
//        request(requestName, new Object[0]);
//    }

    public void request() {
        Method method = null;
        Observable<T> observable = null;
        try {
            method = request.getClass().getMethod("get");
            observable = (Observable<T>) method.invoke(request);
            observable.subscribe(mRetrofitCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setHerokuServer() {
        mServerName = Constant.baseUrlHeroku;
    }

    public void setBaiduServer() {
        mServerName = Constant.baseUrlBaidu;
    }


    public void initRequester() {
        retrofit = mRetrofitInitializer.buildDefault(mServerName);
        request = retrofit.create(RequestInterface.class);
    }

    public void initRequester(int timeOutSeconds) {
        retrofit = mRetrofitInitializer.buildWithTimeout(mServerName, timeOutSeconds);
        request = retrofit.create(RequestInterface.class);
    }

    public void setBaiduImageServer() {
        mServerName = Constant.baseUrlBaiduPic;
    }

    public void setServerName(String serverName) {
        mServerName = serverName;
    }
}
