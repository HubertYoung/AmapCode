package defpackage;

/* renamed from: eur reason: default package */
/* compiled from: OggCrc */
public final class eur {
    private static int[] a = new int[256];

    static {
        for (int i = 0; i < a.length; i++) {
            int i2 = i << 24;
            for (int i3 = 0; i3 < 8; i3++) {
                i2 = (Integer.MIN_VALUE & i2) != 0 ? (i2 << 1) ^ 79764919 : i2 << 1;
            }
            a[i] = i2 & -1;
        }
    }

    public static int a(int i, byte[] bArr, int i2, int i3) {
        int i4 = i3 + i2;
        while (i2 < i4) {
            i = a[((i >>> 24) & 255) ^ (bArr[i2] & 255)] ^ (i << 8);
            i2++;
        }
        return i;
    }
}
