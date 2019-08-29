package com.uc.crashsdk;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import com.uc.crashsdk.a.a;
import com.uc.crashsdk.a.c;

/* compiled from: ProGuard */
public final class r {
    public static PendingIntent a = null;

    public static void a() {
        if (a == null && p.h() >= 0) {
            try {
                Context a2 = e.a();
                Intent launchIntentForPackage = a2.getPackageManager().getLaunchIntentForPackage(a2.getPackageName());
                launchIntentForPackage.addFlags(335544320);
                a = PendingIntent.getActivity(a2, 0, launchIntentForPackage, 0);
            } catch (Throwable th) {
                a.a(th, false);
            }
        }
    }

    public static boolean b() {
        if (a == null) {
            c.c("Restart intent is null!");
            return false;
        }
        try {
            c.b("restarting ...");
            ((AlarmManager) e.a().getSystemService(NotificationCompat.CATEGORY_ALARM)).set(1, System.currentTimeMillis() + 200, a);
            return true;
        } catch (Throwable th) {
            a.a(th, false);
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x007a  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x008a A[SYNTHETIC, Splitter:B:27:0x008a] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0097 A[SYNTHETIC, Splitter:B:34:0x0097] */
    /* JADX WARNING: Removed duplicated region for block: B:46:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(android.content.Context r12) {
        /*
            r10 = 1000(0x3e8, double:4.94E-321)
            r1 = 0
            r0 = 1
            java.lang.String r2 = "restartBrowser"
            com.uc.crashsdk.a.c.a(r2)
            if (r12 != 0) goto L_0x000c
        L_0x000b:
            return
        L_0x000c:
            int r5 = c()
            long r2 = java.lang.System.currentTimeMillis()
            long r6 = r2 / r10
            int r2 = com.uc.crashsdk.p.h()
            if (r2 < 0) goto L_0x00ab
            if (r5 <= 0) goto L_0x002a
            long r2 = (long) r5
            long r2 = r6 - r2
            int r4 = com.uc.crashsdk.p.h()
            long r8 = (long) r4
            int r2 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r2 <= 0) goto L_0x00ab
        L_0x002a:
            long r2 = java.lang.System.currentTimeMillis()
            long r8 = r2 / r10
            com.uc.crashsdk.b.m()
            java.io.File r2 = new java.io.File
            java.lang.String r3 = com.uc.crashsdk.b.d()
            r2.<init>(r3)
            r4 = 0
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0082, all -> 0x0093 }
            r3.<init>(r2)     // Catch:{ Exception -> 0x0082, all -> 0x0093 }
            java.lang.String r2 = java.lang.String.valueOf(r8)     // Catch:{ Exception -> 0x00a9 }
            byte[] r2 = r2.getBytes()     // Catch:{ Exception -> 0x00a9 }
            r3.write(r2)     // Catch:{ Exception -> 0x00a9 }
            if (r3 == 0) goto L_0x0052
            r3.close()     // Catch:{ IOException -> 0x00a5 }
        L_0x0052:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "restartBrowser, lastTime: "
            r2.<init>(r3)
            java.lang.StringBuilder r2 = r2.append(r5)
            java.lang.String r3 = ", currentTime: "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r6)
            java.lang.String r3 = ", needRestart: "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r0)
            java.lang.String r2 = r2.toString()
            com.uc.crashsdk.a.c.a(r2)
            if (r0 == 0) goto L_0x000b
            r0 = 1
            com.uc.crashsdk.d.a(r0)     // Catch:{ Throwable -> 0x009b }
        L_0x007e:
            b()
            goto L_0x000b
        L_0x0082:
            r2 = move-exception
            r3 = r4
        L_0x0084:
            r4 = 1
            com.uc.crashsdk.a.a.a(r2, r4)     // Catch:{ all -> 0x00a7 }
            if (r3 == 0) goto L_0x0052
            r3.close()     // Catch:{ IOException -> 0x008e }
            goto L_0x0052
        L_0x008e:
            r2 = move-exception
        L_0x008f:
            com.uc.crashsdk.a.a.a(r2, r0)
            goto L_0x0052
        L_0x0093:
            r1 = move-exception
            r3 = r4
        L_0x0095:
            if (r3 == 0) goto L_0x009a
            r3.close()     // Catch:{ IOException -> 0x00a0 }
        L_0x009a:
            throw r1
        L_0x009b:
            r0 = move-exception
            com.uc.crashsdk.a.a.a(r0, r1)
            goto L_0x007e
        L_0x00a0:
            r2 = move-exception
            com.uc.crashsdk.a.a.a(r2, r0)
            goto L_0x009a
        L_0x00a5:
            r2 = move-exception
            goto L_0x008f
        L_0x00a7:
            r1 = move-exception
            goto L_0x0095
        L_0x00a9:
            r2 = move-exception
            goto L_0x0084
        L_0x00ab:
            r0 = r1
            goto L_0x0052
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.r.a(android.content.Context):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0041 A[SYNTHETIC, Splitter:B:21:0x0041] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x004b A[SYNTHETIC, Splitter:B:27:0x004b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int c() {
        /*
            r0 = -1
            r5 = 1
            java.lang.String r1 = com.uc.crashsdk.b.d()
            java.io.File r4 = new java.io.File
            r4.<init>(r1)
            boolean r1 = r4.exists()
            if (r1 != 0) goto L_0x0012
        L_0x0011:
            return r0
        L_0x0012:
            r3 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0039, all -> 0x0047 }
            r2.<init>(r4)     // Catch:{ Exception -> 0x0039, all -> 0x0047 }
            long r3 = r4.length()     // Catch:{ Exception -> 0x0056 }
            int r1 = (int) r3     // Catch:{ Exception -> 0x0056 }
            byte[] r1 = new byte[r1]     // Catch:{ Exception -> 0x0056 }
            int r3 = r2.read(r1)     // Catch:{ Exception -> 0x0056 }
            if (r3 <= 0) goto L_0x002e
            java.lang.String r3 = new java.lang.String     // Catch:{ Exception -> 0x0056 }
            r3.<init>(r1)     // Catch:{ Exception -> 0x0056 }
            int r0 = java.lang.Integer.parseInt(r3)     // Catch:{ Exception -> 0x0056 }
        L_0x002e:
            if (r2 == 0) goto L_0x0011
            r2.close()     // Catch:{ IOException -> 0x0034 }
            goto L_0x0011
        L_0x0034:
            r1 = move-exception
        L_0x0035:
            com.uc.crashsdk.a.a.a(r1, r5)
            goto L_0x0011
        L_0x0039:
            r1 = move-exception
            r2 = r3
        L_0x003b:
            r3 = 1
            com.uc.crashsdk.a.a.a(r1, r3)     // Catch:{ all -> 0x0054 }
            if (r2 == 0) goto L_0x0011
            r2.close()     // Catch:{ IOException -> 0x0045 }
            goto L_0x0011
        L_0x0045:
            r1 = move-exception
            goto L_0x0035
        L_0x0047:
            r0 = move-exception
            r2 = r3
        L_0x0049:
            if (r2 == 0) goto L_0x004e
            r2.close()     // Catch:{ IOException -> 0x004f }
        L_0x004e:
            throw r0
        L_0x004f:
            r1 = move-exception
            com.uc.crashsdk.a.a.a(r1, r5)
            goto L_0x004e
        L_0x0054:
            r0 = move-exception
            goto L_0x0049
        L_0x0056:
            r1 = move-exception
            goto L_0x003b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.r.c():int");
    }
}
