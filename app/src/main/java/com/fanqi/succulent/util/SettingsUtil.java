package com.fanqi.succulent.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class SettingsUtil {

    public static DisplayMetrics getDisplayMetrics(Activity activity) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        int heightPixels = outMetrics.heightPixels;
        return outMetrics;
    }
}
