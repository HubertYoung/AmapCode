package com.alipay.mobile.common.logging.util;

public class MathUtil {
    public static byte[] longToByteLittleEndian(long number) {
        long tmp = number;
        byte[] array = new byte[8];
        for (int i = 0; i < 8; i++) {
            array[i] = (byte) ((int) (255 & tmp));
            tmp >>= 8;
        }
        return array;
    }

    public static byte[] longToByteBigEndian(long number) {
        long tmp = number;
        byte[] array = new byte[8];
        for (int i = 7; i >= 0; i--) {
            array[i] = (byte) ((int) (255 & tmp));
            tmp >>= 8;
        }
        return array;
    }

    public static byte[] intToByteLittleEndian(int number) {
        int tmp = number;
        byte[] array = new byte[4];
        for (int i = 0; i < 4; i++) {
            array[i] = (byte) (tmp & 255);
            tmp >>= 8;
        }
        return array;
    }

    public static byte[] intToByteBigEndian(int number) {
        int tmp = number;
        byte[] array = new byte[4];
        for (int i = 3; i >= 0; i--) {
            array[i] = (byte) (tmp & 255);
            tmp >>= 8;
        }
        return array;
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=short, code=int, for r5v0, types: [short, int] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] shortToByteLittleEndian(int r5) {
        /*
            r4 = 2
            r2 = r5
            byte[] r0 = new byte[r4]
            r1 = 0
        L_0x0005:
            if (r1 >= r4) goto L_0x0011
            r3 = r2 & 255(0xff, float:3.57E-43)
            byte r3 = (byte) r3
            r0[r1] = r3
            int r2 = r2 >> 8
            int r1 = r1 + 1
            goto L_0x0005
        L_0x0011:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.logging.util.MathUtil.shortToByteLittleEndian(short):byte[]");
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=short, code=int, for r4v0, types: [short, int] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] shortToByteBigEndian(int r4) {
        /*
            r2 = r4
            r3 = 2
            byte[] r0 = new byte[r3]
            r1 = 1
        L_0x0005:
            if (r1 < 0) goto L_0x0011
            r3 = r2 & 255(0xff, float:3.57E-43)
            byte r3 = (byte) r3
            r0[r1] = r3
            int r2 = r2 >> 8
            int r1 = r1 + -1
            goto L_0x0005
        L_0x0011:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.logging.util.MathUtil.shortToByteBigEndian(short):byte[]");
    }
}
