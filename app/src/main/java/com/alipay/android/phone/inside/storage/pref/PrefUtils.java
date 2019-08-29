package com.alipay.android.phone.inside.storage.pref;

import android.content.SharedPreferences;
import com.alipay.android.phone.inside.storage.StorageInit;
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
        if (StorageInit.a() == null) {
            return null;
        }
        SharedPreferences sharedPreferences = StorageInit.a().getSharedPreferences(str, 0);
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

    public static boolean a(String str, String str2) {
        SharedPreferences a2 = a(str);
        if (a2 != null) {
            return a2.contains(str2);
        }
        return false;
    }

    public static boolean b(String str, String str2) {
        SharedPreferences a2 = a(str);
        if (a2 != null) {
            return a2.edit().remove(str2).commit();
        }
        return false;
    }
}
