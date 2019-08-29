package com.alipay.android.phone.mobilesdk.permission.utils;

/* compiled from: FileBytes */
public final class d {
    /* JADX WARNING: Removed duplicated region for block: B:18:0x002e A[SYNTHETIC, Splitter:B:18:0x002e] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0033 A[SYNTHETIC, Splitter:B:21:0x0033] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x007d A[SYNTHETIC, Splitter:B:52:0x007d] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0082 A[SYNTHETIC, Splitter:B:55:0x0082] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized byte[] a(java.io.File r12) {
        /*
            java.lang.Class<com.alipay.android.phone.mobilesdk.permission.utils.d> r9 = com.alipay.android.phone.mobilesdk.permission.utils.d.class
            monitor-enter(r9)
            r3 = 0
            r5 = 0
            r1 = 0
            java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch:{ IOException -> 0x00a3 }
            r6.<init>(r12)     // Catch:{ IOException -> 0x00a3 }
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x00a6, all -> 0x009c }
            r2.<init>()     // Catch:{ IOException -> 0x00a6, all -> 0x009c }
            r8 = 1024(0x400, float:1.435E-42)
            byte[] r0 = new byte[r8]     // Catch:{ IOException -> 0x0020, all -> 0x009f }
        L_0x0014:
            int r7 = r6.read(r0)     // Catch:{ IOException -> 0x0020, all -> 0x009f }
            r8 = -1
            if (r7 == r8) goto L_0x0038
            r8 = 0
            r2.write(r0, r8, r7)     // Catch:{ IOException -> 0x0020, all -> 0x009f }
            goto L_0x0014
        L_0x0020:
            r4 = move-exception
            r1 = r2
            r5 = r6
        L_0x0023:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x007a }
            java.lang.String r10 = "FileBytes"
            r8.warn(r10, r4)     // Catch:{ all -> 0x007a }
            if (r5 == 0) goto L_0x0031
            r5.close()     // Catch:{ IOException -> 0x0062 }
        L_0x0031:
            if (r1 == 0) goto L_0x0036
            r1.close()     // Catch:{ IOException -> 0x006f }
        L_0x0036:
            monitor-exit(r9)
            return r3
        L_0x0038:
            byte[] r3 = r2.toByteArray()     // Catch:{ IOException -> 0x0020, all -> 0x009f }
            r6.close()     // Catch:{ IOException -> 0x0045 }
        L_0x003f:
            r2.close()     // Catch:{ IOException -> 0x0055 }
            r1 = r2
            r5 = r6
            goto L_0x0036
        L_0x0045:
            r4 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0050 }
            java.lang.String r10 = "FileBytes"
            r8.warn(r10, r4)     // Catch:{ all -> 0x0050 }
            goto L_0x003f
        L_0x0050:
            r8 = move-exception
            r1 = r2
            r5 = r6
        L_0x0053:
            monitor-exit(r9)
            throw r8
        L_0x0055:
            r4 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0050 }
            java.lang.String r10 = "FileBytes"
            r8.warn(r10, r4)     // Catch:{ all -> 0x0050 }
            r1 = r2
            r5 = r6
            goto L_0x0036
        L_0x0062:
            r4 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x006d }
            java.lang.String r10 = "FileBytes"
            r8.warn(r10, r4)     // Catch:{ all -> 0x006d }
            goto L_0x0031
        L_0x006d:
            r8 = move-exception
            goto L_0x0053
        L_0x006f:
            r4 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x006d }
            java.lang.String r10 = "FileBytes"
            r8.warn(r10, r4)     // Catch:{ all -> 0x006d }
            goto L_0x0036
        L_0x007a:
            r8 = move-exception
        L_0x007b:
            if (r5 == 0) goto L_0x0080
            r5.close()     // Catch:{ IOException -> 0x0086 }
        L_0x0080:
            if (r1 == 0) goto L_0x0085
            r1.close()     // Catch:{ IOException -> 0x0091 }
        L_0x0085:
            throw r8     // Catch:{ all -> 0x006d }
        L_0x0086:
            r4 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r10 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x006d }
            java.lang.String r11 = "FileBytes"
            r10.warn(r11, r4)     // Catch:{ all -> 0x006d }
            goto L_0x0080
        L_0x0091:
            r4 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r10 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x006d }
            java.lang.String r11 = "FileBytes"
            r10.warn(r11, r4)     // Catch:{ all -> 0x006d }
            goto L_0x0085
        L_0x009c:
            r8 = move-exception
            r5 = r6
            goto L_0x007b
        L_0x009f:
            r8 = move-exception
            r1 = r2
            r5 = r6
            goto L_0x007b
        L_0x00a3:
            r4 = move-exception
            goto L_0x0023
        L_0x00a6:
            r4 = move-exception
            r5 = r6
            goto L_0x0023
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilesdk.permission.utils.d.a(java.io.File):byte[]");
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x004a A[SYNTHETIC, Splitter:B:32:0x004a] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x004f A[SYNTHETIC, Splitter:B:35:0x004f] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x006e A[SYNTHETIC, Splitter:B:46:0x006e] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0073 A[SYNTHETIC, Splitter:B:49:0x0073] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized boolean a(byte[] r10, java.io.File r11) {
        /*
            java.lang.Class<com.alipay.android.phone.mobilesdk.permission.utils.d> r7 = com.alipay.android.phone.mobilesdk.permission.utils.d.class
            monitor-enter(r7)
            r0 = 0
            r4 = 0
            r1 = 0
            java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x003e }
            r5.<init>(r11)     // Catch:{ IOException -> 0x003e }
            java.io.BufferedOutputStream r2 = new java.io.BufferedOutputStream     // Catch:{ IOException -> 0x0094, all -> 0x008d }
            r2.<init>(r5)     // Catch:{ IOException -> 0x0094, all -> 0x008d }
            r2.write(r10)     // Catch:{ IOException -> 0x0097, all -> 0x0090 }
            r2.flush()     // Catch:{ IOException -> 0x0097, all -> 0x0090 }
            r0 = 1
            r5.close()     // Catch:{ IOException -> 0x0021 }
        L_0x001a:
            r2.close()     // Catch:{ IOException -> 0x0031 }
            r1 = r2
            r4 = r5
        L_0x001f:
            monitor-exit(r7)
            return r0
        L_0x0021:
            r3 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r6 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x002c }
            java.lang.String r8 = "FileBytes"
            r6.warn(r8, r3)     // Catch:{ all -> 0x002c }
            goto L_0x001a
        L_0x002c:
            r6 = move-exception
            r1 = r2
            r4 = r5
        L_0x002f:
            monitor-exit(r7)
            throw r6
        L_0x0031:
            r3 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r6 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x002c }
            java.lang.String r8 = "FileBytes"
            r6.warn(r8, r3)     // Catch:{ all -> 0x002c }
            r1 = r2
            r4 = r5
            goto L_0x001f
        L_0x003e:
            r3 = move-exception
        L_0x003f:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r6 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x006b }
            java.lang.String r8 = "FileBytes"
            r6.warn(r8, r3)     // Catch:{ all -> 0x006b }
            if (r4 == 0) goto L_0x004d
            r4.close()     // Catch:{ IOException -> 0x0060 }
        L_0x004d:
            if (r1 == 0) goto L_0x001f
            r1.close()     // Catch:{ IOException -> 0x0053 }
            goto L_0x001f
        L_0x0053:
            r3 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r6 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x005e }
            java.lang.String r8 = "FileBytes"
            r6.warn(r8, r3)     // Catch:{ all -> 0x005e }
            goto L_0x001f
        L_0x005e:
            r6 = move-exception
            goto L_0x002f
        L_0x0060:
            r3 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r6 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x005e }
            java.lang.String r8 = "FileBytes"
            r6.warn(r8, r3)     // Catch:{ all -> 0x005e }
            goto L_0x004d
        L_0x006b:
            r6 = move-exception
        L_0x006c:
            if (r4 == 0) goto L_0x0071
            r4.close()     // Catch:{ IOException -> 0x0077 }
        L_0x0071:
            if (r1 == 0) goto L_0x0076
            r1.close()     // Catch:{ IOException -> 0x0082 }
        L_0x0076:
            throw r6     // Catch:{ all -> 0x005e }
        L_0x0077:
            r3 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x005e }
            java.lang.String r9 = "FileBytes"
            r8.warn(r9, r3)     // Catch:{ all -> 0x005e }
            goto L_0x0071
        L_0x0082:
            r3 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x005e }
            java.lang.String r9 = "FileBytes"
            r8.warn(r9, r3)     // Catch:{ all -> 0x005e }
            goto L_0x0076
        L_0x008d:
            r6 = move-exception
            r4 = r5
            goto L_0x006c
        L_0x0090:
            r6 = move-exception
            r1 = r2
            r4 = r5
            goto L_0x006c
        L_0x0094:
            r3 = move-exception
            r4 = r5
            goto L_0x003f
        L_0x0097:
            r3 = move-exception
            r1 = r2
            r4 = r5
            goto L_0x003f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilesdk.permission.utils.d.a(byte[], java.io.File):boolean");
    }
}
