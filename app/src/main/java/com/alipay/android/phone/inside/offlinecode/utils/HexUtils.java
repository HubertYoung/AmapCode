package com.alipay.android.phone.inside.offlinecode.utils;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class HexUtils {
    public static final String HEX_CHARS = "0123456789ABCDEF";

    private static String format(String str) {
        return str.toUpperCase();
    }

    public static String toHexString(int i) {
        String hexString = Integer.toHexString(i);
        if ((hexString.length() & 1) == 1) {
            hexString = "0".concat(String.valueOf(hexString));
        }
        return format(hexString);
    }

    public static String toHexString(int i, int i2) {
        String hexString = Integer.toHexString(i);
        if (hexString.length() >= i2) {
            return format(hexString);
        }
        char[] cArr = new char[(i2 - hexString.length())];
        Arrays.fill(cArr, '0');
        StringBuilder sb = new StringBuilder();
        sb.append(new String(cArr));
        sb.append(hexString);
        return format(sb.toString());
    }

    public static String toHexString(byte[] bArr) {
        String str = "";
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() == 1) {
                hexString = "0".concat(String.valueOf(hexString));
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(hexString);
            str = sb.toString();
        }
        return format(str);
    }

    public static byte[] parse(String str, boolean z) {
        int length = str.length() / 2;
        if (z) {
            str = str.toUpperCase();
        }
        char[] charArray = str.toCharArray();
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) (charToByte(charArray[i2 + 1]) | (charToByte(charArray[i2]) << 4));
        }
        return bArr;
    }

    public static byte[] parse(String str) {
        return parse(str, true);
    }

    public static int bytes2Int(byte[] bArr) {
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        if (bArr.length >= 4) {
            return wrap.getInt();
        }
        return wrap.getShort();
    }

    private static byte charToByte(char c) {
        return (byte) HEX_CHARS.indexOf(c);
    }
}
