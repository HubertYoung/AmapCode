package com.ali.user.mobile.accountbiz.sp;

import android.content.Context;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

public class SharedPreferencesManager {
    private static LruCache<String, AUSharedPreferences> a = new LruCache<>(30);

    public static AUSharedPreferences a(Context context, String str, int i) {
        if (context == null || TextUtils.isEmpty(str)) {
            return null;
        }
        AUSharedPreferences aUSharedPreferences = (AUSharedPreferences) a.get(str);
        if (aUSharedPreferences == null) {
            synchronized (SharedPreferencesManager.class) {
                try {
                    aUSharedPreferences = (AUSharedPreferences) a.get(str);
                    if (aUSharedPreferences == null) {
                        aUSharedPreferences = new AUSharedPreferences(context, str, i);
                        a.put(str, aUSharedPreferences);
                    }
                }
            }
        }
        aUSharedPreferences.a();
        return aUSharedPreferences;
    }
}
