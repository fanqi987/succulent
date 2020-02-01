package com.fanqi.succulent.util.constant;

public class Constant {

    public static final String baseUrlHeroku = "https://android-server-fanqi.herokuapp.com/";
    public static final String baseUrlBaidu = " https://baike.baidu.com/item/";
    public static final String baseUrlBaiduPic = "https://baike.baidu.com/pic/";
    public static final String baseUrlBaiduDataImg = "https://bkimg.cdn.bcebos.com/pic/";


    public static final String FIRST_ENTER = "first_enter";

    public static final String DAILY_ITEM_NUMBER = "daily_item_number";
    public static final String DAILY_ITEM_TIME = "daily_item_time";

    public static final int DAY_MILLISECONDS = 24 * 60 * 60 * 1000;

    //统计后的结果，包括科属多包括一个"其它科和其它属"
    public static final int FAMILY_COUNT = 7;
    public static final int GENERAS_COUNT = 12;
    public static final int SUCCULENT_COUNT = 148;

    public class ViewModel {
        public static final String SUCCULENT = "succulent";
        public static final String IMAGE = "image";
        public static final String TEXTS = "texts";
        public static final String INFO_TITLE = "info_title";
        public static final String INFO_CONTENT = "info_content";
        public static final String VIEW_HOLDER = "view_holder";
    }
}
