package com.alipay.mobile.antui.utils;

import android.content.Context;
import com.alipay.mobile.antui.BuildConfig;

public class ResourceUtils {
    public static int getResourceId(Context context, String name, String type) {
        int resId = -1;
        try {
            return context.getResources().getIdentifier(name, type, getPackageName());
        } catch (Exception e) {
            AuiLogger.error("getResourceId", e.toString());
            return resId;
        }
    }

    private static String getPackageName() {
        return BuildConfig.APPLICATION_ID;
    }
}
