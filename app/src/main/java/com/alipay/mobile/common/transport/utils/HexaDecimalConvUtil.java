package com.alipay.mobile.common.transport.utils;

public final class HexaDecimalConvUtil {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '+', '/'};

    public static final String c10to64(long num) {
        return a(num);
    }

    public static final long c64to10(String num) {
        return a(num);
    }

    private static final String a(long i) {
        int bit = (int) Math.pow(2.0d, 6.0d);
        char[] buf = new char[bit];
        int charPos = bit;
        do {
            charPos--;
            buf[charPos] = a[(int) (63 & i)];
            i >>>= 6;
        } while (i != 0);
        return new String(buf, charPos, bit - charPos);
    }

    private static final long a(String src) {
        int bit = (int) Math.pow(2.0d, 6.0d);
        long result = 0;
        int len = src.length();
        int num = len;
        for (int i = 0; i < len; i++) {
            result += ((long) Integer.parseInt(String.valueOf(getNum(src.substring(i, i + 1))))) * ((long) Math.pow((double) bit, (double) (num - 1)));
            num--;
        }
        return result;
    }

    public static final int getNum(String single) {
        for (int n = 0; n < 64; n++) {
            if (single.equals(String.valueOf(a[n]))) {
                return n;
            }
        }
        return 0;
    }
}
