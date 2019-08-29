package com.alipay.mobile.security.bio.utils;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;

public class DisplayUtil {
    public static Point getScreenMetrics(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        BioLog.i("Screen---Width = " + i + " Height = " + i2 + " densityDpi = " + displayMetrics.densityDpi);
        return new Point(i, i2);
    }

    public static float getScreenRate(Context context) {
        Point screenMetrics = getScreenMetrics(context);
        return ((float) screenMetrics.y) / ((float) screenMetrics.x);
    }

    public static int dip2px(Context context, float f) {
        return (int) ((context.getResources().getDisplayMetrics().density * f) + 0.5f);
    }

    public static int px2dip(Context context, float f) {
        return (int) ((f / context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
