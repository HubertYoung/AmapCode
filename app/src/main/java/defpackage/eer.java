package defpackage;

import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;

/* renamed from: eer reason: default package */
/* compiled from: HealthyRideFullLinkLog */
public final class eer {
    private static String a = "[%4d-%02d-%02d %02d:%02d:%02d]";

    public static void a(String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            if (!TextUtils.isEmpty(str)) {
                StringBuilder sb = new StringBuilder(" [");
                sb.append(str);
                sb.append("] ");
                sb.append(str2);
                AMapLog.debug("route.healthyride", "android", sb.toString());
                return;
            }
            AMapLog.debug("route.healthyride", "android", str2);
        }
    }

    public static void a(String str) {
        a(null, str);
    }

    public static void a(String str, double d, double d2) {
        a(null, String.format("[GPS] [%.5f,%.5f,] [%s]", new Object[]{Double.valueOf(d), Double.valueOf(d2), str}));
    }

    public static void a(String str, double d, double d2, Object... objArr) {
        if (objArr.length == 0) {
            a(str, d, d2);
            return;
        }
        StringBuilder sb = new StringBuilder(String.format("[GPS] [%.5f,%.5f,] [%s]", new Object[]{Double.valueOf(d), Double.valueOf(d2), str}));
        sb.append(", [");
        for (Object valueOf : objArr) {
            sb.append(String.valueOf(valueOf));
            sb.append(",");
        }
        sb.append("]");
        a(null, sb.toString());
    }

    public static void a(String str, int i, float f, double d, double d2, double d3, double d4, int i2, int i3, int i4, int i5, int i6, int i7) {
        a(str, d, d2, Integer.valueOf(i), Float.valueOf(f), Double.valueOf(d3), Double.valueOf(d4), String.format(a, new Object[]{Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6), Integer.valueOf(i7)}));
    }
}
