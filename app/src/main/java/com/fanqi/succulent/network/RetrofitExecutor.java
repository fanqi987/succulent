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
            if (args == null) {
                method = request.getClass().getMethod(requestName);
            } else {
                method = request.getClass().getMethod(requestName, args.getClass());
            }
            observable = (Observable<T>) method.invoke(request, args);
            observable.subscribe(mRetrofitCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void request(String requestName) {
        request(requestName, null);
    }


//    public void setBaseServer(String server) throws ServerNameErrException {
//        if (!RetrofitExecutor.BAIDU_SERVER.equals(server)
//                && !RetrofitExecutor.HEROKU_SERVER.equals(server)) {
//            throw new ServerNameErrException();
//        } else {
//            mServerName = server;
//        }
//    }

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

}
