package com.fanqi.succulent.util.changer;

import com.fanqi.succulent.bean.Family;
import com.fanqi.succulent.bean.Succulent;
import com.fanqi.succulent.bean.SucculentFull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenusSimiliarNameChanger {

    public static final String OTHER_GENERA = "其它属";

    public static final ArrayList<List<String>> GENERAS_NAME =
            new ArrayList<>();
    //景天科
    private static final String[] shiLianHuaShu = new String[]
            {"石莲花属", "拟石莲花属",};
    private static final String[] lianHuaZhangShu = new String[]
            {"莲花掌属"};
    private static final String[] qingSuoLongShu = new String[]
            {"青锁龙属"};
    private static final String[] jingTianShu = new String[]
            {"景天属"};
    private static final String[] jiaLanCaiShu = new String[]
            {"伽蓝菜属", "灯笼草属"};
    private static final String[] changShengCaoShu = new String[]
            {"长生草属"};
    private static final String[] yinBoJinShu = new String[]
            {"银波锦属"};
    private static final String[] houYeCaoShu = new String[]
            {"厚叶草属"};
    private static final String[] waSongShu = new String[]
            {"瓦松属"};
    private static final String[] fengCheCaoShu = new String[]
            {"风车草属"};
    //番杏科
    //百合科
    private static final String[] shiErJuanShu = new String[]
            {"十二卷属", "瓦苇属", "蛇尾兰属"};
    //马齿苋科
    //其它科


    static {
        GENERAS_NAME.add(Arrays.asList(shiLianHuaShu));
        GENERAS_NAME.add(Arrays.asList(lianHuaZhangShu));
        GENERAS_NAME.add(Arrays.asList(qingSuoLongShu));
        GENERAS_NAME.add(Arrays.asList(jingTianShu));
        GENERAS_NAME.add(Arrays.asList(jiaLanCaiShu));
        GENERAS_NAME.add(Arrays.asList(changShengCaoShu));
        GENERAS_NAME.add(Arrays.asList(yinBoJinShu));
        GENERAS_NAME.add(Arrays.asList(houYeCaoShu));
        GENERAS_NAME.add(Arrays.asList(waSongShu));
        GENERAS_NAME.add(Arrays.asList(fengCheCaoShu));
        GENERAS_NAME.add(Arrays.asList(shiErJuanShu));
    }

    public static String setGetNormalGeneraName(List<Family> families, SucculentFull succulentFull) {
        if (succulentFull != null) {
            for (List<String> genera : GENERAS_NAME) {
                //找到属中包含的名字，或名字包含属
                if (genera.indexOf(succulentFull.getGeneraName()) != -1) {
                    //设置属的标准名字，再返回名字
                    succulentFull.setGeneraName(genera.get(0));
                    //设置属的科id
                    succulentFull.setFamily_id(families.indexOf(succulentFull.getFamilyName()) + 1);
                    return genera.get(0);
                } else {
                    // 或名字包含属,循环所有属的别名，若包含，那就设为此名
                    for (String generaName : genera) {
                        if (succulentFull.getGeneraName().contains(generaName)) {
                            //设置科的标准名字，再返回名字
                            succulentFull.setGeneraName(genera.get(0));
                            //设置属的科id
                            succulentFull.setFamily_id(families.indexOf(succulentFull.getFamilyName()) + 1);
                            return genera.get(0);
                        }
                    }
                }
            }
            //设置属的科id
            succulentFull.setFamily_id(families.indexOf(succulentFull.getFamilyName()));
            return OTHER_GENERA;
        }
        return null;
    }
}
