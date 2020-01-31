package com.fanqi.succulent.bean;

import java.util.List;

public class SucculentFull extends Succulent {

    //图片地址集合
    protected List<String> imageUrl;

    //条目信息集合，标题，内容
    //第1条infos.get(0)是介绍，可以没有标题infos.get(0)[0]
    protected List<String[]> infos;

    //浏览地址
    protected String url;


    //因为在保存植物数据前，还没有初始化科属归类，所以先暂时保存他们的名字。
    protected String familyName;
    protected String GeneraName;

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String[]> getInfos() {
        return infos;
    }

    public void setInfos(List<String[]> infos) {
        this.infos = infos;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGeneraName() {
        return GeneraName;
    }

    public void setGeneraName(String generaName) {
        GeneraName = generaName;
    }

    public Succulent getSucculent() {
        Succulent succulent = new Succulent();
        succulent.setName(this.getName());
        succulent.setPage_name(this.getPage_name());
        succulent.setWater(this.getWater());
        succulent.setLight(this.getLight());
        succulent.setFamily_id(this.getFamily_id());
        succulent.setGenera_id(this.getGenera_id());
        return succulent;
    }
}
