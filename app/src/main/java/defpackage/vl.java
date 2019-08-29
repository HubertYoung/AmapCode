package defpackage;

import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;

/* renamed from: vl reason: default package */
/* compiled from: HeadUnitFullLinkLog */
public final class vl {
    public static void a(String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            if (!TextUtils.isEmpty(str)) {
                StringBuilder sb = new StringBuilder(" [");
                sb.append(str);
                sb.append("] ");
                sb.append(str2);
                AMapLog.debug("route.carlinkinfo", "android", sb.toString());
                return;
            }
            AMapLog.debug("route.carlinkinfo", "android", str2);
        }
    }
}
