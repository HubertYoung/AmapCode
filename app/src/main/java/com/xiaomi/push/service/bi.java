package com.xiaomi.push.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.MIPushNotificationHelper4Hybrid;
import com.xiaomi.xmpush.thrift.af;
import java.util.Map;

public class bi {
    public static Runnable a;

    private static String a(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("typed_shield_pref", 4);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("_title");
        return sharedPreferences.getString(sb.toString(), str);
    }

    public static String a(af afVar) {
        Map<String, String> s = afVar.m().s();
        if (s == null) {
            return null;
        }
        return s.get("__typed_shield_type");
    }

    @TargetApi(19)
    static void a(Context context, af afVar, Notification notification) {
        if (VERSION.SDK_INT >= 19) {
            String a2 = a(afVar);
            if (!TextUtils.isEmpty(a2) && "com.xiaomi.xmsf".equals(ah.a(afVar))) {
                Bundle bundle = notification.extras;
                if (bundle == null) {
                    bundle = new Bundle();
                }
                bundle.putString(MIPushNotificationHelper4Hybrid.KEY_PKGNAME, a2);
                bundle.putString(MIPushNotificationHelper4Hybrid.KEY_TITLE, a(context, a2));
                notification.extras = bundle;
            }
        }
    }

    public static boolean a(Context context, af afVar) {
        if (!"com.xiaomi.xmsf".equals(ah.a(afVar))) {
            return false;
        }
        String a2 = a(afVar);
        if (TextUtils.isEmpty(a2)) {
            return false;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("typed_shield_pref", 4);
        StringBuilder sb = new StringBuilder();
        sb.append(a2);
        sb.append("_shield");
        if (!sharedPreferences.contains(sb.toString()) && a != null) {
            a.run();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(a2);
        sb2.append("_shield");
        return sharedPreferences.getBoolean(sb2.toString(), true);
    }
}
