package com.alipay.mobile.tinyappcustom.biz;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: MiniProgramStorageManager */
public class a {
    private static a c;
    private SharedPreferences a;
    private ConcurrentHashMap<String, String> b = new ConcurrentHashMap<>();

    private a(Context context) {
        this.a = context.getSharedPreferences("mini_program_auth_storage", 0);
    }

    public static a a(Context context) {
        if (c == null) {
            synchronized (a.class) {
                try {
                    if (c == null) {
                        c = new a(context);
                    }
                }
            }
        }
        return c;
    }

    public final boolean a() {
        this.a.edit().putString("AUTH_CONFIRM", "1").apply();
        return true;
    }
}
