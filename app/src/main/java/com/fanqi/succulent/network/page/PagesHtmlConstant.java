package com.fanqi.succulent.network.page;

public class PagesHtmlConstant {

    public static final String NAME_CHOOSER =
            ".lemmaWgt-lemmaTitle-title h1";
    public static final String FAMILY_CHOOSER =
            ".basicInfo-item.name:matches(^科$) + dd";
    public static final String GENUS_CHOOSER =
            ".basicInfo-item.name:matches(^属$) + dd";

    public static final String INFOS_TITLE_CHOOSER = "";
    public static final String INFOS_CONTENT_CHOOSER = "";

    public static final String INFOS_SUMMARY_CHOOSER =
            ".lemma-summary > .para";


    //需要调用专用的图片地址
    public static final String IMAGE_DATA_CHOOSER = ".pic-list a";
    public static final String IMAGE_DATA_ATTR = "data-sign";
    public static final String IMAGE_PAGE_SUFFIX = "/0/#";

//    public static final String IMAGE_CONTACT_STRING = "/0/#aid=0&pic=";
//    public static final String IMAGE_ID_ATTR = "imgPicture";
//    public static final String IMAGE_URL_SPLITER = "/resize,";

}
