package defpackage;

/* renamed from: euq reason: default package */
/* compiled from: AudioFileWriter */
public abstract class euq {
    public static byte[] a(int i, long j, int i2, int i3, int i4, byte[] bArr) {
        byte[] bArr2 = new byte[(i4 + 27)];
        a(bArr2, 0, (String) "OggS");
        bArr2[4] = 0;
        bArr2[5] = (byte) i;
        bArr2[6] = (byte) ((int) (j & 255));
        bArr2[7] = (byte) ((int) ((j >>> 8) & 255));
        bArr2[8] = (byte) ((int) ((j >>> 16) & 255));
        bArr2[9] = (byte) ((int) ((j >>> 24) & 255));
        bArr2[10] = (byte) ((int) ((j >>> 32) & 255));
        bArr2[11] = (byte) ((int) ((j >>> 40) & 255));
        bArr2[12] = (byte) ((int) ((j >>> 48) & 255));
        bArr2[13] = (byte) ((int) ((j >>> 56) & 255));
        a(bArr2, 14, i2);
        a(bArr2, 18, i3);
        a(bArr2, 22, 0);
        bArr2[26] = (byte) i4;
        System.arraycopy(bArr, 0, bArr2, 27, i4);
        return bArr2;
    }

    public static void a(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) (i2 & 255);
        bArr[i + 1] = (byte) ((i2 >>> 8) & 255);
        bArr[i + 2] = (byte) ((i2 >>> 16) & 255);
        bArr[i + 3] = (byte) ((i2 >>> 24) & 255);
    }

    public static void a(byte[] bArr, int i, String str) {
        byte[] bytes = str.getBytes();
        System.arraycopy(bytes, 0, bArr, i, bytes.length);
    }
}
