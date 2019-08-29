package com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip;

import com.alipay.mobile.common.logging.api.LoggerFactory;

public class LzmaAlone {
    private static final String TAG = "LzmaAlone";

    /* JADX WARNING: Removed duplicated region for block: B:75:0x00f2 A[SYNTHETIC, Splitter:B:75:0x00f2] */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x00fc A[SYNTHETIC, Splitter:B:80:0x00fc] */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x0109 A[SYNTHETIC, Splitter:B:88:0x0109] */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x0113 A[SYNTHETIC, Splitter:B:93:0x0113] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean sevenZipFile(boolean r12, boolean r13, java.lang.String r14, java.lang.String r15) {
        /*
            r0 = 0
            if (r14 == 0) goto L_0x011d
            int r1 = r14.length()
            if (r1 == 0) goto L_0x011d
            if (r15 == 0) goto L_0x011d
            int r1 = r15.length()
            if (r1 != 0) goto L_0x0013
            goto L_0x011d
        L_0x0013:
            java.io.File r1 = new java.io.File
            r1.<init>(r14)
            java.io.File r14 = new java.io.File
            r14.<init>(r15)
            boolean r15 = r1.exists()
            if (r15 == 0) goto L_0x011c
            boolean r15 = r1.isFile()
            if (r15 == 0) goto L_0x011c
            boolean r15 = r14.isDirectory()
            if (r15 == 0) goto L_0x0031
            goto L_0x011c
        L_0x0031:
            r15 = 0
            java.io.BufferedInputStream r10 = new java.io.BufferedInputStream     // Catch:{ Throwable -> 0x00eb, all -> 0x00e7 }
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x00eb, all -> 0x00e7 }
            r2.<init>(r1)     // Catch:{ Throwable -> 0x00eb, all -> 0x00e7 }
            r10.<init>(r2)     // Catch:{ Throwable -> 0x00eb, all -> 0x00e7 }
            java.io.BufferedOutputStream r11 = new java.io.BufferedOutputStream     // Catch:{ Throwable -> 0x00e3, all -> 0x00e0 }
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x00e3, all -> 0x00e0 }
            r2.<init>(r14)     // Catch:{ Throwable -> 0x00e3, all -> 0x00e0 }
            r11.<init>(r2)     // Catch:{ Throwable -> 0x00e3, all -> 0x00e0 }
            r14 = 8
            if (r12 == 0) goto L_0x007e
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder r2 = new com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder     // Catch:{ Throwable -> 0x007c, all -> 0x0079 }
            r2.<init>()     // Catch:{ Throwable -> 0x007c, all -> 0x0079 }
            r2.SetEndMarkerMode(r13)     // Catch:{ Throwable -> 0x007c, all -> 0x0079 }
            r2.WriteCoderProperties(r11)     // Catch:{ Throwable -> 0x007c, all -> 0x0079 }
            if (r13 == 0) goto L_0x005a
            r12 = -1
            goto L_0x005e
        L_0x005a:
            long r12 = r1.length()     // Catch:{ Throwable -> 0x007c, all -> 0x0079 }
        L_0x005e:
            r15 = 0
        L_0x005f:
            if (r15 >= r14) goto L_0x006e
            int r1 = r15 * 8
            long r3 = r12 >>> r1
            int r1 = (int) r3     // Catch:{ Throwable -> 0x007c, all -> 0x0079 }
            r1 = r1 & 255(0xff, float:3.57E-43)
            r11.write(r1)     // Catch:{ Throwable -> 0x007c, all -> 0x0079 }
            int r15 = r15 + 1
            goto L_0x005f
        L_0x006e:
            r5 = -1
            r7 = -1
            r9 = 0
            r3 = r10
            r4 = r11
            r2.Code(r3, r4, r5, r7, r9)     // Catch:{ Throwable -> 0x007c, all -> 0x0079 }
            goto L_0x00cb
        L_0x0079:
            r12 = move-exception
            goto L_0x0107
        L_0x007c:
            r12 = move-exception
            goto L_0x00e5
        L_0x007e:
            r12 = 5
            byte[] r13 = new byte[r12]     // Catch:{ Throwable -> 0x007c, all -> 0x0079 }
            int r15 = r10.read(r13, r0, r12)     // Catch:{ Throwable -> 0x007c, all -> 0x0079 }
            if (r15 == r12) goto L_0x008f
            java.lang.Exception r12 = new java.lang.Exception     // Catch:{ Throwable -> 0x007c, all -> 0x0079 }
            java.lang.String r13 = "input .lzma file is too short"
            r12.<init>(r13)     // Catch:{ Throwable -> 0x007c, all -> 0x0079 }
            throw r12     // Catch:{ Throwable -> 0x007c, all -> 0x0079 }
        L_0x008f:
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Decoder r12 = new com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Decoder     // Catch:{ Throwable -> 0x007c, all -> 0x0079 }
            r12.<init>()     // Catch:{ Throwable -> 0x007c, all -> 0x0079 }
            r1 = 0
            boolean r13 = r12.SetDecoderProperties(r13)     // Catch:{ Throwable -> 0x007c, all -> 0x0079 }
            if (r13 != 0) goto L_0x00a4
            java.lang.Exception r12 = new java.lang.Exception     // Catch:{ Throwable -> 0x007c, all -> 0x0079 }
            java.lang.String r13 = "Incorrect stream properties"
            r12.<init>(r13)     // Catch:{ Throwable -> 0x007c, all -> 0x0079 }
            throw r12     // Catch:{ Throwable -> 0x007c, all -> 0x0079 }
        L_0x00a4:
            r13 = 0
        L_0x00a5:
            if (r13 >= r14) goto L_0x00bd
            int r15 = r10.read()     // Catch:{ Throwable -> 0x007c, all -> 0x0079 }
            if (r15 >= 0) goto L_0x00b5
            java.lang.Exception r12 = new java.lang.Exception     // Catch:{ Throwable -> 0x007c, all -> 0x0079 }
            java.lang.String r13 = "Can't read stream size"
            r12.<init>(r13)     // Catch:{ Throwable -> 0x007c, all -> 0x0079 }
            throw r12     // Catch:{ Throwable -> 0x007c, all -> 0x0079 }
        L_0x00b5:
            long r3 = (long) r15     // Catch:{ Throwable -> 0x007c, all -> 0x0079 }
            int r15 = r13 * 8
            long r3 = r3 << r15
            long r1 = r1 | r3
            int r13 = r13 + 1
            goto L_0x00a5
        L_0x00bd:
            boolean r12 = r12.Code(r10, r11, r1)     // Catch:{ Throwable -> 0x007c, all -> 0x0079 }
            if (r12 != 0) goto L_0x00cb
            java.lang.Exception r12 = new java.lang.Exception     // Catch:{ Throwable -> 0x007c, all -> 0x0079 }
            java.lang.String r13 = "Error in data stream"
            r12.<init>(r13)     // Catch:{ Throwable -> 0x007c, all -> 0x0079 }
            throw r12     // Catch:{ Throwable -> 0x007c, all -> 0x0079 }
        L_0x00cb:
            r11.flush()     // Catch:{ Throwable -> 0x007c, all -> 0x0079 }
            r10.close()     // Catch:{ Throwable -> 0x00d2 }
            goto L_0x00d6
        L_0x00d2:
            r12 = move-exception
            printThrowableLog(r12)
        L_0x00d6:
            r11.close()     // Catch:{ Throwable -> 0x00da }
            goto L_0x00de
        L_0x00da:
            r12 = move-exception
            printThrowableLog(r12)
        L_0x00de:
            r12 = 1
            return r12
        L_0x00e0:
            r12 = move-exception
            r11 = r15
            goto L_0x0107
        L_0x00e3:
            r12 = move-exception
            r11 = r15
        L_0x00e5:
            r15 = r10
            goto L_0x00ed
        L_0x00e7:
            r12 = move-exception
            r10 = r15
            r11 = r10
            goto L_0x0107
        L_0x00eb:
            r12 = move-exception
            r11 = r15
        L_0x00ed:
            printThrowableLog(r12)     // Catch:{ all -> 0x0105 }
            if (r15 == 0) goto L_0x00fa
            r15.close()     // Catch:{ Throwable -> 0x00f6 }
            goto L_0x00fa
        L_0x00f6:
            r12 = move-exception
            printThrowableLog(r12)
        L_0x00fa:
            if (r11 == 0) goto L_0x0104
            r11.close()     // Catch:{ Throwable -> 0x0100 }
            goto L_0x0104
        L_0x0100:
            r12 = move-exception
            printThrowableLog(r12)
        L_0x0104:
            return r0
        L_0x0105:
            r12 = move-exception
            r10 = r15
        L_0x0107:
            if (r10 == 0) goto L_0x0111
            r10.close()     // Catch:{ Throwable -> 0x010d }
            goto L_0x0111
        L_0x010d:
            r13 = move-exception
            printThrowableLog(r13)
        L_0x0111:
            if (r11 == 0) goto L_0x011b
            r11.close()     // Catch:{ Throwable -> 0x0117 }
            goto L_0x011b
        L_0x0117:
            r13 = move-exception
            printThrowableLog(r13)
        L_0x011b:
            throw r12
        L_0x011c:
            return r0
        L_0x011d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LzmaAlone.sevenZipFile(boolean, boolean, java.lang.String, java.lang.String):boolean");
    }

    private static void printThrowableLog(Throwable th) {
        LoggerFactory.getTraceLogger().error(TAG, "sevenZipFile", th);
    }
}
