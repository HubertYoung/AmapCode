package com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils;

public class Exif {
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0064, code lost:
        if (r2 <= 8) goto L_0x00e0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0066, code lost:
        r7 = a(r14, r5, 4, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x006d, code lost:
        if (r7 == 1229531648) goto L_0x0081;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0072, code lost:
        if (r7 == 1296891946) goto L_0x0081;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0074, code lost:
        com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.E("CameraExif", "Invalid byte order", new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0084, code lost:
        if (r7 != 1229531648) goto L_0x009f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0086, code lost:
        r0 = a(r14, r5 + 4, 4, r3) + 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0090, code lost:
        if (r0 < 10) goto L_0x0094;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0092, code lost:
        if (r0 <= r2) goto L_0x00a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0094, code lost:
        com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.E("CameraExif", "Invalid offset", new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x009f, code lost:
        r3 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00a1, code lost:
        r5 = r5 + r0;
        r2 = r2 - r0;
        r0 = a(r14, r5 - 2, 2, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00aa, code lost:
        r1 = r0;
        r0 = r1 - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00ac, code lost:
        if (r1 <= 0) goto L_0x00e0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00b0, code lost:
        if (r2 < 12) goto L_0x00e0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00b8, code lost:
        if (a(r14, r5, 2, r3) != 274) goto L_0x00da;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00c0, code lost:
        switch(a(r14, r5 + 8, 2, r3)) {
            case 1: goto L_0x0008;
            case 2: goto L_0x00c3;
            case 3: goto L_0x00ce;
            case 4: goto L_0x00c3;
            case 5: goto L_0x00c3;
            case 6: goto L_0x00d2;
            case 7: goto L_0x00c3;
            case 8: goto L_0x00d6;
            default: goto L_0x00c3;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00c3, code lost:
        com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.I("CameraExif", "Unsupported orientation", new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00da, code lost:
        r5 = r5 + 12;
        r2 = r2 - 12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00e0, code lost:
        com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.I("CameraExif", "Orientation not found", new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:?, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:?, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:?, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:?, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:?, code lost:
        return 180;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:?, code lost:
        return 90;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:?, code lost:
        return 270;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:?, code lost:
        return 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int getOrientation(byte[] r14) {
        /*
            r13 = 8
            r3 = 1
            r12 = 4
            r11 = 2
            r8 = 0
            if (r14 != 0) goto L_0x0009
        L_0x0008:
            return r8
        L_0x0009:
            r5 = 0
            r2 = 0
        L_0x000b:
            int r9 = r5 + 3
            int r10 = r14.length
            if (r9 >= r10) goto L_0x0064
            int r6 = r5 + 1
            byte r9 = r14[r5]
            r9 = r9 & 255(0xff, float:3.57E-43)
            r10 = 255(0xff, float:3.57E-43)
            if (r9 != r10) goto L_0x00ee
            byte r9 = r14[r6]
            r4 = r9 & 255(0xff, float:3.57E-43)
            r9 = 255(0xff, float:3.57E-43)
            if (r4 == r9) goto L_0x00eb
            int r5 = r6 + 1
            r9 = 216(0xd8, float:3.03E-43)
            if (r4 == r9) goto L_0x000b
            if (r4 == r3) goto L_0x000b
            r9 = 217(0xd9, float:3.04E-43)
            if (r4 == r9) goto L_0x0064
            r9 = 218(0xda, float:3.05E-43)
            if (r4 == r9) goto L_0x0064
            int r2 = a(r14, r5, r11, r8)
            if (r2 < r11) goto L_0x003d
            int r9 = r5 + r2
            int r10 = r14.length
            if (r9 <= r10) goto L_0x0047
        L_0x003d:
            java.lang.String r9 = "CameraExif"
            java.lang.String r10 = "Invalid length"
            java.lang.Object[] r11 = new java.lang.Object[r8]
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.E(r9, r10, r11)
            goto L_0x0008
        L_0x0047:
            r9 = 225(0xe1, float:3.15E-43)
            if (r4 != r9) goto L_0x007e
            if (r2 < r13) goto L_0x007e
            int r9 = r5 + 2
            int r9 = a(r14, r9, r12, r8)
            r10 = 1165519206(0x45786966, float:3974.5874)
            if (r9 != r10) goto L_0x007e
            int r9 = r5 + 6
            int r9 = a(r14, r9, r11, r8)
            if (r9 != 0) goto L_0x007e
            int r5 = r5 + 8
            int r2 = r2 + -8
        L_0x0064:
            if (r2 <= r13) goto L_0x00e0
            int r7 = a(r14, r5, r12, r8)
            r9 = 1229531648(0x49492a00, float:823968.0)
            if (r7 == r9) goto L_0x0081
            r9 = 1296891946(0x4d4d002a, float:2.14958752E8)
            if (r7 == r9) goto L_0x0081
            java.lang.String r9 = "CameraExif"
            java.lang.String r10 = "Invalid byte order"
            java.lang.Object[] r11 = new java.lang.Object[r8]
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.E(r9, r10, r11)
            goto L_0x0008
        L_0x007e:
            int r5 = r5 + r2
            r2 = 0
            goto L_0x000b
        L_0x0081:
            r9 = 1229531648(0x49492a00, float:823968.0)
            if (r7 != r9) goto L_0x009f
        L_0x0086:
            int r9 = r5 + 4
            int r9 = a(r14, r9, r12, r3)
            int r0 = r9 + 2
            r9 = 10
            if (r0 < r9) goto L_0x0094
            if (r0 <= r2) goto L_0x00a1
        L_0x0094:
            java.lang.String r9 = "CameraExif"
            java.lang.String r10 = "Invalid offset"
            java.lang.Object[] r11 = new java.lang.Object[r8]
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.E(r9, r10, r11)
            goto L_0x0008
        L_0x009f:
            r3 = r8
            goto L_0x0086
        L_0x00a1:
            int r5 = r5 + r0
            int r2 = r2 - r0
            int r9 = r5 + -2
            int r0 = a(r14, r9, r11, r3)
            r1 = r0
        L_0x00aa:
            int r0 = r1 + -1
            if (r1 <= 0) goto L_0x00e0
            r9 = 12
            if (r2 < r9) goto L_0x00e0
            int r9 = a(r14, r5, r11, r3)
            r10 = 274(0x112, float:3.84E-43)
            if (r9 != r10) goto L_0x00da
            int r9 = r5 + 8
            int r9 = a(r14, r9, r11, r3)
            switch(r9) {
                case 1: goto L_0x0008;
                case 2: goto L_0x00c3;
                case 3: goto L_0x00ce;
                case 4: goto L_0x00c3;
                case 5: goto L_0x00c3;
                case 6: goto L_0x00d2;
                case 7: goto L_0x00c3;
                case 8: goto L_0x00d6;
                default: goto L_0x00c3;
            }
        L_0x00c3:
            java.lang.String r9 = "CameraExif"
            java.lang.String r10 = "Unsupported orientation"
            java.lang.Object[] r11 = new java.lang.Object[r8]
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.I(r9, r10, r11)
            goto L_0x0008
        L_0x00ce:
            r8 = 180(0xb4, float:2.52E-43)
            goto L_0x0008
        L_0x00d2:
            r8 = 90
            goto L_0x0008
        L_0x00d6:
            r8 = 270(0x10e, float:3.78E-43)
            goto L_0x0008
        L_0x00da:
            int r5 = r5 + 12
            int r2 = r2 + -12
            r1 = r0
            goto L_0x00aa
        L_0x00e0:
            java.lang.String r9 = "CameraExif"
            java.lang.String r10 = "Orientation not found"
            java.lang.Object[] r11 = new java.lang.Object[r8]
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.I(r9, r10, r11)
            goto L_0x0008
        L_0x00eb:
            r5 = r6
            goto L_0x000b
        L_0x00ee:
            r5 = r6
            goto L_0x0064
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Exif.getOrientation(byte[]):int");
    }

    private static int a(byte[] bytes, int offset, int length, boolean littleEndian) {
        int step = 1;
        if (littleEndian) {
            offset += length - 1;
            step = -1;
        }
        int value = 0;
        while (true) {
            int length2 = length;
            length = length2 - 1;
            if (length2 <= 0) {
                return value;
            }
            value = (value << 8) | (bytes[offset] & 255);
            offset += step;
        }
    }
}
