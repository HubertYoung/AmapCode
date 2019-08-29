package com.ali.auth.third.core.device;

import android.content.Context;
import android.text.TextUtils;

public class DeviceInfo {
    public static String deviceId;

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0041  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void a(android.content.Context r7) {
        /*
            java.lang.String r0 = "com.ta.utdid2.device.UTDevice"
            java.lang.Class r0 = com.ali.auth.third.core.util.ReflectionUtils.loadClassQuietly(r0)
            r1 = 0
            if (r0 == 0) goto L_0x0033
            java.lang.String r2 = "getUtdid"
            r3 = 1
            java.lang.Class[] r4 = new java.lang.Class[r3]     // Catch:{ Exception -> 0x002c }
            java.lang.Class<android.content.Context> r5 = android.content.Context.class
            r6 = 0
            r4[r6] = r5     // Catch:{ Exception -> 0x002c }
            java.lang.reflect.Method r0 = r0.getMethod(r2, r4)     // Catch:{ Exception -> 0x002c }
            java.lang.Object[] r2 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x002c }
            r2[r6] = r7     // Catch:{ Exception -> 0x002c }
            java.lang.Object r0 = r0.invoke(r1, r2)     // Catch:{ Exception -> 0x002c }
            if (r0 != 0) goto L_0x0029
            java.lang.String r0 = ""
            java.lang.String r2 = "get utdid null"
            com.ali.auth.third.core.trace.SDKLogger.e(r0, r2)     // Catch:{ Exception -> 0x002c }
            goto L_0x0033
        L_0x0029:
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x002c }
            goto L_0x0034
        L_0x002c:
            java.lang.String r0 = ""
            java.lang.String r2 = "get utdid error"
            com.ali.auth.third.core.trace.SDKLogger.e(r0, r2)
        L_0x0033:
            r0 = r1
        L_0x0034:
            if (r0 != 0) goto L_0x0041
            com.ali.auth.third.core.service.MemberExecutorService r0 = com.ali.auth.third.core.context.KernelContext.executorService
            com.ali.auth.third.core.device.a r1 = new com.ali.auth.third.core.device.a
            r1.<init>(r7)
        L_0x003d:
            r0.postTask(r1)
            return
        L_0x0041:
            deviceId = r0
            java.lang.String r1 = ""
            java.lang.String r2 = "utdid = "
            java.lang.String r0 = java.lang.String.valueOf(r0)
            java.lang.String r0 = r2.concat(r0)
            com.ali.auth.third.core.trace.SDKLogger.e(r1, r0)
            com.ali.auth.third.core.service.MemberExecutorService r0 = com.ali.auth.third.core.context.KernelContext.executorService
            com.ali.auth.third.core.device.b r1 = new com.ali.auth.third.core.device.b
            r1.<init>(r7)
            goto L_0x003d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.auth.third.core.device.DeviceInfo.a(android.content.Context):void");
    }

    public static void init(Context context) {
        if (TextUtils.isEmpty(deviceId)) {
            a(context);
        }
    }
}
