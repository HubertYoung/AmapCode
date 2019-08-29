package defpackage;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.util.Iterator;
import java.util.List;

/* renamed from: cwg reason: default package */
/* compiled from: ProcessUtils */
public final class cwg {
    private static String a;

    public static String a(Context context) {
        if (TextUtils.isEmpty(a)) {
            String c = c(context);
            if (TextUtils.isEmpty(c)) {
                c = a();
            }
            a = c;
        }
        return a;
    }

    public static String b(Context context) {
        String a2 = a(context);
        return !TextUtils.isEmpty(a2) ? a2.replace(context.getPackageName(), "").replace(":", "") : "";
    }

    private static String c(Context context) {
        if (context == null) {
            return null;
        }
        if (a != null) {
            return a;
        }
        int myPid = Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(WidgetType.ACTIVITY);
        if (activityManager == null) {
            return null;
        }
        List<RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return null;
        }
        Iterator<RunningAppProcessInfo> it = runningAppProcesses.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            RunningAppProcessInfo next = it.next();
            if (next != null && next.pid == myPid) {
                a = next.processName;
                break;
            }
        }
        return a;
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0041  */
    /* JADX WARNING: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String a() {
        /*
            int r0 = android.os.Process.myPid()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "/proc/"
            r1.<init>(r2)
            r1.append(r0)
            java.lang.String r0 = "/cmdline"
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            r1 = 1024(0x400, float:1.435E-42)
            byte[] r1 = new byte[r1]
            r2 = 0
            r3 = 0
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ Exception -> 0x002f, all -> 0x002c }
            r4.<init>(r0)     // Catch:{ Exception -> 0x002f, all -> 0x002c }
            int r0 = r4.read(r1)     // Catch:{ Exception -> 0x002a }
            defpackage.cwf.a(r4)
            goto L_0x003f
        L_0x002a:
            r0 = move-exception
            goto L_0x0031
        L_0x002c:
            r0 = move-exception
            r4 = r2
            goto L_0x004c
        L_0x002f:
            r0 = move-exception
            r4 = r2
        L_0x0031:
            java.lang.String r5 = "ProcessUtils"
            r6 = 1
            java.lang.Exception[] r6 = new java.lang.Exception[r6]     // Catch:{ all -> 0x004b }
            r6[r3] = r0     // Catch:{ all -> 0x004b }
            defpackage.cwl.d(r5, r6)     // Catch:{ all -> 0x004b }
            defpackage.cwf.a(r4)
            r0 = 0
        L_0x003f:
            if (r0 <= 0) goto L_0x004a
            java.lang.String r2 = new java.lang.String
            r2.<init>(r1, r3, r0)
            java.lang.String r2 = r2.trim()
        L_0x004a:
            return r2
        L_0x004b:
            r0 = move-exception
        L_0x004c:
            defpackage.cwf.a(r4)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cwg.a():java.lang.String");
    }
}
