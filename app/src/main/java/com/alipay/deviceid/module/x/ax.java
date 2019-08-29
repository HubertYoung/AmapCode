package com.alipay.deviceid.module.x;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import java.util.UUID;

public final class ax {
    private static String a = "";

    public static void a(Context context, String str) {
        a(context, (String) "webrtcurl", str);
    }

    public static void a(Context context, String str, long j) {
        try {
            Editor edit = context.getSharedPreferences("alipay_device_id_settings", 0).edit();
            if (edit != null) {
                edit.putString("vkey_valid".concat(String.valueOf(str)), i.a(i.a(), String.valueOf(j)));
                edit.commit();
            }
        } catch (Throwable unused) {
        }
    }

    private static void a(Context context, String str, String str2) {
        az.a(context, "alipay_device_id_settings", str, str2);
    }

    public static void a(Context context, boolean z) {
        a(context, (String) "log_switch", z ? "1" : "0");
    }

    public static boolean a(Context context) {
        String a2 = az.a(context, "alipay_device_id_settings", "log_switch");
        return a2 != null && "1".equals(a2);
    }

    public static long b(Context context, String str) {
        try {
            String string = context.getSharedPreferences("alipay_device_id_settings", 0).getString("vkey_valid".concat(String.valueOf(str)), "");
            if (e.a(string)) {
                return 0;
            }
            String b = i.b(i.a(), string);
            if (e.a(b)) {
                return 0;
            }
            return Long.parseLong(b);
        } catch (Throwable unused) {
            return 0;
        }
    }

    public static synchronized String b(Context context) {
        String str;
        synchronized (ax.class) {
            if (e.a(a)) {
                String a2 = v.a(context, "alipay_device_id_tags", "random", "");
                a = a2;
                if (e.a(a2)) {
                    a = h.a(UUID.randomUUID().toString());
                    String str2 = a;
                    if (str2 != null) {
                        Editor edit = context.getSharedPreferences("alipay_device_id_tags", 0).edit();
                        if (edit != null) {
                            edit.putString("random", str2);
                            edit.commit();
                        }
                    }
                }
            }
            str = a;
        }
        return str;
    }
}
