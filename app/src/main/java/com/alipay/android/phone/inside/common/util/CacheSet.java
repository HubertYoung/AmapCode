package com.alipay.android.phone.inside.common.util;

import android.content.Context;
import android.content.SharedPreferences;

public class CacheSet {
    private static CacheSet b;
    private Context a;

    public static synchronized CacheSet a(Context context) {
        CacheSet cacheSet;
        synchronized (CacheSet.class) {
            try {
                if (b == null) {
                    b = new CacheSet();
                }
                b.a = context.getApplicationContext();
                cacheSet = b;
            }
        }
        return cacheSet;
    }

    private CacheSet() {
    }

    public final String a(String str) {
        SharedPreferences sharedPreferences = this.a.getSharedPreferences(com.alipay.mobile.common.utils.CacheSet.FILE_NAME, 0);
        if (sharedPreferences == null) {
            return "";
        }
        return sharedPreferences.getString(str, "");
    }
}
