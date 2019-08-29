package com.alipay.apmobilesecuritysdk.loggers;

import com.alipay.security.mobile.module.commonutils.CommonUtils;
import java.util.ArrayList;
import java.util.List;

public class Logger {
    private static String a = "";
    private static String b = "";
    private static String c = "";

    public static synchronized void a(String str, String str2, String str3) {
        synchronized (Logger.class) {
            a = str;
            b = str2;
            c = str3;
        }
    }

    public static synchronized void a(String str) {
        synchronized (Logger.class) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(str);
            a((List<String>) arrayList);
        }
    }

    public static synchronized void a(Throwable th) {
        synchronized (Logger.class) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(CommonUtils.getStackString(th));
            a((List<String>) arrayList);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0093, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static synchronized void a(java.util.List<java.lang.String> r6) {
        /*
            java.lang.Class<com.alipay.apmobilesecuritysdk.loggers.Logger> r0 = com.alipay.apmobilesecuritysdk.loggers.Logger.class
            monitor-enter(r0)
            java.lang.String r1 = b     // Catch:{ all -> 0x0094 }
            boolean r1 = com.alipay.security.mobile.module.commonutils.CommonUtils.isBlank(r1)     // Catch:{ all -> 0x0094 }
            if (r1 != 0) goto L_0x0092
            java.lang.String r1 = c     // Catch:{ all -> 0x0094 }
            boolean r1 = com.alipay.security.mobile.module.commonutils.CommonUtils.isBlank(r1)     // Catch:{ all -> 0x0094 }
            if (r1 == 0) goto L_0x0015
            goto L_0x0092
        L_0x0015:
            java.lang.StringBuffer r1 = new java.lang.StringBuffer     // Catch:{ all -> 0x0094 }
            r1.<init>()     // Catch:{ all -> 0x0094 }
            java.lang.String r2 = c     // Catch:{ all -> 0x0094 }
            r1.append(r2)     // Catch:{ all -> 0x0094 }
            java.util.Iterator r6 = r6.iterator()     // Catch:{ all -> 0x0094 }
        L_0x0023:
            boolean r2 = r6.hasNext()     // Catch:{ all -> 0x0094 }
            if (r2 == 0) goto L_0x003d
            java.lang.Object r2 = r6.next()     // Catch:{ all -> 0x0094 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ all -> 0x0094 }
            java.lang.String r3 = ", "
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x0094 }
            java.lang.String r2 = r3.concat(r2)     // Catch:{ all -> 0x0094 }
            r1.append(r2)     // Catch:{ all -> 0x0094 }
            goto L_0x0023
        L_0x003d:
            java.lang.String r6 = "\n"
            r1.append(r6)     // Catch:{ all -> 0x0094 }
            java.io.File r6 = new java.io.File     // Catch:{ Exception -> 0x0090 }
            java.lang.String r2 = a     // Catch:{ Exception -> 0x0090 }
            r6.<init>(r2)     // Catch:{ Exception -> 0x0090 }
            boolean r2 = r6.exists()     // Catch:{ Exception -> 0x0090 }
            if (r2 != 0) goto L_0x0052
            r6.mkdirs()     // Catch:{ Exception -> 0x0090 }
        L_0x0052:
            java.io.File r6 = new java.io.File     // Catch:{ Exception -> 0x0090 }
            java.lang.String r2 = a     // Catch:{ Exception -> 0x0090 }
            java.lang.String r3 = b     // Catch:{ Exception -> 0x0090 }
            r6.<init>(r2, r3)     // Catch:{ Exception -> 0x0090 }
            boolean r2 = r6.exists()     // Catch:{ Exception -> 0x0090 }
            if (r2 != 0) goto L_0x0064
            r6.createNewFile()     // Catch:{ Exception -> 0x0090 }
        L_0x0064:
            long r2 = r6.length()     // Catch:{ Exception -> 0x0090 }
            int r4 = r1.length()     // Catch:{ Exception -> 0x0090 }
            long r4 = (long) r4     // Catch:{ Exception -> 0x0090 }
            long r4 = r4 + r2
            r2 = 51200(0xc800, double:2.5296E-319)
            int r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r2 > 0) goto L_0x007c
            java.io.FileWriter r2 = new java.io.FileWriter     // Catch:{ Exception -> 0x0090 }
            r3 = 1
            r2.<init>(r6, r3)     // Catch:{ Exception -> 0x0090 }
            goto L_0x0081
        L_0x007c:
            java.io.FileWriter r2 = new java.io.FileWriter     // Catch:{ Exception -> 0x0090 }
            r2.<init>(r6)     // Catch:{ Exception -> 0x0090 }
        L_0x0081:
            java.lang.String r6 = r1.toString()     // Catch:{ Exception -> 0x0090 }
            r2.write(r6)     // Catch:{ Exception -> 0x0090 }
            r2.flush()     // Catch:{ Exception -> 0x0090 }
            r2.close()     // Catch:{ Exception -> 0x0090 }
            monitor-exit(r0)
            return
        L_0x0090:
            monitor-exit(r0)
            return
        L_0x0092:
            monitor-exit(r0)
            return
        L_0x0094:
            r6 = move-exception
            monitor-exit(r0)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.apmobilesecuritysdk.loggers.Logger.a(java.util.List):void");
    }
}
