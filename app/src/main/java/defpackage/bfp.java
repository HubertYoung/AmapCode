package defpackage;

import com.amap.bundle.logs.AMapLog;

/* renamed from: bfp reason: default package */
/* compiled from: VUILogUtil */
public final class bfp {
    public static void a(bfq bfq, int i, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(bfq.b);
        sb.append("-");
        sb.append(i);
        sb.append(",");
        sb.append(str);
        AMapLog.error("route.vui", bfq.a, sb.toString());
    }

    public static void b(bfq bfq, int i, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(bfq.b);
        sb.append("-");
        sb.append(i);
        sb.append(",");
        sb.append(str);
        AMapLog.info("route.vui", bfq.a, sb.toString());
    }
}
