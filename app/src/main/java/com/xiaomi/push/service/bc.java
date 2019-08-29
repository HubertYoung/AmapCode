package com.xiaomi.push.service;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

public class bc {
    private static volatile bc a;
    /* access modifiers changed from: private */
    public Context b;
    private Handler c = new Handler(Looper.getMainLooper());
    private Map<String, Map<String, String>> d = new HashMap();

    private bc(Context context) {
        this.b = context;
    }

    public static bc a(Context context) {
        if (a == null) {
            synchronized (bc.class) {
                try {
                    if (a == null) {
                        a = new bc(context);
                    }
                }
            }
        }
        return a;
    }

    private synchronized String a(String str, String str2) {
        if (this.d == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return "";
        }
        try {
            Map map = this.d.get(str);
            if (map == null) {
                return "";
            }
            return (String) map.get(str2);
        } catch (Throwable unused) {
            return "";
        }
    }

    private synchronized void c(String str, String str2, String str3) {
        if (this.d == null) {
            this.d = new HashMap();
        }
        Map map = this.d.get(str);
        if (map == null) {
            map = new HashMap();
        }
        map.put(str2, str3);
        this.d.put(str, map);
    }

    public synchronized void a(String str, String str2, Boolean bool) {
        c(str, str2, String.valueOf(bool));
        this.c.post(new bd(this, str, str2, bool));
    }

    public synchronized void a(String str, String str2, String str3) {
        c(str, str2, str3);
        this.c.post(new be(this, str, str2, str3));
    }

    public synchronized boolean a(String str, String str2, boolean z) {
        try {
            String a2 = a(str, str2);
            if (!TextUtils.isEmpty(a2)) {
                return Boolean.parseBoolean(a2);
            }
            return this.b.getSharedPreferences(str, 4).getBoolean(str2, z);
        } catch (Throwable unused) {
            return z;
        }
    }

    public synchronized String b(String str, String str2, String str3) {
        String a2 = a(str, str2);
        if (!TextUtils.isEmpty(a2)) {
            return a2;
        }
        return this.b.getSharedPreferences(str, 4).getString(str2, str3);
    }
}
