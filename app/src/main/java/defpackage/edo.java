package defpackage;

import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;

/* renamed from: edo reason: default package */
/* compiled from: RideNaviFullLinkLog */
public final class edo {
    public static void a(String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            if (!TextUtils.isEmpty(str)) {
                AMapLog.debug("route.ridenavi", "android", b(str, str2));
            } else {
                AMapLog.debug("route.ridenavi", "android", str2);
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
