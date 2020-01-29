package com.fanqi.succulent.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.fanqi.succulent.util.constant.Constant;

import java.util.Random;

public class PreferenceUtil {
    @NonNull
    private static Activity sActivity;
    private static SharedPreferences sPreferences;
    private static SharedPreferences.Editor sEditor;

    public static void init(@NonNull Activity activity) {
        sActivity = activity;
        sPreferences = sActivity.getPreferences(Context.MODE_PRIVATE);
    }

    public static boolean getFirstEnterFlag() {
        return sPreferences.getBoolean(Constant.FIRST_ENTER, true);
    }

    public static void setFirstEnterFlag(boolean flag) {
        sEditor = sPreferences.edit();
        sEditor.putBoolean(Constant.FIRST_ENTER, flag);
        sEditor.commit();
    }

    public static int getDailyNumber() {
        Long timeNow = System.currentTimeMillis();
        int dailyNumber;
        if (timeNow - sPreferences.getLong(Constant.DAILY_ITEM_TIME, 0)
                > Constant.DAY_MILLISECONDS) {
            dailyNumber = new Random().nextInt(Constant.SUCCULENT_COUNT);
            sEditor = sPreferences.edit();
            sEditor.putInt(Constant.DAILY_ITEM_NUMBER, dailyNumber);
            sEditor.putLong(Constant.DAILY_ITEM_TIME, timeNow);
            sEditor.commit();
        } else {
            dailyNumber = sPreferences.getInt(Constant.DAILY_ITEM_NUMBER, 0);
        }
        return dailyNumber;
    }
}
