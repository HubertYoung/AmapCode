package defpackage;

import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;

/* renamed from: duy reason: default package */
/* compiled from: BusLineFullLinkLog */
public final class duy {
    public static void a(String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            if (!TextUtils.isEmpty(str)) {
                AMapLog.debug("route.busline", "android", b(str, str2));
                return;
            }
            AMapLog.debug("route.busline", "android", str2);
        }
    }

    private static String b(String str, String str2) {
        StringBuilder sb = new StringBuilder(" [");
        sb.append(str);
        sb.append("] ");
        sb.append(str2);
        return sb.toString();
    }
}
