package com.alipay.android.phone.inside.log.util.storage;

import android.content.SharedPreferences;
import com.alipay.android.phone.inside.log.biz.ContextManager;
import java.util.HashMap;
import java.util.Map;

public class PrefUtils {
    private static Map<String, SharedPreferences> a;

    private static SharedPreferences a(String str) {
        if (a == null) {
            a = new HashMap();
        }
        if (a.containsKey(str)) {
            return a.get(str);
        }
        if (ContextManager.a().getContext() == null) {
            return null;
        }
        SharedPreferences sharedPreferences = ContextManager.a().getContext().getSharedPreferences(str, 0);
        a.put(str, sharedPreferences);
        return sharedPreferences;
    }

    public static boolean a(String str, String str2, String str3) {
        SharedPreferences a2 = a(str);
        if (a2 == null) {
            return false;
        }
        a2.edit().putString(str2, str3).commit();
        return true;
    }

    public static String b(String str, String str2, String str3) {
        SharedPreferences a2 = a(str);
        if (a2 != null) {
            return a2.getString(str2, str3);
        }
        return null;
    }

    public static int a(String str, String str2, int i) {
        SharedPreferences a2 = a(str);
        return a2 != null ? a2.getInt(str2, i) : i;
    }

    public static boolean b(String str, String str2, int i) {
        SharedPreferences a2 = a(str);
        if (a2 == null) {
            return false;
        }
        a2.edit().putInt(str2, i).commit();
        return true;
    }

    public static boolean a(String str, String str2) {
        SharedPreferences a2 = a(str);
        if (a2 != null) {
            return a2.edit().remove(str2).commit();
        }
        return false;
    }
}
