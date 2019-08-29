package com.alipay.sdk.encrypt;

public final class a {
    private static final int a = 128;
    private static final int b = 64;
    private static final int c = 24;
    private static final int d = 8;
    private static final int e = 16;
    private static final int f = 4;
    private static final int g = -128;
    private static final char h = '=';
    private static final byte[] i = new byte[128];
    private static final char[] j = new char[64];

    private static boolean a(char c2) {
        return c2 == ' ' || c2 == 13 || c2 == 10 || c2 == 9;
    }

    private static boolean b(char c2) {
        return c2 == '=';
    }

    static {
        int i2;
        int i3;
        int i4 = 0;
        for (int i5 = 0; i5 < 128; i5++) {
            i[i5] = -1;
        }
        for (int i6 = 90; i6 >= 65; i6--) {
            i[i6] = (byte) (i6 - 65);
        }
        int i7 = 122;
        while (true) {
            i2 = 26;
            if (i7 < 97) {
                break;
            }
            i[i7] = (byte) ((i7 - 97) + 26);
            i7--;
        }
        int i8 = 57;
        while (true) {
            i3 = 52;
            if (i8 < 48) {
                break;
            }
            i[i8] = (byte) ((i8 - 48) + 52);
            i8--;
        }
        i[43] = 62;
        i[47] = 63;
        for (int i9 = 0; i9 <= 25; i9++) {
            j[i9] = (char) (i9 + 65);
        }
        int i10 = 0;
        while (i2 <= 51) {
            j[i2] = (char) (i10 + 97);
            i2++;
            i10++;
        }
        while (i3 <= 61) {
            j[i3] = (char) (i4 + 48);
            i3++;
            i4++;
        }
        j[62] = '+';
        j[63] = '/';
    }

    private static boolean c(char c2) {
        return c2 < 128 && i[c2] != -1;
    }

    public static String a(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        int length = bArr.length * 8;
        if (length == 0) {
            return "";
        }
        int i2 = length % 24;
        int i3 = length / 24;
        char[] cArr = new char[((i2 != 0 ? i3 + 1 : i3) * 4)];
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (i4 < i3) {
            int i7 = i5 + 1;
            byte b2 = bArr[i5];
            int i8 = i7 + 1;
            byte b3 = bArr[i7];
            int i9 = i8 + 1;
            byte b4 = bArr[i8];
            byte b5 = (byte) (b3 & 15);
            byte b6 = (byte) (b2 & 3);
            byte b7 = (byte) ((b3 & Byte.MIN_VALUE) == 0 ? b3 >> 4 : (b3 >> 4) ^ 240);
            byte b8 = (byte) ((b4 & Byte.MIN_VALUE) == 0 ? b4 >> 6 : (b4 >> 6) ^ 252);
            int i10 = i6 + 1;
            cArr[i6] = j[(byte) ((b2 & Byte.MIN_VALUE) == 0 ? b2 >> 2 : (b2 >> 2) ^ 192)];
            int i11 = i10 + 1;
            cArr[i10] = j[b7 | (b6 << 4)];
            int i12 = i11 + 1;
            cArr[i11] = j[(b5 << 2) | b8];
            cArr[i12] = j[b4 & 63];
            i4++;
            i6 = i12 + 1;
            i5 = i9;
        }
        if (i2 == 8) {
            byte b9 = bArr[i5];
            byte b10 = (byte) (b9 & 3);
            int i13 = i6 + 1;
            cArr[i6] = j[(byte) ((b9 & Byte.MIN_VALUE) == 0 ? b9 >> 2 : (b9 >> 2) ^ 192)];
            int i14 = i13 + 1;
            cArr[i13] = j[b10 << 4];
            cArr[i14] = h;
            cArr[i14 + 1] = h;
        } else if (i2 == 16) {
            byte b11 = bArr[i5];
            byte b12 = bArr[i5 + 1];
            byte b13 = (byte) (b12 & 15);
            byte b14 = (byte) (b11 & 3);
            byte b15 = (byte) ((b12 & Byte.MIN_VALUE) == 0 ? b12 >> 4 : (b12 >> 4) ^ 240);
            int i15 = i6 + 1;
            cArr[i6] = j[(byte) ((b11 & Byte.MIN_VALUE) == 0 ? b11 >> 2 : (b11 >> 2) ^ 192)];
            int i16 = i15 + 1;
            cArr[i15] = j[b15 | (b14 << 4)];
            cArr[i16] = j[b13 << 2];
            cArr[i16 + 1] = h;
        }
        return new String(cArr);
    }

    public static byte[] a(String str) {
        int i2;
        if (str == null) {
            return null;
        }
        char[] charArray = str.toCharArray();
        if (charArray == null) {
            i2 = 0;
        } else {
            int length = charArray.length;
            i2 = 0;
            for (int i3 = 0; i3 < length; i3++) {
                char c2 = charArray[i3];
                if (!(c2 == ' ' || c2 == 13 || c2 == 10 || c2 == 9)) {
                    charArray[i2] = charArray[i3];
                    i2++;
                }
            }
        }
        if (i2 % 4 != 0) {
            return null;
        }
        int i4 = i2 / 4;
        if (i4 == 0) {
            return new byte[0];
        }
        byte[] bArr = new byte[(i4 * 3)];
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        while (i5 < i4 - 1) {
            int i8 = i6 + 1;
            char c3 = charArray[i6];
            if (c(c3)) {
                int i9 = i8 + 1;
                char c4 = charArray[i8];
                if (c(c4)) {
                    int i10 = i9 + 1;
                    char c5 = charArray[i9];
                    if (c(c5)) {
                        int i11 = i10 + 1;
                        char c6 = charArray[i10];
                        if (c(c6)) {
                            byte b2 = i[c3];
                            byte b3 = i[c4];
                            byte b4 = i[c5];
                            byte b5 = i[c6];
                            int i12 = i7 + 1;
                            bArr[i7] = (byte) ((b2 << 2) | (b3 >> 4));
                            int i13 = i12 + 1;
                            bArr[i12] = (byte) (((b3 & 15) << 4) | ((b4 >> 2) & 15));
                            i7 = i13 + 1;
                            bArr[i13] = (byte) ((b4 << 6) | b5);
                            i5++;
                            i6 = i11;
                        }
                    }
                }
            }
            return null;
        }
        int i14 = i6 + 1;
        char c7 = charArray[i6];
        if (c(c7)) {
            int i15 = i14 + 1;
            char c8 = charArray[i14];
            if (c(c8)) {
                byte b6 = i[c7];
                byte b7 = i[c8];
                int i16 = i15 + 1;
                char c9 = charArray[i15];
                char c10 = charArray[i16];
                if (c(c9) && c(c10)) {
                    byte b8 = i[c9];
                    byte b9 = i[c10];
                    int i17 = i7 + 1;
                    bArr[i7] = (byte) ((b6 << 2) | (b7 >> 4));
                    bArr[i17] = (byte) (((b7 & 15) << 4) | ((b8 >> 2) & 15));
                    bArr[i17 + 1] = (byte) (b9 | (b8 << 6));
                    return bArr;
                } else if (!b(c9) || !b(c10)) {
                    if (b(c9) || !b(c10)) {
                        return null;
                    }
                    byte b10 = i[c9];
                    if ((b10 & 3) != 0) {
                        return null;
                    }
                    int i18 = i5 * 3;
                    byte[] bArr2 = new byte[(i18 + 2)];
                    System.arraycopy(bArr, 0, bArr2, 0, i18);
                    bArr2[i7] = (byte) ((b6 << 2) | (b7 >> 4));
                    bArr2[i7 + 1] = (byte) (((b10 >> 2) & 15) | ((b7 & 15) << 4));
                    return bArr2;
                } else if ((b7 & 15) != 0) {
                    return null;
                } else {
                    int i19 = i5 * 3;
                    byte[] bArr3 = new byte[(i19 + 1)];
                    System.arraycopy(bArr, 0, bArr3, 0, i19);
                    bArr3[i7] = (byte) ((b6 << 2) | (b7 >> 4));
                    return bArr3;
                }
            }
        }
        return null;
    }

    private static int a(char[] cArr) {
        if (cArr == null) {
            return 0;
        }
        int length = cArr.length;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            char c2 = cArr[i3];
            if (!(c2 == ' ' || c2 == 13 || c2 == 10 || c2 == 9)) {
                cArr[i2] = cArr[i3];
                i2++;
            }
        }
        return i2;
    }
}
