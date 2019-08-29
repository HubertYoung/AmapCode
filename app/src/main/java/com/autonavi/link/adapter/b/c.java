package com.autonavi.link.adapter.b;

/* compiled from: PacketUtil */
public class c {
    public static byte[] a(byte[] bArr, boolean z) {
        byte[] bArr2 = new byte[(bArr.length + 6)];
        byte b = 0;
        System.arraycopy(bArr, 0, bArr2, 6, bArr.length);
        int length = bArr.length + 2;
        bArr2[0] = (byte) (length & 255);
        bArr2[1] = (byte) ((length >>> 8) & 255);
        bArr2[2] = (byte) ((length >>> 16) & 255);
        bArr2[3] = (byte) ((length >>> 24) & 255);
        bArr2[5] = b.b;
        if (z) {
            b = a(bArr2);
        }
        bArr2[4] = b;
        return bArr2;
    }

    public static byte a(byte[] bArr) {
        byte b = 0;
        for (int i = 6; i < bArr.length; i++) {
            b = (byte) (b ^ bArr[i]);
        }
        return (byte) (bArr[5] ^ ((byte) (((byte) (((byte) (((byte) (bArr[0] ^ b)) ^ bArr[1])) ^ bArr[2])) ^ bArr[3])));
    }

    public static boolean b(byte[] bArr) {
        if (bArr == null || bArr.length < 6 || bArr[4] != a(bArr)) {
            return false;
        }
        return true;
    }
}
