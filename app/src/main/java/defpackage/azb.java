package defpackage;

import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;

/* renamed from: azb reason: default package */
/* compiled from: CommuteFullLinkLog */
public final class azb {
    public static void a(String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            if (!TextUtils.isEmpty(str)) {
                AMapLog.debug("route.routecommute", "android", b(str, str2));
            } else {
                AMapLog.debug("route.routecommute", "android", str2);
            }
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
