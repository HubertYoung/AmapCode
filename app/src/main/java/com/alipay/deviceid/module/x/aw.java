package com.alipay.deviceid.module.x;

import android.content.Context;
import java.util.Map;

public final class aw {
    public static synchronized String a(Context context, String str) {
        String a;
        synchronized (aw.class) {
            try {
                a = az.a(context, "alipay_device_id_storage", "hash".concat(String.valueOf(str)));
            }
        }
        return a;
    }

    public static synchronized void a(Context context) {
        synchronized (aw.class) {
            Map<String, ?> all = context.getSharedPreferences("alipay_device_id_storage", 0).getAll();
            for (String next : all.keySet()) {
                String b = i.b(i.a(), (String) all.get(next));
                StringBuilder sb = new StringBuilder();
                sb.append(next);
                sb.append(" = ");
                sb.append(b);
            }
        }
    }

    public static synchronized void a(Context context, String str, String str2) {
        synchronized (aw.class) {
            c(context, "hash".concat(String.valueOf(str)), str2);
        }
    }

    public static synchronized String b(Context context, String str) {
        String a;
        synchronized (aw.class) {
            try {
                a = az.a(context, "alipay_device_id_storage", "apdidtoekn".concat(String.valueOf(str)));
            }
        }
        return a;
    }

    public static synchronized void b(Context context, String str, String str2) {
        synchronized (aw.class) {
            c(context, "apdidtoekn".concat(String.valueOf(str)), str2);
        }
    }

    private static void c(Context context, String str, String str2) {
        az.a(context, "alipay_device_id_storage", str, str2);
    }
}
