package defpackage;

import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;

/* renamed from: eau reason: default package */
/* compiled from: FootNaviFullLinkLog */
public final class eau {
    public static void a(String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            if (!TextUtils.isEmpty(str)) {
                StringBuilder sb = new StringBuilder(" [");
                sb.append(str);
                sb.append("] ");
                sb.append(str2);
                AMapLog.debug("route.footnavi", "android", sb.toString());
                return;
            }
            AMapLog.debug("route.footnavi", "android", str2);
        }
    }
}
