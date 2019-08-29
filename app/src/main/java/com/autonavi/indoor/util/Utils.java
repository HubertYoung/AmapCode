package com.autonavi.indoor.util;

public class Utils {
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0042 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x004a A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] gzip(byte[] r3) throws java.io.IOException, java.lang.Throwable {
        /*
            r0 = 0
            if (r3 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x003b, Throwable -> 0x0038, all -> 0x0034 }
            r1.<init>()     // Catch:{ IOException -> 0x003b, Throwable -> 0x0038, all -> 0x0034 }
            java.util.zip.GZIPOutputStream r2 = new java.util.zip.GZIPOutputStream     // Catch:{ IOException -> 0x0030, Throwable -> 0x002c, all -> 0x0029 }
            r2.<init>(r1)     // Catch:{ IOException -> 0x0030, Throwable -> 0x002c, all -> 0x0029 }
            r2.write(r3)     // Catch:{ IOException -> 0x0027, Throwable -> 0x0025, all -> 0x0023 }
            r2.finish()     // Catch:{ IOException -> 0x0027, Throwable -> 0x0025, all -> 0x0023 }
            byte[] r3 = r1.toByteArray()     // Catch:{ IOException -> 0x0027, Throwable -> 0x0025, all -> 0x0023 }
            r2.close()     // Catch:{ Throwable -> 0x0021 }
            r1.close()     // Catch:{ Throwable -> 0x001f }
            return r3
        L_0x001f:
            r3 = move-exception
            throw r3
        L_0x0021:
            r3 = move-exception
            throw r3
        L_0x0023:
            r3 = move-exception
            goto L_0x0040
        L_0x0025:
            r3 = move-exception
            goto L_0x002e
        L_0x0027:
            r3 = move-exception
            goto L_0x0032
        L_0x0029:
            r3 = move-exception
            r2 = r0
            goto L_0x0040
        L_0x002c:
            r3 = move-exception
            r2 = r0
        L_0x002e:
            r0 = r1
            goto L_0x003a
        L_0x0030:
            r3 = move-exception
            r2 = r0
        L_0x0032:
            r0 = r1
            goto L_0x003d
        L_0x0034:
            r3 = move-exception
            r1 = r0
            r2 = r1
            goto L_0x0040
        L_0x0038:
            r3 = move-exception
            r2 = r0
        L_0x003a:
            throw r3     // Catch:{ all -> 0x003e }
        L_0x003b:
            r3 = move-exception
            r2 = r0
        L_0x003d:
            throw r3     // Catch:{ all -> 0x003e }
        L_0x003e:
            r3 = move-exception
            r1 = r0
        L_0x0040:
            if (r2 == 0) goto L_0x0048
            r2.close()     // Catch:{ Throwable -> 0x0046 }
            goto L_0x0048
        L_0x0046:
            r3 = move-exception
            throw r3
        L_0x0048:
            if (r1 == 0) goto L_0x0050
            r1.close()     // Catch:{ Throwable -> 0x004e }
            goto L_0x0050
        L_0x004e:
            r3 = move-exception
            throw r3
        L_0x0050:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.indoor.util.Utils.gzip(byte[]):byte[]");
    }
}
