package com.alipay.mobile.common.transport.utils;

public class GzipUtils {
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0042 A[SYNTHETIC, Splitter:B:21:0x0042] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0047 A[SYNTHETIC, Splitter:B:24:0x0047] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x004c A[SYNTHETIC, Splitter:B:27:0x004c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] toGzip(byte[] r14) {
        /*
            r0 = 0
            r2 = 0
            r7 = 0
            java.io.ByteArrayInputStream r1 = new java.io.ByteArrayInputStream     // Catch:{ IOException -> 0x009a }
            r1.<init>(r14)     // Catch:{ IOException -> 0x009a }
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x009c, all -> 0x008e }
            r3.<init>()     // Catch:{ IOException -> 0x009c, all -> 0x008e }
            java.util.zip.GZIPOutputStream r8 = new java.util.zip.GZIPOutputStream     // Catch:{ IOException -> 0x009f, all -> 0x0091 }
            r8.<init>(r3)     // Catch:{ IOException -> 0x009f, all -> 0x0091 }
            r11 = 4096(0x1000, float:5.74E-42)
            byte[] r4 = new byte[r11]     // Catch:{ IOException -> 0x0022, all -> 0x0095 }
        L_0x0016:
            int r10 = r1.read(r4)     // Catch:{ IOException -> 0x0022, all -> 0x0095 }
            r11 = -1
            if (r10 == r11) goto L_0x0050
            r11 = 0
            r8.write(r4, r11, r10)     // Catch:{ IOException -> 0x0022, all -> 0x0095 }
            goto L_0x0016
        L_0x0022:
            r6 = move-exception
            r7 = r8
            r2 = r3
            r0 = r1
        L_0x0026:
            java.lang.String r11 = "GzipUtils"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x003f }
            java.lang.String r13 = "toGzip ex:"
            r12.<init>(r13)     // Catch:{ all -> 0x003f }
            java.lang.String r13 = r6.toString()     // Catch:{ all -> 0x003f }
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ all -> 0x003f }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x003f }
            com.alipay.mobile.common.transport.utils.LogCatUtil.error(r11, r12)     // Catch:{ all -> 0x003f }
            throw r6     // Catch:{ all -> 0x003f }
        L_0x003f:
            r11 = move-exception
        L_0x0040:
            if (r0 == 0) goto L_0x0045
            r0.close()     // Catch:{ Throwable -> 0x0079 }
        L_0x0045:
            if (r2 == 0) goto L_0x004a
            r2.close()     // Catch:{ Throwable -> 0x0080 }
        L_0x004a:
            if (r7 == 0) goto L_0x004f
            r7.close()     // Catch:{ Throwable -> 0x0087 }
        L_0x004f:
            throw r11
        L_0x0050:
            r8.flush()     // Catch:{ IOException -> 0x0022, all -> 0x0095 }
            r8.finish()     // Catch:{ IOException -> 0x0022, all -> 0x0095 }
            byte[] r9 = r3.toByteArray()     // Catch:{ IOException -> 0x0022, all -> 0x0095 }
            r1.close()     // Catch:{ Throwable -> 0x0064 }
        L_0x005d:
            r3.close()     // Catch:{ Throwable -> 0x006b }
        L_0x0060:
            r8.close()     // Catch:{ Throwable -> 0x0072 }
        L_0x0063:
            return r9
        L_0x0064:
            r5 = move-exception
            java.lang.String r11 = "GzipUtils"
            com.alipay.mobile.common.transport.utils.LogCatUtil.printError(r11, r5)
            goto L_0x005d
        L_0x006b:
            r5 = move-exception
            java.lang.String r11 = "GzipUtils"
            com.alipay.mobile.common.transport.utils.LogCatUtil.printError(r11, r5)
            goto L_0x0060
        L_0x0072:
            r5 = move-exception
            java.lang.String r11 = "GzipUtils"
            com.alipay.mobile.common.transport.utils.LogCatUtil.printError(r11, r5)
            goto L_0x0063
        L_0x0079:
            r5 = move-exception
            java.lang.String r12 = "GzipUtils"
            com.alipay.mobile.common.transport.utils.LogCatUtil.printError(r12, r5)
            goto L_0x0045
        L_0x0080:
            r5 = move-exception
            java.lang.String r12 = "GzipUtils"
            com.alipay.mobile.common.transport.utils.LogCatUtil.printError(r12, r5)
            goto L_0x004a
        L_0x0087:
            r5 = move-exception
            java.lang.String r12 = "GzipUtils"
            com.alipay.mobile.common.transport.utils.LogCatUtil.printError(r12, r5)
            goto L_0x004f
        L_0x008e:
            r11 = move-exception
            r0 = r1
            goto L_0x0040
        L_0x0091:
            r11 = move-exception
            r2 = r3
            r0 = r1
            goto L_0x0040
        L_0x0095:
            r11 = move-exception
            r7 = r8
            r2 = r3
            r0 = r1
            goto L_0x0040
        L_0x009a:
            r6 = move-exception
            goto L_0x0026
        L_0x009c:
            r6 = move-exception
            r0 = r1
            goto L_0x0026
        L_0x009f:
            r6 = move-exception
            r2 = r3
            r0 = r1
            goto L_0x0026
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transport.utils.GzipUtils.toGzip(byte[]):byte[]");
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0045 A[SYNTHETIC, Splitter:B:21:0x0045] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x004a A[SYNTHETIC, Splitter:B:24:0x004a] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x004f A[SYNTHETIC, Splitter:B:27:0x004f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] unGzip(byte[] r14) {
        /*
            r0 = 0
            r7 = 0
            r2 = 0
            java.io.ByteArrayInputStream r1 = new java.io.ByteArrayInputStream     // Catch:{ IOException -> 0x009a }
            r1.<init>(r14)     // Catch:{ IOException -> 0x009a }
            java.util.zip.GZIPInputStream r8 = new java.util.zip.GZIPInputStream     // Catch:{ IOException -> 0x009c, all -> 0x008e }
            r8.<init>(r1)     // Catch:{ IOException -> 0x009c, all -> 0x008e }
            r11 = 4096(0x1000, float:5.74E-42)
            byte[] r4 = new byte[r11]     // Catch:{ IOException -> 0x009f, all -> 0x0091 }
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x009f, all -> 0x0091 }
            r3.<init>()     // Catch:{ IOException -> 0x009f, all -> 0x0091 }
        L_0x0016:
            r11 = 0
            r12 = 4096(0x1000, float:5.74E-42)
            int r9 = r8.read(r4, r11, r12)     // Catch:{ IOException -> 0x0025, all -> 0x0095 }
            r11 = -1
            if (r9 == r11) goto L_0x0053
            r11 = 0
            r3.write(r4, r11, r9)     // Catch:{ IOException -> 0x0025, all -> 0x0095 }
            goto L_0x0016
        L_0x0025:
            r6 = move-exception
            r2 = r3
            r7 = r8
            r0 = r1
        L_0x0029:
            java.lang.String r11 = "GzipUtils"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x0042 }
            java.lang.String r13 = "unGzip ex:"
            r12.<init>(r13)     // Catch:{ all -> 0x0042 }
            java.lang.String r13 = r6.toString()     // Catch:{ all -> 0x0042 }
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ all -> 0x0042 }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x0042 }
            com.alipay.mobile.common.transport.utils.LogCatUtil.error(r11, r12)     // Catch:{ all -> 0x0042 }
            throw r6     // Catch:{ all -> 0x0042 }
        L_0x0042:
            r11 = move-exception
        L_0x0043:
            if (r2 == 0) goto L_0x0048
            r2.close()     // Catch:{ Throwable -> 0x0079 }
        L_0x0048:
            if (r7 == 0) goto L_0x004d
            r7.close()     // Catch:{ Throwable -> 0x0080 }
        L_0x004d:
            if (r0 == 0) goto L_0x0052
            r0.close()     // Catch:{ Throwable -> 0x0087 }
        L_0x0052:
            throw r11
        L_0x0053:
            byte[] r10 = r3.toByteArray()     // Catch:{ IOException -> 0x0025, all -> 0x0095 }
            r3.flush()     // Catch:{ IOException -> 0x0025, all -> 0x0095 }
            r3.close()     // Catch:{ Throwable -> 0x0064 }
        L_0x005d:
            r8.close()     // Catch:{ Throwable -> 0x006b }
        L_0x0060:
            r1.close()     // Catch:{ Throwable -> 0x0072 }
        L_0x0063:
            return r10
        L_0x0064:
            r5 = move-exception
            java.lang.String r11 = "GzipUtils"
            com.alipay.mobile.common.transport.utils.LogCatUtil.printError(r11, r5)
            goto L_0x005d
        L_0x006b:
            r5 = move-exception
            java.lang.String r11 = "GzipUtils"
            com.alipay.mobile.common.transport.utils.LogCatUtil.printError(r11, r5)
            goto L_0x0060
        L_0x0072:
            r5 = move-exception
            java.lang.String r11 = "GzipUtils"
            com.alipay.mobile.common.transport.utils.LogCatUtil.printError(r11, r5)
            goto L_0x0063
        L_0x0079:
            r5 = move-exception
            java.lang.String r12 = "GzipUtils"
            com.alipay.mobile.common.transport.utils.LogCatUtil.printError(r12, r5)
            goto L_0x0048
        L_0x0080:
            r5 = move-exception
            java.lang.String r12 = "GzipUtils"
            com.alipay.mobile.common.transport.utils.LogCatUtil.printError(r12, r5)
            goto L_0x004d
        L_0x0087:
            r5 = move-exception
            java.lang.String r12 = "GzipUtils"
            com.alipay.mobile.common.transport.utils.LogCatUtil.printError(r12, r5)
            goto L_0x0052
        L_0x008e:
            r11 = move-exception
            r0 = r1
            goto L_0x0043
        L_0x0091:
            r11 = move-exception
            r7 = r8
            r0 = r1
            goto L_0x0043
        L_0x0095:
            r11 = move-exception
            r2 = r3
            r7 = r8
            r0 = r1
            goto L_0x0043
        L_0x009a:
            r6 = move-exception
            goto L_0x0029
        L_0x009c:
            r6 = move-exception
            r0 = r1
            goto L_0x0029
        L_0x009f:
            r6 = move-exception
            r7 = r8
            r0 = r1
            goto L_0x0029
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transport.utils.GzipUtils.unGzip(byte[]):byte[]");
    }
}
