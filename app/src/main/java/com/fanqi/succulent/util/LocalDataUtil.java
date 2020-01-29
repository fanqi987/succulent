package com.fanqi.succulent.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;

import com.fanqi.succulent.R;
import com.fanqi.succulent.bean.Bean;
import com.fanqi.succulent.bean.Family;
import com.fanqi.succulent.bean.Genera;
import com.fanqi.succulent.bean.Succulent;
import com.fanqi.succulent.util.constant.Constant;

import org.litepal.LitePal;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class LocalDataUtil {

    private static List<Succulent> sSucculents = null;
    private static List<Family> sFamilies = null;
    private static List<Genera> sGeneras = null;

    private LocalDataUtil() {
        throw new UnsupportedOperationException("Can not be instantiated");
    }

    public static Succulent[] getAssetsPlantInfo(Context context) {
        AssetManager assetManager = context.getAssets();
        String fileName =
                context.getResources().getString(R.string.assets_path) +
                        context.getResources().getString(R.string.assets_file_name_plant);
        BufferedReader bufferedReader = null;
        ArrayList<Succulent> beanArrayList = new ArrayList<>();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open(fileName);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String tmp;
            while ((tmp = bufferedReader.readLine()) != null) {
                Succulent succulent = new Succulent();
                //网页名字 光 水
                String[] item = tmp.split(" ");
                succulent.setPage_name(item[0]);
                succulent.setName(item[0].split("/")[0]);
                succulent.setLight(Integer.valueOf(item[1]));
                succulent.setWater(Integer.valueOf(item[2]));
                beanArrayList.add(succulent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ClosableCloser.close(inputStream);
            ClosableCloser.close(bufferedReader);
        }
        return (Succulent[]) beanArrayList.toArray();
    }

    public static List<Succulent> getSucculents() {
        if (sSucculents == null) {
            sSucculents = LitePal.findAll(Succulent.class);
        }
        return sSucculents;
    }


    public static Family findFamily(int post_id) {
        return LitePal.where("post_id", String.valueOf(post_id)).find(Family.class).get(0);
    }

    public static List<Family> getFamilies() {
        if (sFamilies == null) {
            sFamilies = LitePal.findAll(Family.class);
        }
        return sFamilies;
    }

    public static Genera findGenera(int post_id) {
        return LitePal.where("post_id", String.valueOf(post_id)).find(Genera.class).get(0);
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
