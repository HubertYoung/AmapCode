package defpackage;

import java.util.Locale;

/* renamed from: ahg reason: default package */
/* compiled from: ConvertUtil */
public final class ahg {
    public static byte[] a(byte b) {
        return new byte[]{b};
    }

    public static byte[] a(int i) {
        return new byte[]{(byte) (i & 255), (byte) ((i >> 8) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 24) & 255)};
    }

    public static final String a(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer(bArr.length);
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() < 2) {
                stringBuffer.append(0);
            }
            stringBuffer.append(hexString.toUpperCase(Locale.getDefault()));
        }
        return stringBuffer.toString();
    }

    public static int a(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << 24) + ((bArr[i + 2] & 255) << 16) + ((bArr[i + 1] & 255) << 8) + ((bArr[i + 0] & 255) << 0);
    }

    public static short b(byte[] bArr, int i) {
        return (short) (((bArr[i + 1] & 255) << 8) + ((bArr[i + 0] & 255) << 0));
    }

    public static byte[] b(int i) {
        return new byte[]{(byte) (i & 255), (byte) ((i >> 8) & 255)};
    }

    public static double c(byte[] bArr, int i) {
        long j = 0;
        for (int i2 = 0; i2 < 8; i2++) {
            j += ((long) (bArr[i2 + i] & 255)) << (i2 * 8);
        }
        return Double.longBitsToDouble(j);
    }

    public static long b(byte[] bArr) {
        return (((long) (bArr[7] & 255)) << 56) | ((long) (bArr[0] & 255)) | (((long) (bArr[1] & 255)) << 8) | (((long) (bArr[2] & 255)) << 16) | (((long) (bArr[3] & 255)) << 24) | (((long) (bArr[4] & 255)) << 32) | (((long) (bArr[5] & 255)) << 40) | (((long) (bArr[6] & 255)) << 48);
    }
}
