package com.uc.crashsdk;

import android.content.Context;
import com.uc.crashsdk.a.c;

/* compiled from: ProGuard */
public final class e {
    public static String a = null;
    private static Context b;

    public static void a(Context context) {
        if (b != null) {
            c.b("mContext is existed");
        }
        b = context;
        a = context.getApplicationInfo().dataDir;
    }

    public static Context a() {
        return b;
    }

    public static Object a(String str) {
        return b.getSystemService(str);
    }
}
