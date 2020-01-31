package com.fanqi.succulent.util.changer;

import com.fanqi.succulent.bean.SucculentFull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FamilySimiliarNameChanger {

    public static final String OTHER_FAMILY = "其它科";

    public static final ArrayList<List<String>> FAMILIES_NAMES =
            new ArrayList<>();
    private static final String[] jingTianKe = new String[]
            {"景天科"};
    private static final String[] fanXingKe = new String[]
            {"番杏科"};
    private static final String[] baiHeKe = new String[]
            {"百合科"};
    private static final String[] riGuanglanKe = new String[]
            {"日光兰科", "阿福花科", "独尾草科"};
    private static final String[] juKe = new String[]
            {"菊科"};
    private static final String[] maChiXianKe = new String[]
            {"马齿苋科"};

    static {
        FAMILIES_NAMES.add(Arrays.asList(jingTianKe));
        FAMILIES_NAMES.add(Arrays.asList(fanXingKe));
        FAMILIES_NAMES.add(Arrays.asList(baiHeKe));
        FAMILIES_NAMES.add(Arrays.asList(riGuanglanKe));
        FAMILIES_NAMES.add(Arrays.asList(jingTianKe));
        FAMILIES_NAMES.add(Arrays.asList(juKe));
        FAMILIES_NAMES.add(Arrays.asList(maChiXianKe));
    }

    public static String setGetNormalFamilyName(SucculentFull succulentFull) {
        //如果传过来的数据非空，就开始操作
        if (succulentFull != null) {
            //循环科列表
            for (List<String> family : FAMILIES_NAMES) {
                //找到科中包含的名字，或名字包含科
                if (family.indexOf(succulentFull.getFamilyName()) != -1) {
                    //设置科的标准名字，再返回名字
                    succulentFull.setFamilyName(family.get(0));
                    return family.get(0);
                } else {
                    // 或名字包含科,循环所有科的别名，若包含，那就设为此名
                    for (String familyName : family) {
                        if (succulentFull.getFamilyName().contains(familyName)) {
                            //设置科的标准名字，再返回名字
                            succulentFull.setFamilyName(family.get(0));
                            return family.get(0);
                        }
                    }
                }
            }
            //因为科要分类，保存为其它科后，分类标题直接取这个，显示详细时单独处理
            succulentFull.setFamilyName(OTHER_FAMILY);
            return OTHER_FAMILY;
        }
        return null;
    }
}
