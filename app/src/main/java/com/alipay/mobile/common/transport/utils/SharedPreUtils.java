package com.alipay.mobile.common.transport.utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public final class SharedPreUtils {
    public static final boolean putData(Context context, String key, String value) {
        try {
            Editor edit = a(context);
            edit.putString(key, value);
            edit.commit();
            return true;
        } catch (Throwable e) {
            LogCatUtil.error((String) "SharedPreUtils", e);
            return false;
        }
    }

    public static final void putData(Context context, String key, long value) {
        try {
            Editor edit = a(context);
            edit.putLong(key, value);
            edit.commit();
        } catch (Exception e) {
            LogCatUtil.error((String) "SharedPreUtils", (Throwable) e);
        }
    }

    public static void putData(Context context, String key, int value) {
        try {
            Editor edit = a(context);
            edit.putInt(key, value);
            edit.commit();
        } catch (Exception ex) {
            LogCatUtil.error((String) "SharedPreUtils", (Throwable) ex);
        }
    }

    public static void putData(Context context, String key, boolean value) {
        try {
            Editor edit = a(context);
            edit.putBoolean(key, value);
            edit.commit();
        } catch (Exception ex) {
            LogCatUtil.error((String) "SharedPreUtils", (Throwable) ex);
        }
    }

    public static final String getStringData(Context context, String key) {
        try {
            return context.getSharedPreferences("com_transp_sf", 4).getString(key, "");
        } catch (Exception e) {
            LogCatUtil.error((String) "SharedPreUtils", (Throwable) e);
            return "";
        }
    }

    public static final long getLonggData(Context context, String key) {
        long j = -1;
        try {
            return context.getSharedPreferences("com_transp_sf", 4).getLong(key, -1);
        } catch (Exception e) {
            LogCatUtil.error((String) "SharedPreUtils", (Throwable) e);
            return j;
        }
    }

    public static int getIntData(Context context, String key) {
        char c = 65535;
        try {
            return context.getSharedPreferences("com_transp_sf", 4).getInt(key, -1);
        } catch (Exception e) {
            LogCatUtil.error((String) "SharedPreUtils", (Throwable) e);
            return c;
        }
    }

    public static boolean getBooleanData(Context context, String key) {
        boolean z = false;
        try {
            return context.getSharedPreferences("com_transp_sf", 4).getBoolean(key, false);
        } catch (Exception e) {
            LogCatUtil.error((String) "SharedPreUtils", (Throwable) e);
            return z;
        }
    }

    public static final void removeData(Context context, String key) {
        try {
            Editor editor = a(context);
            editor.remove(key);
            editor.commit();
        } catch (Exception e) {
            LogCatUtil.error("SharedPreUtils", "removeData", e);
        }
    }

    private static Editor a(Context context) {
        return context.getSharedPreferences("com_transp_sf", 4).edit();
    }
}
