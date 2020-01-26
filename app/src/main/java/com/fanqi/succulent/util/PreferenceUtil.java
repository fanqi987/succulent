package com.fanqi.succulent.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.fanqi.succulent.util.constant.Constant;

public class PreferenceUtil {
    @NonNull
    private static Activity sActivity;

    public static void init(@NonNull Activity activity) {
        sActivity = activity;
    }

    public static boolean getFirstEnterFlag() {
        SharedPreferences preferences = sActivity.getPreferences(Context.MODE_PRIVATE);
        return preferences.getBoolean(Constant.FIRST_ENTER, true);
    }

    public static void setFirstEnterFlag(boolean flag) {
        SharedPreferences preferences = sActivity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Constant.FIRST_ENTER, flag);
        editor.commit();
    }

}
