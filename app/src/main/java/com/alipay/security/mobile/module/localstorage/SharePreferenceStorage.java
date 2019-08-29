package com.alipay.security.mobile.module.localstorage;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import java.util.Map;

public class SharePreferenceStorage {
    public static void writeDataToSharePreference(Context context, String str, Map<String, String> map) {
        if (map != null) {
            try {
                Editor edit = context.getSharedPreferences(str, 0).edit();
                if (edit != null) {
                    for (String next : map.keySet()) {
                        edit.putString(next, map.get(next));
                    }
                    edit.commit();
                }
            } catch (Exception unused) {
            }
        }
    }

    public static void writeDataToSharePreference(Context context, String str, String str2, String str3) {
        if (str3 != null) {
            try {
                Editor edit = context.getSharedPreferences(str, 0).edit();
                if (edit != null) {
                    edit.putString(str2, str3);
                    edit.commit();
                }
            } catch (Exception unused) {
            }
        }
    }

    public static String getDataFromSharePreference(Context context, String str, String str2, String str3) {
        try {
            return context.getSharedPreferences(str, 0).getString(str2, str3);
        } catch (Exception unused) {
            r2 = "";
            return "";
        }
    }
}
