package com.alipay.mobile.quinox.utils.bytedata;

public final class ByteOrderValues {
    public static final int BIG_ENDIAN = 1;
    public static final int LITTLE_ENDIAN = 2;

    private ByteOrderValues() {
    }

    public static boolean getBoolean(byte[] bArr) {
        return bArr[0] > 0;
    }

    public static void putBoolean(boolean z, byte[] bArr) {
        bArr[0] = z ? (byte) 1 : 0;
    }

    public static byte getByte(byte[] bArr) {
        return getByte(bArr, 1);
    }

    public static byte getByte(byte[] bArr, int i) {
        return bArr[0];
    }

    public static void putByte(byte b, byte[] bArr) {
        putByte(b, bArr, 1);
    }

    public static void putByte(byte b, byte[] bArr, int i) {
        bArr[0] = b;
    }

    public static short getShort(byte[] bArr) {
        return getShort(bArr, 1);
    }

    public static short getShort(byte[] bArr, int i) {
        if (i == 1) {
            return (short) ((bArr[1] & 255) | ((bArr[0] & 255) << 8));
        }
        return (short) ((bArr[0] & 255) | ((bArr[1] & 255) << 8));
    }

    public static void putShort(short s, byte[] bArr) {
        putShort(s, bArr, 1);
    }

    public static void putShort(short s, byte[] bArr, int i) {
        if (i == 1) {
            bArr[0] = (byte) (s >> 8);
            bArr[1] = (byte) s;
            return;
        }
        bArr[0] = (byte) s;
        bArr[1] = (byte) (s >> 8);
    }

    public static int getInt(byte[] bArr) {
        return getInt(bArr, 1);
    }

    public static int getInt(byte[] bArr, int i) {
        if (i == 1) {
            return (bArr[3] & 255) | ((bArr[0] & 255) << 24) | ((bArr[1] & 255) << 16) | ((bArr[2] & 255) << 8);
        }
        return (bArr[0] & 255) | ((bArr[3] & 255) << 24) | ((bArr[2] & 255) << 16) | ((bArr[1] & 255) << 8);
    }

    public static void putInt(int i, byte[] bArr) {
        putInt(i, bArr, 1);
    }

    public static void putInt(int i, byte[] bArr, int i2) {
        if (i2 == 1) {
            bArr[0] = (byte) (i >> 24);
            bArr[1] = (byte) (i >> 16);
            bArr[2] = (byte) (i >> 8);
            bArr[3] = (byte) i;
            return;
        }
        bArr[0] = (byte) i;
        bArr[1] = (byte) (i >> 8);
        bArr[2] = (byte) (i >> 16);
        bArr[3] = (byte) (i >> 24);
    }

    public static float getFloat(byte[] bArr) {
        return getFloat(bArr, 1);
    }

    public static float getFloat(byte[] bArr, int i) {
        return Float.intBitsToFloat(getInt(bArr, i));
    }

    public static void putFloat(float f, byte[] bArr) {
        putFloat(f, bArr, 1);
    }

    public static void putFloat(float f, byte[] bArr, int i) {
        putInt(Float.floatToIntBits(f), bArr, i);
    }

    public static void putLong(long j, byte[] bArr) {
        putLong(j, bArr, 1);
    }

    public static void putLong(long j, byte[] bArr, int i) {
        long j2 = j;
        if (i == 1) {
            bArr[0] = (byte) ((int) (j2 >> 56));
            bArr[1] = (byte) ((int) (j2 >> 48));
            bArr[2] = (byte) ((int) (j2 >> 40));
            bArr[3] = (byte) ((int) (j2 >> 32));
            bArr[4] = (byte) ((int) (j2 >> 24));
            bArr[5] = (byte) ((int) (j2 >> 16));
            bArr[6] = (byte) ((int) (j2 >> 8));
            bArr[7] = (byte) ((int) j2);
            return;
        }
        bArr[0] = (byte) ((int) j2);
        bArr[1] = (byte) ((int) (j2 >> 8));
        bArr[2] = (byte) ((int) (j2 >> 16));
        bArr[3] = (byte) ((int) (j2 >> 24));
        bArr[4] = (byte) ((int) (j2 >> 32));
        bArr[5] = (byte) ((int) (j2 >> 40));
        bArr[6] = (byte) ((int) (j2 >> 48));
        bArr[7] = (byte) ((int) (j2 >> 56));
    }

    public static long getLong(byte[] bArr) {
        return getLong(bArr, 1);
    }

    public static long getLong(byte[] bArr, int i) {
        if (i == 1) {
            return (((long) (bArr[0] & 255)) << 56) | (((long) (bArr[1] & 255)) << 48) | (((long) (bArr[2] & 255)) << 40) | (((long) (bArr[3] & 255)) << 32) | (((long) (bArr[4] & 255)) << 24) | (((long) (bArr[5] & 255)) << 16) | (((long) (bArr[6] & 255)) << 8) | ((long) (bArr[7] & 255));
        }
        return (((long) (bArr[7] & 255)) << 56) | (((long) (bArr[6] & 255)) << 48) | (((long) (bArr[5] & 255)) << 40) | (((long) (bArr[4] & 255)) << 32) | (((long) (bArr[3] & 255)) << 24) | (((long) (bArr[2] & 255)) << 16) | (((long) (bArr[1] & 255)) << 8) | ((long) (bArr[0] & 255));
    }

    public static double getDouble(byte[] bArr) {
        return getDouble(bArr, 1);
    }

    public static double getDouble(byte[] bArr, int i) {
        return Double.longBitsToDouble(getLong(bArr, i));
    }

    public static void putDouble(double d, byte[] bArr) {
        putDouble(d, bArr, 1);
    }

    public static void putDouble(double d, byte[] bArr, int i) {
        putLong(Double.doubleToLongBits(d), bArr, i);
    }
}
