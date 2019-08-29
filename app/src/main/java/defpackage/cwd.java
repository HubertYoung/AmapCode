package defpackage;

/* renamed from: cwd reason: default package */
/* compiled from: ByteUtils */
public final class cwd {
    public static byte[] a(long j) {
        return new byte[]{(byte) ((int) j), (byte) ((int) (j >> 8)), (byte) ((int) (j >> 16)), (byte) ((int) (j >> 24)), (byte) ((int) (j >> 32)), (byte) ((int) (j >> 40)), (byte) ((int) (j >> 48)), (byte) ((int) (j >> 56))};
    }

    public static byte[] a(int i) {
        return new byte[]{(byte) i, (byte) (i >> 8), (byte) (i >> 16), (byte) (i >> 24)};
    }

    public static byte[] a(short s) {
        return new byte[]{(byte) s, (byte) (s >> 8)};
    }

    public static byte[] a(byte[]... bArr) {
        int i = 0;
        for (byte[] length : bArr) {
            i += length.length;
        }
        byte[] bArr2 = new byte[i];
        int i2 = 0;
        for (int i3 = 0; i3 < bArr.length; i3++) {
            System.arraycopy(bArr[i3], 0, bArr2, i2, bArr[i3].length);
            i2 += bArr[i3].length;
        }
        return bArr2;
    }
}
