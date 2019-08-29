package com.alipay.android.phone.inside.cashier.enc;

public final class Base64 {
    private static final int BASELENGTH = 128;
    private static final int EIGHTBIT = 8;
    private static final int FOURBYTE = 4;
    private static final int LOOKUPLENGTH = 64;
    private static final char PAD = '=';
    private static final int SIGN = -128;
    private static final int SIXTEENBIT = 16;
    private static final int TWENTYFOURBITGROUP = 24;
    private static final byte[] base64Alphabet = new byte[128];
    private static final boolean fDebug = false;
    private static final char[] lookUpBase64Alphabet = new char[64];

    private static boolean isPad(char c) {
        return c == '=';
    }

    private static boolean isWhiteSpace(char c) {
        return c == ' ' || c == 13 || c == 10 || c == 9;
    }

    static {
        int i;
        int i2;
        int i3 = 0;
        for (int i4 = 0; i4 < 128; i4++) {
            base64Alphabet[i4] = -1;
        }
        for (int i5 = 90; i5 >= 65; i5--) {
            base64Alphabet[i5] = (byte) (i5 - 65);
        }
        int i6 = 122;
        while (true) {
            i = 26;
            if (i6 < 97) {
                break;
            }
            base64Alphabet[i6] = (byte) ((i6 - 97) + 26);
            i6--;
        }
        int i7 = 57;
        while (true) {
            i2 = 52;
            if (i7 < 48) {
                break;
            }
            base64Alphabet[i7] = (byte) ((i7 - 48) + 52);
            i7--;
        }
        base64Alphabet[43] = 62;
        base64Alphabet[47] = 63;
        for (int i8 = 0; i8 <= 25; i8++) {
            lookUpBase64Alphabet[i8] = (char) (i8 + 65);
        }
        int i9 = 0;
        while (i <= 51) {
            lookUpBase64Alphabet[i] = (char) (i9 + 97);
            i++;
            i9++;
        }
        while (i2 <= 61) {
            lookUpBase64Alphabet[i2] = (char) (i3 + 48);
            i2++;
            i3++;
        }
        lookUpBase64Alphabet[62] = '+';
        lookUpBase64Alphabet[63] = '/';
    }

    private static boolean isData(char c) {
        return c < 128 && base64Alphabet[c] != -1;
    }

    public static String encode(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        int length = bArr.length * 8;
        if (length == 0) {
            return "";
        }
        int i = length % 24;
        int i2 = length / 24;
        char[] cArr = new char[((i != 0 ? i2 + 1 : i2) * 4)];
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (i3 < i2) {
            int i6 = i4 + 1;
            byte b = bArr[i4];
            int i7 = i6 + 1;
            byte b2 = bArr[i6];
            int i8 = i7 + 1;
            byte b3 = bArr[i7];
            byte b4 = (byte) (b2 & 15);
            byte b5 = (byte) (b & 3);
            byte b6 = (byte) ((b2 & Byte.MIN_VALUE) == 0 ? b2 >> 4 : (b2 >> 4) ^ 240);
            byte b7 = (byte) ((b3 & Byte.MIN_VALUE) == 0 ? b3 >> 6 : (b3 >> 6) ^ 252);
            int i9 = i5 + 1;
            cArr[i5] = lookUpBase64Alphabet[(byte) ((b & Byte.MIN_VALUE) == 0 ? b >> 2 : (b >> 2) ^ 192)];
            int i10 = i9 + 1;
            cArr[i9] = lookUpBase64Alphabet[b6 | (b5 << 4)];
            int i11 = i10 + 1;
            cArr[i10] = lookUpBase64Alphabet[(b4 << 2) | b7];
            cArr[i11] = lookUpBase64Alphabet[b3 & 63];
            i3++;
            i5 = i11 + 1;
            i4 = i8;
        }
        if (i == 8) {
            byte b8 = bArr[i4];
            byte b9 = (byte) (b8 & 3);
            int i12 = i5 + 1;
            cArr[i5] = lookUpBase64Alphabet[(byte) ((b8 & Byte.MIN_VALUE) == 0 ? b8 >> 2 : (b8 >> 2) ^ 192)];
            int i13 = i12 + 1;
            cArr[i12] = lookUpBase64Alphabet[b9 << 4];
            cArr[i13] = PAD;
            cArr[i13 + 1] = PAD;
        } else if (i == 16) {
            byte b10 = bArr[i4];
            byte b11 = bArr[i4 + 1];
            byte b12 = (byte) (b11 & 15);
            byte b13 = (byte) (b10 & 3);
            byte b14 = (byte) ((b11 & Byte.MIN_VALUE) == 0 ? b11 >> 4 : (b11 >> 4) ^ 240);
            int i14 = i5 + 1;
            cArr[i5] = lookUpBase64Alphabet[(byte) ((b10 & Byte.MIN_VALUE) == 0 ? b10 >> 2 : (b10 >> 2) ^ 192)];
            int i15 = i14 + 1;
            cArr[i14] = lookUpBase64Alphabet[b14 | (b13 << 4)];
            cArr[i15] = lookUpBase64Alphabet[b12 << 2];
            cArr[i15 + 1] = PAD;
        }
        return new String(cArr);
    }

    public static byte[] decode(String str) {
        if (str == null) {
            return null;
        }
        char[] charArray = str.toCharArray();
        int removeWhiteSpace = removeWhiteSpace(charArray);
        if (removeWhiteSpace % 4 != 0) {
            return null;
        }
        int i = removeWhiteSpace / 4;
        if (i == 0) {
            return new byte[0];
        }
        byte[] bArr = new byte[(i * 3)];
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i2 < i - 1) {
            int i5 = i3 + 1;
            char c = charArray[i3];
            if (isData(c)) {
                int i6 = i5 + 1;
                char c2 = charArray[i5];
                if (isData(c2)) {
                    int i7 = i6 + 1;
                    char c3 = charArray[i6];
                    if (isData(c3)) {
                        int i8 = i7 + 1;
                        char c4 = charArray[i7];
                        if (isData(c4)) {
                            byte b = base64Alphabet[c];
                            byte b2 = base64Alphabet[c2];
                            byte b3 = base64Alphabet[c3];
                            byte b4 = base64Alphabet[c4];
                            int i9 = i4 + 1;
                            bArr[i4] = (byte) ((b << 2) | (b2 >> 4));
                            int i10 = i9 + 1;
                            bArr[i9] = (byte) (((b2 & 15) << 4) | ((b3 >> 2) & 15));
                            i4 = i10 + 1;
                            bArr[i10] = (byte) ((b3 << 6) | b4);
                            i2++;
                            i3 = i8;
                        }
                    }
                }
            }
            return null;
        }
        int i11 = i3 + 1;
        char c5 = charArray[i3];
        if (isData(c5)) {
            int i12 = i11 + 1;
            char c6 = charArray[i11];
            if (isData(c6)) {
                byte b5 = base64Alphabet[c5];
                byte b6 = base64Alphabet[c6];
                int i13 = i12 + 1;
                char c7 = charArray[i12];
                char c8 = charArray[i13];
                if (isData(c7) && isData(c8)) {
                    byte b7 = base64Alphabet[c7];
                    byte b8 = base64Alphabet[c8];
                    int i14 = i4 + 1;
                    bArr[i4] = (byte) ((b5 << 2) | (b6 >> 4));
                    bArr[i14] = (byte) (((b6 & 15) << 4) | ((b7 >> 2) & 15));
                    bArr[i14 + 1] = (byte) (b8 | (b7 << 6));
                    return bArr;
                } else if (!isPad(c7) || !isPad(c8)) {
                    if (isPad(c7) || !isPad(c8)) {
                        return null;
                    }
                    byte b9 = base64Alphabet[c7];
                    if ((b9 & 3) != 0) {
                        return null;
                    }
                    int i15 = i2 * 3;
                    byte[] bArr2 = new byte[(i15 + 2)];
                    System.arraycopy(bArr, 0, bArr2, 0, i15);
                    bArr2[i4] = (byte) ((b5 << 2) | (b6 >> 4));
                    bArr2[i4 + 1] = (byte) (((b9 >> 2) & 15) | ((b6 & 15) << 4));
                    return bArr2;
                } else if ((b6 & 15) != 0) {
                    return null;
                } else {
                    int i16 = i2 * 3;
                    byte[] bArr3 = new byte[(i16 + 1)];
                    System.arraycopy(bArr, 0, bArr3, 0, i16);
                    bArr3[i4] = (byte) ((b5 << 2) | (b6 >> 4));
                    return bArr3;
                }
            }
        }
        return null;
    }

    private static int removeWhiteSpace(char[] cArr) {
        if (cArr == null) {
            return 0;
        }
        int length = cArr.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (!isWhiteSpace(cArr[i2])) {
                cArr[i] = cArr[i2];
                i++;
            }
        }
        return i;
    }
}
