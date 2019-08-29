package com.ta.audid.utils;

public class UmidUtils {
    private static String mUmidToken = "";

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0063, code lost:
        return r10;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized java.lang.String getUmidToken(android.content.Context r10) {
        /*
            java.lang.Class<com.ta.audid.utils.UmidUtils> r0 = com.ta.audid.utils.UmidUtils.class
            monitor-enter(r0)
            java.lang.String r1 = mUmidToken     // Catch:{ all -> 0x0072 }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ all -> 0x0072 }
            if (r1 != 0) goto L_0x000f
            java.lang.String r10 = mUmidToken     // Catch:{ all -> 0x0072 }
            monitor-exit(r0)
            return r10
        L_0x000f:
            r1 = 1
            r2 = 0
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0064 }
            com.alibaba.wireless.security.open.SecurityGuardManager r10 = com.alibaba.wireless.security.open.SecurityGuardManager.getInstance(r10)     // Catch:{ Throwable -> 0x0064 }
            com.alibaba.wireless.security.open.umid.IUMIDComponent r10 = r10.getUMIDComp()     // Catch:{ Throwable -> 0x0064 }
            int r5 = r10.initUMIDSync(r2)     // Catch:{ Throwable -> 0x0064 }
            r6 = 200(0xc8, float:2.8E-43)
            if (r5 == r6) goto L_0x0038
            java.util.HashMap r6 = new java.util.HashMap     // Catch:{ Throwable -> 0x0064 }
            r6.<init>()     // Catch:{ Throwable -> 0x0064 }
            java.lang.String r7 = "errorCode"
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Throwable -> 0x0064 }
            r6.put(r7, r5)     // Catch:{ Throwable -> 0x0064 }
            java.lang.String r5 = "umid"
            com.ta.audid.utils.UtUtils.sendUtdidMonitorEvent(r5, r6)     // Catch:{ Throwable -> 0x0064 }
        L_0x0038:
            java.lang.String r5 = ""
            java.lang.Object[] r6 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x0064 }
            long r7 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0064 }
            r9 = 0
            long r7 = r7 - r3
            java.lang.Long r3 = java.lang.Long.valueOf(r7)     // Catch:{ Throwable -> 0x0064 }
            r6[r2] = r3     // Catch:{ Throwable -> 0x0064 }
            com.ta.audid.utils.UtdidLogger.d(r5, r6)     // Catch:{ Throwable -> 0x0064 }
            java.lang.String r10 = r10.getSecurityToken(r2)     // Catch:{ Throwable -> 0x0064 }
            boolean r3 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Throwable -> 0x0064 }
            if (r3 != 0) goto L_0x0062
            int r3 = r10.length()     // Catch:{ Throwable -> 0x0064 }
            r4 = 24
            if (r3 != r4) goto L_0x005e
            goto L_0x0062
        L_0x005e:
            mUmidToken = r10     // Catch:{ Throwable -> 0x0064 }
            monitor-exit(r0)
            return r10
        L_0x0062:
            monitor-exit(r0)
            return r10
        L_0x0064:
            r10 = move-exception
            java.lang.String r3 = ""
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x0072 }
            r1[r2] = r10     // Catch:{ all -> 0x0072 }
            com.ta.audid.utils.UtdidLogger.d(r3, r1)     // Catch:{ all -> 0x0072 }
            java.lang.String r10 = ""
            monitor-exit(r0)
            return r10
        L_0x0072:
            r10 = move-exception
            monitor-exit(r0)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.audid.utils.UmidUtils.getUmidToken(android.content.Context):java.lang.String");
    }
}
