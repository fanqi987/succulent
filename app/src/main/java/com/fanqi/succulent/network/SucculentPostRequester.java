package com.fanqi.succulent.network;

import com.fanqi.succulent.bean.Family;
import com.fanqi.succulent.bean.Genera;
import com.fanqi.succulent.bean.Succulent;
import com.fanqi.succulent.network.page.PagesBaseDataResolver;
import com.fanqi.succulent.presenter.listener.InitializePostDataListener;
import com.fanqi.succulent.thread.MyDataThreadPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SucculentPostRequester extends  Requester{

    public class Name {
        public static final String FAMILY = "postFamily";
        public static final String GENERA = "postGenera";
        public static final String SUCCULENT = "postSucculent";

    }
    public static final String NAME="name";
    public static final String PAGE_NAME="page_name";
    public static final String POST_ID="post_id";
    public static final String FAMILY_ID="family_id";
    public static final String GENERA_ID="genera_id";
    public static final String LIGHT="light";
    public static final String WATER="water";

    private PagesBaseDataResolver mPagesBaseDataResolver;

    public SucculentPostRequester() {

    }

    public void doPostDataToServer(InitializePostDataListener postDataListener,
                                   PagesBaseDataResolver pagesBaseDataResolver, MyDataThreadPool myDataThreadPool) {
        this.mThreadPool=myDataThreadPool;
        this.mPagesBaseDataResolver = pagesBaseDataResolver;
        mCallback.setThreadPool(mThreadPool);
        mCallback.setInitializePostDataListener(postDataListener);
        mExecutor.setHerokuServer();
        mExecutor.initRequester();
        mExecutor.request(Name.FAMILY);

        List<Family> familyList= pagesBaseDataResolver.getFamilies();
        List<Genera> generaList= pagesBaseDataResolver.getGeneras();
        List<Succulent> succulentList= pagesBaseDataResolver.getSucculents();
        for(Family family:familyList){
            Map<String,Object> postMap=new HashMap<>();
            postMap.put(NAME,family.getName());
            postMap.put(POST_ID,family.getPost_id());
            mThreadPool.addPostItemTask(Name.FAMILY,postMap);
        }
        for(Genera genera:generaList){
            Map<String,Object> postMap=new HashMap<>();
            postMap.put(NAME,genera.getName());
            postMap.put(POST_ID,genera.getPost_id());
            postMap.put(FAMILY_ID,genera.getFamily_id());
            mThreadPool.addPostItemTask(Name.GENERA,postMap);
        }
        for(Succulent succulent:succulentList){
            Map<String,Object> postMap=new HashMap<>();
            postMap.put(NAME,succulent.getName());
            postMap.put(PAGE_NAME,succulent.getPage_name());
            postMap.put(FAMILY_ID,succulent.getFamily_id());
            postMap.put(GENERA_ID,succulent.getGenera_id());
            postMap.put(LIGHT,succulent.getLight());
            postMap.put(WATER,succulent.getWater());
            mThreadPool.addPostItemTask(Name.SUCCULENT,postMap);
        }
    }
}
