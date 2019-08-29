package defpackage;

/* renamed from: amy reason: default package */
/* compiled from: GLConvertUtil */
public final class amy {
    public static int a(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << 24) + ((bArr[i + 2] & 255) << 16) + ((bArr[i + 1] & 255) << 8) + ((bArr[i + 0] & 255) << 0);
    }

    public static long b(byte[] bArr, int i) {
        return (((long) (bArr[i + 7] & 255)) << 56) + (((long) (bArr[i + 6] & 255)) << 48) + (((long) (bArr[i + 5] & 255)) << 40) + (((long) (bArr[i + 4] & 255)) << 32) + (((long) (bArr[i + 3] & 255)) << 24) + (((long) (bArr[i + 2] & 255)) << 16) + (((long) (bArr[i + 1] & 255)) << 8) + (((long) (bArr[i + 0] & 255)) << 0);
    }

    public static short c(byte[] bArr, int i) {
        return (short) (((bArr[i + 1] & 255) << 8) + ((bArr[i + 0] & 255) << 0));
    }

    public static String a(byte[] bArr, int i, int i2) {
        try {
            return new String(bArr, i, i2, "UTF-8");
        } catch (Exception unused) {
            return "";
        }
    }
}
