package com.fanqi.succulent.network.page;

import android.util.Log;

import com.fanqi.succulent.bean.Bean;
import com.fanqi.succulent.bean.Family;
import com.fanqi.succulent.bean.Genera;
import com.fanqi.succulent.bean.Succulent;
import com.fanqi.succulent.bean.SucculentFull;
import com.fanqi.succulent.util.changer.FamilySimiliarNameChanger;
import com.fanqi.succulent.util.changer.GenusSimiliarNameChanger;
import com.fanqi.succulent.util.constant.Constant;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 解析固定的基本文字数据类
 */
public class PagesBaseDataResolver {


    private List<SucculentFull> mSucculentFulls;
    private List<Succulent> mSucculents;
    private List<Family> mFamilies;
    private List<Genera> mGeneras;

    private List<String> mNames;
    private List<String> mFamilyNames;
    private List<String> mGeneraNames;

    private Elements mElements;

    public PagesBaseDataResolver() {
        mSucculentFulls = new ArrayList<>();
        mSucculents = new ArrayList<>();
        mNames = new ArrayList<>();
        mFamilies = Collections.synchronizedList(new ArrayList<Family>());
        mGeneras = Collections.synchronizedList(new ArrayList<Genera>());
        mFamilyNames = new ArrayList<>();
        mGeneraNames = new ArrayList<>();
    }

    public void initDataList(List<SucculentFull> succulentFulls) {
        mSucculentFulls = succulentFulls;
        for (SucculentFull succulentFull : mSucculentFulls) {
            mNames.add(succulentFull.getName());
        }
    }

    public List<SucculentFull> getSucculentFulls() {
        return mSucculentFulls;
    }

    public List<Family> getFamilies() {
        return mFamilies;
    }

    public List<Genera> getGeneras() {
        return mGeneras;
    }

    public List<Succulent> getSucculents() {
        return mSucculents;
    }

    public void resolve(String response) {
        Document document = Jsoup.parse(response);

        //因为科和属还没有归类，所以先把对应的名字放进去再说
        //所以要为succulentFull类中，设置保存的科属名
        SucculentFull succulentFull;
        //找名字
        mElements = document.select(PagesHtmlConstant.NAME_CHOOSER);
        int itemIndex = 0;
        if (mElements.first() == null) {
            Log.e("response", response);
        }
        itemIndex = mNames.indexOf(mElements.first().text().trim());
        succulentFull = mSucculentFulls.get(itemIndex);
        //找科
        mElements = document.select(PagesHtmlConstant.FAMILY_CHOOSER);
        if (mElements.first() == null) {
            Log.e("名字", succulentFull.getName());
            succulentFull.setFamilyName("其它科");
        } else {
            succulentFull.setFamilyName(mElements.first().text().trim());
        }
        Log.e("family name ", succulentFull.getFamilyName() + "  ");
        //找属
        mElements = document.select(PagesHtmlConstant.GENUS_CHOOSER);
        if (mElements.first() == null) {
            Log.e("名字", succulentFull.getName());
            succulentFull.setGeneraName("其它属");
        } else {
            succulentFull.setGeneraName(mElements.first().text().trim());
        }
        //找摘要信息
        mElements = document.select(PagesHtmlConstant.INFOS_SUMMARY_CHOOSER);
        ArrayList<String[]> infos = new ArrayList<>();
        infos.add(new String[]{"", mElements.first().text()});
        succulentFull.setInfos(infos);
        //找图片data属性


        try {
            document = Jsoup.connect(Constant.baseUrlBaiduPic
                    + succulentFull.getPage_name() + "/0/#").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mElements = document.select(PagesHtmlConstant.IMAGE_DATA_CHOOSER);
        ArrayList<String> imageUrl = new ArrayList<>();
        for (Element element : mElements) {
            imageUrl.add(Constant.baseUrlBaiduDataImg +
                    element.attr(PagesHtmlConstant.IMAGE_DATA_ATTR));
        }
        succulentFull.setImageUrl(imageUrl);


//        mSucculentFulls.add(succulentFull);
        //根据实体类的大小，获取何时解析停止。
        //之后处理初始化科属问题，再全部保存到数据库
        //之后清空所有缓存变量，就可以更新数据到UI页面了
    }

    public void saveToDB() {
        initFamilies();
        save(mFamilies);
        reset(mFamilies, Family.class);
        initGeneras();
        save(mGeneras);
        reset(mGeneras, Genera.class);
        initSucculents();
        for (SucculentFull succulentFull : mSucculentFulls) {
            mSucculents.add(succulentFull.getSucculent());
        }
        save(mSucculents);
        reset(mSucculents, Succulent.class);
    }

    private void initSucculents() {
        for (SucculentFull succulentFull : mSucculentFulls) {
            for (Genera genera : mGeneras) {
                if (succulentFull.getGeneraName().equals(genera.getName())) {
                    succulentFull.setGenera_id(genera.getPost_id());
                    break;
                }
            }
        }
    }

    private void initFamilies() {
        //判断出正确的名字，并保存，再保存到family列表中
        for (SucculentFull succulentFull : mSucculentFulls) {
            String familyName = FamilySimiliarNameChanger.setGetNormalFamilyName(succulentFull);
            if (!mFamilyNames.contains(familyName)) {
                mFamilyNames.add(familyName);
                Family family = new Family();
                family.setName(familyName);
                family.setPost_id(mFamilies.size() + 1);
                mFamilies.add(family);
            }
        }
    }

    private void initGeneras() {
        //判断出正确的名字，并保存，再保存到genera列表中
        for (SucculentFull succulentFull : mSucculentFulls) {
            String generaName = GenusSimiliarNameChanger.
                    setGetNormalGeneraName(mFamilyNames, succulentFull);
            if (!mGeneraNames.contains(generaName)) {
                mGeneraNames.add(generaName);
                Genera genera = new Genera();
                genera.setName(generaName);
                genera.setFamily_id(succulentFull.getFamily_id());
                genera.setPost_id(mGeneras.size() + 1);
                mGeneras.add(genera);
            }
        }
    }

    private void save(List list) {
        List<Bean> beans = (List<Bean>) list;
        for (Bean bean : beans) {
            bean.save();
        }
    }

    private void reset(List list, Class clazz) {
        list = LitePal.findAll(clazz);
    }
}
