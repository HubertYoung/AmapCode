package defpackage;

import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;

/* renamed from: zu reason: default package */
/* compiled from: SceneMapsFilter */
public final class zu {
    private static boolean a = false;
    private static String b = "/ws/mps/";

    public static void a(boolean z) {
        a = z;
    }

    public static boolean a(String str) {
        boolean z = true;
        if (!TextUtils.isEmpty(str) && a) {
            z = true ^ str.contains(b);
        }
        if (zt.a) {
            StringBuilder sb = new StringBuilder("result [");
            sb.append(z);
            sb.append("] needStaticsRequestFlow requestUrl:");
            sb.append(str);
            sb.append(" mFilterEnable 【");
            sb.append(a);
            sb.append("]");
            AMapLog.e("SceneMapsFilter", sb.toString());
        }
        return z;
    }
}
