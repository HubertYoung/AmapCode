package com.alipay.mobile.monitor.track;

public class IntUtil {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '+', '/'};

    public static String a(long num) {
        return a(num, 6);
    }

    private static String a(long i, int shift) {
        int bit = (int) Math.pow(2.0d, (double) shift);
        char[] buf = new char[bit];
        int charPos = bit;
        long mask = (long) ((1 << shift) - 1);
        do {
            charPos--;
            buf[charPos] = a[(int) (i & mask)];
            i >>>= shift;
        } while (i != 0);
        return new String(buf, charPos, bit - charPos);
    }
}
