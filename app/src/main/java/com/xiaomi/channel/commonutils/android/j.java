package com.xiaomi.channel.commonutils.android;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.channel.commonutils.reflect.a;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class j {
    private static Context a;

    public static Context a() {
        return a;
    }

    public static void a(Context context) {
        a = context.getApplicationContext();
    }

    public static String b() {
        String b = d.b(a);
        if (b == null) {
            b = d.h(a);
        }
        if (b != null) {
            try {
                return Base64.encodeToString(MessageDigest.getInstance("SHA1").digest(b.getBytes()), 8).substring(0, 16);
            } catch (NoSuchAlgorithmException e) {
                b.a((Throwable) e);
            }
        }
        return null;
    }

    public static boolean b(Context context) {
        try {
            return (context.getApplicationInfo().flags & 2) != 0;
        } catch (Exception e) {
            b.a((Throwable) e);
            return false;
        }
    }

    public static int c() {
        try {
            Class<?> cls = Class.forName("miui.os.Build");
            if (cls.getField("IS_STABLE_VERSION").getBoolean(null)) {
                return 3;
            }
            return cls.getField("IS_DEVELOPMENT_VERSION").getBoolean(null) ? 2 : 1;
        } catch (Exception unused) {
            return 0;
        }
    }

    public static boolean d() {
        return TextUtils.equals((String) a.a((String) "android.os.SystemProperties", (String) "get", "sys.boot_completed"), "1");
    }

    public static boolean e() {
        try {
            return Class.forName("miui.os.Build").getField("IS_GLOBAL_BUILD").getBoolean(Boolean.FALSE);
        } catch (Exception e) {
            b.a((Throwable) e);
            return false;
        }
    }
}
