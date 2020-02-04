package com.fanqi.succulent.network;

import com.fanqi.succulent.bean.Family;
import com.fanqi.succulent.bean.Genera;
import com.fanqi.succulent.bean.Succulent;
import com.fanqi.succulent.network.page.PagesBaseDataResolver;
import com.fanqi.succulent.presenter.listener.InitializePostDataListener;
import com.fanqi.succulent.thread.MyDataThreadPool;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SucculentPostRequester extends Requester {

    public class MethodName {
        public static final String FAMILY = "postFamily";
        public static final String GENERA = "postGenera";
        public static final String SUCCULENT = "postSucculent";
    }

    /**
     * 服务器json要求的对象名
     */
    public class Name {
        public static final String FAMILY = "family";
        public static final String GENERA = "genera";
        public static final String SUCCULENT = "succulent";
    }

    public static final String NAME = "name";
    public static final String PAGE_NAME = "page_name";
    public static final String POST_ID = "post_id";
    public static final String FAMILY_ID = "family_id";
    public static final String GENERA_ID = "genera_id";
    public static final String LIGHT = "light";
    public static final String WATER = "water";


    private PagesBaseDataResolver mPagesBaseDataResolver;

    public SucculentPostRequester() {

    }

    public void doPostDataToServer(InitializePostDataListener postDataListener,
                                   PagesBaseDataResolver pagesBaseDataResolver) {
        this.mPagesBaseDataResolver = pagesBaseDataResolver;
        mCallback.setInitializePostDataListener(postDataListener);
        mExecutor.setHerokuServer();
        mExecutor.initRequester(60);

        List<Family> familyList = mPagesBaseDataResolver.getFamilies();
        List<Genera> generaList = mPagesBaseDataResolver.getGeneras();
        List<Succulent> succulentList = mPagesBaseDataResolver.getSucculents();
        JsonObject postJsonObject;
        JsonObject beanJsonObject;
        //拼装成，实体类对象，包含，实体内容对象
        for (Family family : familyList) {
            postJsonObject = new JsonObject();
            beanJsonObject = new JsonObject();
            beanJsonObject.addProperty(NAME, family.getName());
            beanJsonObject.addProperty(POST_ID, family.getPost_id());
            postJsonObject.add(Name.FAMILY, beanJsonObject);
            mThreadPool.addPostItemTask(MethodName.FAMILY, postJsonObject);
        }
        for (Genera genera : generaList) {
            postJsonObject = new JsonObject();
            beanJsonObject = new JsonObject();
            beanJsonObject.addProperty(NAME, genera.getName());
            beanJsonObject.addProperty(POST_ID, genera.getPost_id());
            beanJsonObject.addProperty(FAMILY_ID, genera.getFamily_id());
            postJsonObject.add(Name.GENERA, beanJsonObject);
            mThreadPool.addPostItemTask(MethodName.GENERA, postJsonObject);
        }
        for (Succulent succulent : succulentList) {
            postJsonObject = new JsonObject();
            beanJsonObject = new JsonObject();
            beanJsonObject.addProperty(NAME, succulent.getName());
            beanJsonObject.addProperty(PAGE_NAME, succulent.getPage_name());
            beanJsonObject.addProperty(FAMILY_ID, succulent.getFamily_id());
            beanJsonObject.addProperty(GENERA_ID, succulent.getGenera_id());
            beanJsonObject.addProperty(LIGHT, succulent.getLight());
            beanJsonObject.addProperty(WATER, succulent.getWater());
            postJsonObject.add(Name.SUCCULENT, beanJsonObject);
            mThreadPool.addPostItemTask(MethodName.SUCCULENT, postJsonObject);
        }
    }
}
