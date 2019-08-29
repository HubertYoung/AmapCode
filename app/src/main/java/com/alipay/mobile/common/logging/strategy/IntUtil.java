package com.alipay.mobile.common.logging.strategy;

public class IntUtil {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '+', '/'};

    public static long a(String num) {
        return b(num);
    }

    private static long b(String src) {
        int bit = (int) Math.pow(2.0d, 6.0d);
        long result = 0;
        int len = src.length();
        int num = len;
        for (int i = 0; i < len; i++) {
            result += ((long) Integer.parseInt(String.valueOf(c(src.substring(i, i + 1))))) * ((long) Math.pow((double) bit, (double) (num - 1)));
            num--;
        }
        return result;
    }

    private static int c(String single) {
        for (int n = 0; n < 64; n++) {
            if (single.equals(String.valueOf(a[n]))) {
                return n;
            }
        }
        return 0;
    }
}
