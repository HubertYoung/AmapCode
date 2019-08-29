package com.xiaomi.channel.commonutils.string;

public class a {
    private static final String a = System.getProperty("line.separator");
    private static char[] b = new char[64];
    private static byte[] c = new byte[128];

    static {
        char c2 = 'A';
        int i = 0;
        while (c2 <= 'Z') {
            b[i] = c2;
            c2 = (char) (c2 + 1);
            i++;
        }
        char c3 = 'a';
        while (c3 <= 'z') {
            b[i] = c3;
            c3 = (char) (c3 + 1);
            i++;
        }
        char c4 = '0';
        while (c4 <= '9') {
            b[i] = c4;
            c4 = (char) (c4 + 1);
            i++;
        }
        b[i] = '+';
        b[i + 1] = '/';
        for (int i2 = 0; i2 < c.length; i2++) {
            c[i2] = -1;
        }
        for (int i3 = 0; i3 < 64; i3++) {
            c[b[i3]] = (byte) i3;
        }
    }

    public static byte[] a(String str) {
        return a(str.toCharArray());
    }

    public static byte[] a(char[] cArr) {
        return a(cArr, 0, cArr.length);
    }

    public static byte[] a(char[] cArr, int i, int i2) {
        int i3;
        char c2;
        int i4;
        if (i2 % 4 != 0) {
            throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
        }
        while (i2 > 0 && cArr[(i + i2) - 1] == '=') {
            i2--;
        }
        int i5 = (i2 * 3) / 4;
        byte[] bArr = new byte[i5];
        int i6 = i2 + i;
        int i7 = 0;
        while (i < i6) {
            int i8 = i + 1;
            char c3 = cArr[i];
            int i9 = i8 + 1;
            char c4 = cArr[i8];
            char c5 = 'A';
            if (i9 < i6) {
                i3 = i9 + 1;
                c2 = cArr[i9];
            } else {
                i3 = i9;
                c2 = 'A';
            }
            if (i3 < i6) {
                char c6 = cArr[i3];
                i3++;
                c5 = c6;
            }
            if (c3 > 127 || c4 > 127 || c2 > 127 || c5 > 127) {
                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            }
            byte b2 = c[c3];
            byte b3 = c[c4];
            byte b4 = c[c2];
            byte b5 = c[c5];
            if (b2 < 0 || b3 < 0 || b4 < 0 || b5 < 0) {
                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            }
            int i10 = (b2 << 2) | (b3 >>> 4);
            int i11 = ((b3 & 15) << 4) | (b4 >>> 2);
            byte b6 = ((b4 & 3) << 6) | b5;
            int i12 = i7 + 1;
            bArr[i7] = (byte) i10;
            if (i12 < i5) {
                i4 = i12 + 1;
                bArr[i12] = (byte) i11;
            } else {
                i4 = i12;
            }
            if (i4 < i5) {
                i7 = i4 + 1;
                bArr[i4] = (byte) b6;
            } else {
                i7 = i4;
            }
            i = i3;
        }
        return bArr;
    }

    public static char[] a(byte[] bArr) {
        return a(bArr, 0, bArr.length);
    }

    public static char[] a(byte[] bArr, int i, int i2) {
        int i3;
        byte b2;
        int i4;
        byte b3;
        int i5 = ((i2 * 4) + 2) / 3;
        char[] cArr = new char[(((i2 + 2) / 3) * 4)];
        int i6 = i2 + i;
        int i7 = 0;
        while (i < i6) {
            int i8 = i + 1;
            byte b4 = bArr[i] & 255;
            if (i8 < i6) {
                i3 = i8 + 1;
                b2 = bArr[i8] & 255;
            } else {
                i3 = i8;
                b2 = 0;
            }
            if (i3 < i6) {
                i4 = i3 + 1;
                b3 = bArr[i3] & 255;
            } else {
                i4 = i3;
                b3 = 0;
            }
            int i9 = b4 >>> 2;
            int i10 = ((b4 & 3) << 4) | (b2 >>> 4);
            int i11 = ((b2 & 15) << 2) | (b3 >>> 6);
            byte b5 = b3 & 63;
            int i12 = i7 + 1;
            cArr[i7] = b[i9];
            int i13 = i12 + 1;
            cArr[i12] = b[i10];
            char c2 = '=';
            cArr[i13] = i13 < i5 ? b[i11] : '=';
            int i14 = i13 + 1;
            if (i14 < i5) {
                c2 = b[b5];
            }
            cArr[i14] = c2;
            i7 = i14 + 1;
            i = i4;
        }
        return cArr;
    }
}
