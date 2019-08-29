package com.autonavi.jni.server.aos;

public class HexUtil {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'a', 'b', 'c', 'd', 'e', 'f'};

    private HexUtil() {
    }

    public static String toHex(byte[] bArr) {
        return toHex(bArr, false);
    }

    public static String toHex(byte[] bArr, boolean z) {
        if (bArr == null) {
            return null;
        }
        char[] cArr = new char[(bArr.length + bArr.length)];
        int length = bArr.length - 1;
        int length2 = cArr.length - 1;
        while (length >= 0) {
            byte b = bArr[length];
            int i = b & 15;
            if (z && i >= 10) {
                i += 6;
            }
            int i2 = length2 - 1;
            cArr[length2] = HEX_DIGITS[i];
            int i3 = (b >>> 4) & 15;
            if (z && i3 >= 10) {
                i3 += 6;
            }
            cArr[i2] = HEX_DIGITS[i3];
            length--;
            length2 = i2 - 1;
        }
        return new String(cArr);
    }

    public static byte[] toBytes(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        int length = str.length();
        if (length % 2 != 0) {
            str = "0".concat(String.valueOf(str));
            length++;
        }
        char[] charArray = str.toCharArray();
        byte[] bArr = new byte[(length >> 1)];
        for (int i = 0; i < length; i += 2) {
            bArr[i >> 1] = (byte) ((hex2Dec(charArray[i]) << 4) | hex2Dec(charArray[i + 1]));
        }
        return bArr;
    }

    public static int hex2Dec(char c) throws IllegalArgumentException {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'A' && c <= 'F') {
            return (c - 'A') + 10;
        }
        if (c >= 'a' && c <= 'f') {
            return (c - 'a') + 10;
        }
        throw new IllegalArgumentException();
    }
}
