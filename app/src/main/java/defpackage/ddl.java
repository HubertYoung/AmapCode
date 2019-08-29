package defpackage;

import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;

/* renamed from: ddl reason: default package */
/* compiled from: ShareLogUtil */
public final class ddl {
    public static void a(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            str = "share";
        }
        AMapLog.info("basemap.share", str, str2);
    }
}
