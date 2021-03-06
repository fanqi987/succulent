package com.fanqi.succulent.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInitializer {

    private Retrofit.Builder mBuilder;

    private String mServerName;
    private Converter.Factory mConverterFactory;
    private CallAdapter.Factory mCallAdapterFactory;

    public RetrofitInitializer() {
        mBuilder = new Retrofit.Builder();
    }

    public Retrofit build(String serverName) {
        mServerName = serverName;
        return new Retrofit.Builder()
                .baseUrl(mServerName)
                .addConverterFactory(mConverterFactory)
                .addCallAdapterFactory(mCallAdapterFactory)
                .build();
    }

    public void setConverterFactory(Converter.Factory factory) {
        this.mConverterFactory = factory;
    }

    public void setCallAdapterFactory(CallAdapter.Factory factory) {
        this.mCallAdapterFactory = factory;
    }

    public Retrofit buildDefault(String serverName) {
        mServerName = serverName;
        mConverterFactory = GsonConverterFactory.create();
        mCallAdapterFactory = RxJava2CallAdapterFactory.create();
        return new Retrofit.Builder()
                .baseUrl(mServerName)
                .addConverterFactory(mConverterFactory)
                .addCallAdapterFactory(mCallAdapterFactory)
                .build();
    }

    public Retrofit buildWithTimeout(String serverName, int timeoutSeconds) {
        mServerName = serverName;
        mConverterFactory = GsonConverterFactory.create();
        mCallAdapterFactory = RxJava2CallAdapterFactory.create();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(timeoutSeconds, TimeUnit.SECONDS)
                .readTimeout(timeoutSeconds, TimeUnit.SECONDS)
                .writeTimeout(timeoutSeconds, TimeUnit.SECONDS).build();
        return new Retrofit.Builder()
                .baseUrl(mServerName)
                .addConverterFactory(mConverterFactory)
                .addCallAdapterFactory(mCallAdapterFactory)
                .client(client)
                .build();
    }
}
