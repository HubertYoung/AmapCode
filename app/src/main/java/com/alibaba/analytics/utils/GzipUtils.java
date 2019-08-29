package com.alibaba.analytics.utils;

public class GzipUtils {
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0040 A[SYNTHETIC, Splitter:B:29:0x0040] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x004a A[SYNTHETIC, Splitter:B:34:0x004a] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0058 A[SYNTHETIC, Splitter:B:43:0x0058] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0062 A[SYNTHETIC, Splitter:B:48:0x0062] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] gzip(byte[] r4) {
        /*
            if (r4 == 0) goto L_0x006b
            int r0 = r4.length
            if (r0 != 0) goto L_0x0007
            goto L_0x006b
        L_0x0007:
            r0 = 0
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0038, all -> 0x0035 }
            r1.<init>()     // Catch:{ Exception -> 0x0038, all -> 0x0035 }
            java.util.zip.GZIPOutputStream r2 = new java.util.zip.GZIPOutputStream     // Catch:{ Exception -> 0x0032, all -> 0x0030 }
            int r3 = r4.length     // Catch:{ Exception -> 0x0032, all -> 0x0030 }
            r2.<init>(r1, r3)     // Catch:{ Exception -> 0x0032, all -> 0x0030 }
            r2.write(r4)     // Catch:{ Exception -> 0x002e }
            r2.finish()     // Catch:{ Exception -> 0x002e }
            byte[] r4 = r1.toByteArray()     // Catch:{ Exception -> 0x002e }
            r2.close()     // Catch:{ IOException -> 0x0021 }
            goto L_0x0025
        L_0x0021:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0025:
            r1.close()     // Catch:{ IOException -> 0x0029 }
            goto L_0x0053
        L_0x0029:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0053
        L_0x002e:
            r4 = move-exception
            goto L_0x003b
        L_0x0030:
            r4 = move-exception
            goto L_0x0056
        L_0x0032:
            r4 = move-exception
            r2 = r0
            goto L_0x003b
        L_0x0035:
            r4 = move-exception
            r1 = r0
            goto L_0x0056
        L_0x0038:
            r4 = move-exception
            r1 = r0
            r2 = r1
        L_0x003b:
            r4.printStackTrace()     // Catch:{ all -> 0x0054 }
            if (r2 == 0) goto L_0x0048
            r2.close()     // Catch:{ IOException -> 0x0044 }
            goto L_0x0048
        L_0x0044:
            r4 = move-exception
            r4.printStackTrace()
        L_0x0048:
            if (r1 == 0) goto L_0x0052
            r1.close()     // Catch:{ IOException -> 0x004e }
            goto L_0x0052
        L_0x004e:
            r4 = move-exception
            r4.printStackTrace()
        L_0x0052:
            r4 = r0
        L_0x0053:
            return r4
        L_0x0054:
            r4 = move-exception
            r0 = r2
        L_0x0056:
            if (r0 == 0) goto L_0x0060
            r0.close()     // Catch:{ IOException -> 0x005c }
            goto L_0x0060
        L_0x005c:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0060:
            if (r1 == 0) goto L_0x006a
            r1.close()     // Catch:{ IOException -> 0x0066 }
            goto L_0x006a
        L_0x0066:
            r0 = move-exception
            r0.printStackTrace()
        L_0x006a:
            throw r4
        L_0x006b:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.utils.GzipUtils.gzip(byte[]):byte[]");
    }

    /* JADX WARNING: type inference failed for: r4v0, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARNING: type inference failed for: r4v1, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARNING: type inference failed for: r4v2 */
    /* JADX WARNING: type inference failed for: r4v3 */
    /* JADX WARNING: type inference failed for: r4v4 */
    /* JADX WARNING: type inference failed for: r4v5 */
    /* JADX WARNING: type inference failed for: r4v6 */
    /* JADX WARNING: type inference failed for: r4v7 */
    /* JADX WARNING: type inference failed for: r4v8 */
    /* JADX WARNING: type inference failed for: r4v10 */
    /* JADX WARNING: type inference failed for: r4v11 */
    /* JADX WARNING: type inference failed for: r4v12 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0060 A[SYNTHETIC, Splitter:B:44:0x0060] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x006a A[SYNTHETIC, Splitter:B:49:0x006a] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0074 A[SYNTHETIC, Splitter:B:54:0x0074] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0080 A[SYNTHETIC, Splitter:B:60:0x0080] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x008a A[SYNTHETIC, Splitter:B:65:0x008a] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0094 A[SYNTHETIC, Splitter:B:70:0x0094] */
    /* JADX WARNING: Removed duplicated region for block: B:78:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Unknown variable types count: 4 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] unGzip(byte[] r8) {
        /*
            r0 = 0
            java.io.ByteArrayInputStream r1 = new java.io.ByteArrayInputStream     // Catch:{ Exception -> 0x0057, all -> 0x0051 }
            r1.<init>(r8)     // Catch:{ Exception -> 0x0057, all -> 0x0051 }
            java.util.zip.GZIPInputStream r8 = new java.util.zip.GZIPInputStream     // Catch:{ Exception -> 0x004d, all -> 0x004a }
            r8.<init>(r1)     // Catch:{ Exception -> 0x004d, all -> 0x004a }
            r2 = 1024(0x400, float:1.435E-42)
            byte[] r3 = new byte[r2]     // Catch:{ Exception -> 0x0047, all -> 0x0043 }
            java.io.ByteArrayOutputStream r4 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0047, all -> 0x0043 }
            r4.<init>()     // Catch:{ Exception -> 0x0047, all -> 0x0043 }
        L_0x0014:
            r5 = 0
            int r6 = r8.read(r3, r5, r2)     // Catch:{ Exception -> 0x0041 }
            r7 = -1
            if (r6 == r7) goto L_0x0020
            r4.write(r3, r5, r6)     // Catch:{ Exception -> 0x0041 }
            goto L_0x0014
        L_0x0020:
            r4.flush()     // Catch:{ Exception -> 0x0041 }
            byte[] r2 = r4.toByteArray()     // Catch:{ Exception -> 0x0041 }
            r4.close()     // Catch:{ Exception -> 0x002b }
            goto L_0x002f
        L_0x002b:
            r0 = move-exception
            r0.printStackTrace()
        L_0x002f:
            r8.close()     // Catch:{ IOException -> 0x0033 }
            goto L_0x0037
        L_0x0033:
            r8 = move-exception
            r8.printStackTrace()
        L_0x0037:
            r1.close()     // Catch:{ IOException -> 0x003b }
            goto L_0x003f
        L_0x003b:
            r8 = move-exception
            r8.printStackTrace()
        L_0x003f:
            r0 = r2
            goto L_0x007c
        L_0x0041:
            r2 = move-exception
            goto L_0x005b
        L_0x0043:
            r2 = move-exception
            r4 = r0
            r0 = r2
            goto L_0x007e
        L_0x0047:
            r2 = move-exception
            r4 = r0
            goto L_0x005b
        L_0x004a:
            r8 = move-exception
            r4 = r0
            goto L_0x0054
        L_0x004d:
            r2 = move-exception
            r8 = r0
            r4 = r8
            goto L_0x005b
        L_0x0051:
            r8 = move-exception
            r1 = r0
            r4 = r1
        L_0x0054:
            r0 = r8
            r8 = r4
            goto L_0x007e
        L_0x0057:
            r2 = move-exception
            r8 = r0
            r1 = r8
            r4 = r1
        L_0x005b:
            r2.printStackTrace()     // Catch:{ all -> 0x007d }
            if (r4 == 0) goto L_0x0068
            r4.close()     // Catch:{ Exception -> 0x0064 }
            goto L_0x0068
        L_0x0064:
            r2 = move-exception
            r2.printStackTrace()
        L_0x0068:
            if (r8 == 0) goto L_0x0072
            r8.close()     // Catch:{ IOException -> 0x006e }
            goto L_0x0072
        L_0x006e:
            r8 = move-exception
            r8.printStackTrace()
        L_0x0072:
            if (r1 == 0) goto L_0x007c
            r1.close()     // Catch:{ IOException -> 0x0078 }
            goto L_0x007c
        L_0x0078:
            r8 = move-exception
            r8.printStackTrace()
        L_0x007c:
            return r0
        L_0x007d:
            r0 = move-exception
        L_0x007e:
            if (r4 == 0) goto L_0x0088
            r4.close()     // Catch:{ Exception -> 0x0084 }
            goto L_0x0088
        L_0x0084:
            r2 = move-exception
            r2.printStackTrace()
        L_0x0088:
            if (r8 == 0) goto L_0x0092
            r8.close()     // Catch:{ IOException -> 0x008e }
            goto L_0x0092
        L_0x008e:
            r8 = move-exception
            r8.printStackTrace()
        L_0x0092:
            if (r1 == 0) goto L_0x009c
            r1.close()     // Catch:{ IOException -> 0x0098 }
            goto L_0x009c
        L_0x0098:
            r8 = move-exception
            r8.printStackTrace()
        L_0x009c:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.utils.GzipUtils.unGzip(byte[]):byte[]");
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0029 A[SYNTHETIC, Splitter:B:17:0x0029] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x003a A[SYNTHETIC, Splitter:B:26:0x003a] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] gzipAndRc4Bytes(java.lang.String r3) {
        /*
            java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream
            r0.<init>()
            r1 = 0
            java.util.zip.GZIPOutputStream r2 = new java.util.zip.GZIPOutputStream     // Catch:{ IOException -> 0x0023 }
            r2.<init>(r0)     // Catch:{ IOException -> 0x0023 }
            java.lang.String r1 = "UTF-8"
            byte[] r3 = r3.getBytes(r1)     // Catch:{ IOException -> 0x001d, all -> 0x001b }
            r2.write(r3)     // Catch:{ IOException -> 0x001d, all -> 0x001b }
            r2.flush()     // Catch:{ IOException -> 0x001d, all -> 0x001b }
            r2.close()     // Catch:{ Exception -> 0x002c }
            goto L_0x002c
        L_0x001b:
            r3 = move-exception
            goto L_0x0038
        L_0x001d:
            r3 = move-exception
            r1 = r2
            goto L_0x0024
        L_0x0020:
            r3 = move-exception
            r2 = r1
            goto L_0x0038
        L_0x0023:
            r3 = move-exception
        L_0x0024:
            r3.printStackTrace()     // Catch:{ all -> 0x0020 }
            if (r1 == 0) goto L_0x002c
            r1.close()     // Catch:{ Exception -> 0x002c }
        L_0x002c:
            byte[] r3 = r0.toByteArray()
            byte[] r3 = com.alibaba.analytics.utils.RC4.rc4(r3)
            r0.close()     // Catch:{ Exception -> 0x0037 }
        L_0x0037:
            return r3
        L_0x0038:
            if (r2 == 0) goto L_0x003d
            r2.close()     // Catch:{ Exception -> 0x003d }
        L_0x003d:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.utils.GzipUtils.gzipAndRc4Bytes(java.lang.String):byte[]");
    }
}
