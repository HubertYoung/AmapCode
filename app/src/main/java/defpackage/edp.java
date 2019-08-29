package defpackage;

import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;

/* renamed from: edp reason: default package */
/* compiled from: RideResultFullLinkLog */
public final class edp {
    public static void a(String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            if (!TextUtils.isEmpty(str)) {
                AMapLog.debug("route.ridepath", "android", b(str, str2));
            } else {
                AMapLog.debug("route.ridepath", "android", str2);
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
