package defpackage;

import com.amap.bundle.logs.AMapLog;
import com.autonavi.ae.AEUtil;

/* renamed from: tq reason: default package */
/* compiled from: DriveLogUtil */
public final class tq {
    public static void a(String str, String str2, String str3) {
        a("NaviMonitor", str, str2, str3);
    }

    public static void a(String str, String str2, String str3, String str4) {
        ku a = ku.a();
        StringBuilder sb = new StringBuilder("[");
        sb.append(str2);
        sb.append("]  ");
        sb.append(a(str3, str4));
        a.c(str, sb.toString());
    }

    private static String a(String str, String str2) {
        if (!AEUtil.IS_DEBUG) {
            return "";
        }
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("MSG=");
        stringBuffer.append(str);
        stringBuffer.append(" | ");
        stringBuffer.append(str2);
        stringBuffer.append("\n");
        stringBuffer.append("THREAD=");
        stringBuffer.append(Thread.currentThread().getName());
        for (int i = 4; i < stackTrace.length; i++) {
            stringBuffer.append("\n\t\t\t");
            stringBuffer.append(stackTrace[i]);
        }
        return stringBuffer.toString();
    }

    public static void b(String str, String str2, String str3) {
        AMapLog.d(str2, str3);
        ku a = ku.a();
        StringBuilder sb = new StringBuilder("[");
        sb.append(str2);
        sb.append("]  ");
        sb.append(str3);
        a.c(str, sb.toString());
    }
}
