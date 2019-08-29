package defpackage;

import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;

/* renamed from: ask reason: default package */
/* compiled from: BusCardFullLinkLog */
public final class ask {
    public static void a(String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            if (!TextUtils.isEmpty(str)) {
                StringBuilder sb = new StringBuilder(" [");
                sb.append(str);
                sb.append("] ");
                sb.append(str2);
                AMapLog.debug("route.buscard", "android", sb.toString());
                return;
            }
            AMapLog.debug("route.buscard", "android", str2);
        }
    }
}
