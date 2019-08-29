package com.xiaomi.push.service;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.metoknlp.geofencing.a;
import com.xiaomi.xmpush.thrift.m;
import java.util.ArrayList;
import java.util.Iterator;

public class k {
    public static void a(Context context, String str) {
        ArrayList<m> b = h.a(context).b(str);
        if (b != null && b.size() > 0) {
            if (h.a(context).e(str) == 0) {
                b.a("appIsUninstalled. failed to delete geofencing with package name. name:".concat(String.valueOf(str)));
            }
            Iterator<m> it = b.iterator();
            while (it.hasNext()) {
                m next = it.next();
                if (next == null) {
                    b.a("appIsUninstalled. failed to find geofence with package name. name:".concat(String.valueOf(str)));
                    return;
                }
                a(next.a(), context);
                if (j.a(context).b(next.a()) == 0) {
                    StringBuilder sb = new StringBuilder("appIsUninstalled. failed to delete geoMessage with package name. name:");
                    sb.append(str);
                    sb.append(", geoId:");
                    sb.append(next.a());
                    b.a(sb.toString());
                }
            }
        }
    }

    public static void a(Context context, boolean z) {
        bc.a(context).a((String) "mipush_extra", (String) "geo_switch", Boolean.valueOf(z));
    }

    public static void a(String str, Context context) {
        new a(context).a(context, "com.xiaomi.xmsf", str);
    }

    public static boolean a(Context context) {
        return a(context, "com.xiaomi.metoknlp", 6);
    }

    public static boolean a(Context context, String str, int i) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(str, 0);
        } catch (NameNotFoundException unused) {
            packageInfo = null;
        }
        return packageInfo != null && packageInfo.versionCode >= i;
    }

    public static boolean b(Context context) {
        return a(context, "com.xiaomi.xmsf", 106) && (a(context, "com.xiaomi.metok", 20) || a(context, "com.xiaomi.metoknlp", 6));
    }

    public static boolean c(Context context) {
        return TextUtils.equals(context.getPackageName(), "com.xiaomi.xmsf");
    }

    public static boolean d(Context context) {
        return a(context);
    }

    public static boolean e(Context context) {
        return bc.a(context).a((String) "mipush_extra", (String) "geo_switch", false);
    }
}
