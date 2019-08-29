package com.autonavi.indoor.util;

import android.content.Context;
import android.os.Build.VERSION;

public abstract class PermissionUtil {
    public static String[] permissions = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"};

    public static boolean hasSelfPermission(Context context, String str) {
        if (VERSION.SDK_INT < 23 || context.checkSelfPermission(str) == 0) {
            return true;
        }
        return false;
    }

    public static boolean hasSelfPermission(Context context, String[] strArr) {
        if (VERSION.SDK_INT >= 23) {
            for (String checkSelfPermission : strArr) {
                if (context.checkSelfPermission(checkSelfPermission) != 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
