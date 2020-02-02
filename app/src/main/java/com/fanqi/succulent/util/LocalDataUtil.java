package com.fanqi.succulent.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.fanqi.succulent.R;
import com.fanqi.succulent.bean.Family;
import com.fanqi.succulent.bean.Genera;
import com.fanqi.succulent.bean.Succulent;
import com.fanqi.succulent.bean.SucculentFull;

import org.litepal.LitePal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LocalDataUtil {

    private static List<Succulent> sSucculents = null;
    private static List<Family> sFamilies = null;
    private static List<Genera> sGeneras = null;

    private LocalDataUtil() {
        throw new UnsupportedOperationException("Can not be instantiated");
    }

    public static SucculentFull[] getAssetsPlantInfo(Context context) {
        AssetManager assetManager = context.getAssets();
        String fileName =
//                context.getResources().getString(R.string.assets_path) +
                context.getResources().getString(R.string.assets_file_name_plant);
        BufferedReader bufferedReader = null;
        List<SucculentFull> beanArrayList = new ArrayList<>();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open(fileName);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String tmp;
            bufferedReader.readLine();
            while ((tmp = bufferedReader.readLine()) != null) {
                SucculentFull succulentFull = new SucculentFull();
                //网页名字 光 水
                String[] item = tmp.split(" ");
                succulentFull.setPage_name(item[0]);
                succulentFull.setName(item[0].split("/")[0]);
                succulentFull.setLight(Integer.valueOf(item[1]));
                succulentFull.setWater(Integer.valueOf(item[2]));
                beanArrayList.add(succulentFull);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ClosableCloser.close(inputStream);
            ClosableCloser.close(bufferedReader);
        }
        return beanArrayList.toArray(new SucculentFull[beanArrayList.size()]);
    }

    public static List<Succulent> getSucculents() {
        if (sSucculents == null) {
            sSucculents = LitePal.findAll(Succulent.class);
        }
        return sSucculents;
    }
    public static List<Succulent> getSucculents(int family_id) {
        if (sSucculents == null) {
            sSucculents = LitePal.findAll(Succulent.class);
        }
        List<Succulent> succulentList=new ArrayList<>();
        for(Succulent succulent:sSucculents){
            if(succulent.getFamily_id()==family_id){
                succulentList.add(succulent);
            }
        }
        return succulentList;
    }

    public static Family findFamily(int post_id) {
        return LitePal.where("post_id = ?", String.valueOf(post_id)).find(Family.class).get(0);
    }

    public static List<Family> getFamilies() {
        if (sFamilies == null) {
            sFamilies = LitePal.findAll(Family.class);
        }
        return sFamilies;
    }

    public static Genera findGenera(int post_id) {
        return LitePal.where("post_id = ?", String.valueOf(post_id)).find(Genera.class).get(0);
    }

    public static List<Genera> getGeneras() {
        if (sGeneras == null) {
            sGeneras = LitePal.findAll(Genera.class);
        }
        return sGeneras;
    }

    public static int getDailyItem() {
        return PreferenceUtil.getDailyNumber();
    }


}
