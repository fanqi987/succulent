package com.fanqi.succulent.network;

import com.fanqi.succulent.bean.Family;
import com.fanqi.succulent.bean.Genera;
import com.fanqi.succulent.bean.Succulent;
import com.fanqi.succulent.presenter.listener.InitializeDataListener;

public class FirstEnterRequester extends Requester {


    public class Name {
        public static final String SUCCULENTS = "getSucculents";
        public static final String FAMILIES = "getFamilies";
        public static final String GENERAS = "getGeneras";
    }


    public void doFirstInfoRequest(InitializeDataListener dataListener) {
        //请求3项内容，植物数据，科数据，属数据，必须全部请求成功
        mExecutor.setHerokuServer();
        mExecutor.initRequester(180);
        mCallback.setInitializeDataListener(dataListener);

//        mCallback.setValue(new Succulent[0]);
        mThreadPool.addFirstEnterTasks(Name.SUCCULENTS);
//        mCallback.setValue(new Family[0]);
        mThreadPool.addFirstEnterTasks(Name.FAMILIES);
//        mCallback.setValue(new Genera[0]);
        mThreadPool.addFirstEnterTasks(Name.GENERAS);
    }
}
