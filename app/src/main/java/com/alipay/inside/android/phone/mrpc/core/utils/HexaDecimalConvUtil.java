package com.alipay.inside.android.phone.mrpc.core.utils;

public final class HexaDecimalConvUtil {
    private static final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '+', '/'};

    public static final String c10to64(long j) {
        return toUnsignedString(j, 6);
    }

    private static final String toUnsignedString(long j, int i) {
        int pow = (int) Math.pow(2.0d, (double) i);
        char[] cArr = new char[pow];
        long j2 = (long) ((1 << i) - 1);
        long j3 = j;
        int i2 = pow;
        do {
            i2--;
            cArr[i2] = digits[(int) (j3 & j2)];
            j3 >>>= i;
        } while (j3 != 0);
        return new String(cArr, i2, pow - i2);
    }
}
