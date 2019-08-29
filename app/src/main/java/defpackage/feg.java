package defpackage;

import java.util.concurrent.ConcurrentHashMap;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;

/* renamed from: feg reason: default package */
/* compiled from: ApiLockHelper */
public final class feg {
    private static ConcurrentHashMap<String, feh> a = new ConcurrentHashMap<>();

    public static boolean a(String str, long j) {
        boolean z = false;
        if (fdd.b(str)) {
            return false;
        }
        feh feh = a.get(str);
        if (feh != null) {
            if (Math.abs(j - feh.b) < feh.c) {
                z = true;
            } else {
                a.remove(str);
                if (TBSdkLog.a(LogEnable.WarnEnable)) {
                    TBSdkLog.c("mtopsdk.ApiLockHelper", "[iSApiLocked]remove apiKey=".concat(String.valueOf(str)));
                }
            }
            if (TBSdkLog.a(LogEnable.WarnEnable)) {
                StringBuilder sb = new StringBuilder("[iSApiLocked] isLocked=");
                sb.append(z);
                sb.append(", ");
                sb.append(a(j, feh));
                TBSdkLog.c("mtopsdk.ApiLockHelper", sb.toString());
            }
        }
        return z;
    }

    public static void b(String str, long j) {
        if (!fdd.b(str)) {
            feh feh = a.get(str);
            fff.a();
            long a2 = fff.a(str);
            if (a2 <= 0) {
                fff.a();
                a2 = fff.f();
                if (a2 <= 0) {
                    a2 = 10;
                }
            }
            long j2 = a2;
            if (feh == null) {
                feh = new feh(str, j, j2);
            } else {
                feh.b = j;
                feh.c = j2;
            }
            a.put(str, feh);
            if (TBSdkLog.a(LogEnable.WarnEnable)) {
                StringBuilder sb = new StringBuilder("[lock]");
                sb.append(a(j, feh));
                TBSdkLog.c("mtopsdk.ApiLockHelper", sb.toString());
            }
        }
    }

    private static String a(long j, feh feh) {
        StringBuilder sb = new StringBuilder(32);
        sb.append(", currentTime=");
        sb.append(j);
        StringBuilder sb2 = new StringBuilder(", lockEntity=");
        sb2.append(feh.toString());
        sb.append(sb2.toString());
        return sb.toString();
    }
}
