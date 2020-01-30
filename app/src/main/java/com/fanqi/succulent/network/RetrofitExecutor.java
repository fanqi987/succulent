package com.fanqi.succulent.network;

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
                method = request.getClass().getMethod(requestName, args.getClass());
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
        mServerName = RequestInterface.baseUrlHeroku;
    }

    public void setBaiduServer() {
        mServerName = RequestInterface.baseUrlBaidu;
    }


    public void initRequester() {
        retrofit = mRetrofitInitializer.buildDefault(mServerName);
        request = retrofit.create(RequestInterface.class);
    }

    public void setBaiduImageServer() {
        mServerName = RequestInterface.baseUrlBaiduPic;
    }

    public void setServerName(String serverName) {
        mServerName = serverName;
    }
}
