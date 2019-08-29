package com.alipay.mobile.antui.screenadpt;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.alipay.mobile.antui.utils.AuiLogger;

public class AUScreenAdaptTool {
    public static final String PREFIX_ID = "@";
    private static final String SUFFIX_DIP = "dip";
    private static final String SUFFIX_DP = "dp";
    private static final String SUFFIX_PX = "px";
    private static final String SUFFIX_SP = "sp";
    private static final String TAG = "AUScreenAdaptTool";
    public static final int WIDTH_BASE = 360;
    public static int widthBase = 360;

    public static int getApFromAttrsStr(Context context, String attrValue) {
        if (TextUtils.isEmpty(attrValue)) {
            return -1;
        }
        if (attrValue.startsWith(PREFIX_ID)) {
            return getApFromDp(context, (float) getDpFromDimension(context, attrValue));
        }
        return getApFromString(context, attrValue);
    }

    public static int getApFromString(Context context, String attrValue) {
        return getApFromString(context, attrValue, 0);
    }

    public static int getApFromDimen(Context context, int resourceId) {
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    public static int getApFromPx(Context context, float px) {
        return (int) (((double) px) + 0.5d);
    }

    public static int getApFromDp(Context context, float dp) {
        return (int) (((double) (getDensity(context) * dp)) + 0.5d);
    }

    public static float changePxToDp(Context context, float px) {
        return px / getDensity(context);
    }

    public static int getApFromString(Context context, String attrValue, int defaultValue) {
        try {
            String attrValueStr = attrValue.toLowerCase();
            if (TextUtils.isEmpty(attrValueStr)) {
                AuiLogger.debug(TAG, "attrValue为空字符串");
                return defaultValue;
            }
            float scale = getDensity(context);
            if (attrValueStr.contains("dp")) {
                return (int) (((double) (Float.valueOf(Float.parseFloat(attrValueStr.replace("dp", ""))).floatValue() * scale)) + 0.5d);
            }
            if (attrValueStr.contains("dip")) {
                return (int) (((double) (Float.valueOf(Float.parseFloat(attrValueStr.replace("dip", ""))).floatValue() * scale)) + 0.5d);
            }
            if (attrValueStr.contains("sp")) {
                return (int) (((double) (((Float.valueOf(Float.parseFloat(attrValueStr.replace("sp", ""))).floatValue() * getScaledDensity(context)) / getDensity(context)) * scale)) + 0.5d);
            }
            if (attrValueStr.contains("px")) {
                return (int) Float.parseFloat(attrValueStr.replace("px", ""));
            }
            return defaultValue;
        } catch (NumberFormatException e) {
            AuiLogger.debug(TAG, "attrValue数字解析异常: e = " + e);
            return defaultValue;
        }
    }

    public static float getAPDensity(Context context) {
        return ((float) getScreenWidth(context)) / ((float) widthBase);
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        if (displayMetrics.widthPixels > displayMetrics.heightPixels) {
            return displayMetrics.heightPixels;
        }
        return displayMetrics.widthPixels;
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static float getScaledDensity(Context context) {
        return context.getResources().getDisplayMetrics().scaledDensity;
    }

    public static int getDpFromDimension(Context context, String resourceId) {
        return (int) (((double) (getPxFromResourceId(context, resourceId) / getDensity(context))) + 0.5d);
    }

    public static float getPxFromResourceId(Context context, String resourceId) {
        return context.getResources().getDimension(getIdStr(resourceId));
    }

    public static int getIdStr(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            if (str.startsWith(PREFIX_ID)) {
                return Integer.parseInt(str.substring(1, str.length()));
            }
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            AuiLogger.error(TAG, "资源ID错误:" + e.toString());
            return 0;
        }
    }
}
