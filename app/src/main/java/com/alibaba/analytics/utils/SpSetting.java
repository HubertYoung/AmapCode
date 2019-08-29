package com.alibaba.analytics.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SpSetting {
    public static String get(Context context, String str) {
        if (context == null) {
            return null;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("ut_setting", 4);
        if (sharedPreferences != null) {
            return sharedPreferences.getString(str, null);
        }
        return null;
    }

    public static void put(Context context, String str, String str2) {
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("ut_setting", 4);
            if (sharedPreferences != null) {
                Editor edit = sharedPreferences.edit();
                if (edit != null) {
                    edit.putString(str, str2);
                    edit.apply();
                }
            }
        }
    }
}
