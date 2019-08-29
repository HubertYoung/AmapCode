package com.alipay.mobile.common.utils;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.nio.charset.Charset;

public class HexStringUtil {
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    public static final String DEFAULT_CHARSET_NAME = "UTF-8";
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private final Charset c;

    public static byte[] decodeHex(char[] data) {
        int len = data.length;
        if ((len & 1) != 0) {
            throw new Exception("Odd number of characters.");
        }
        byte[] out = new byte[(len >> 1)];
        int i = 0;
        int j = 0;
        while (j < len) {
            int j2 = j + 1;
            j = j2 + 1;
            out[i] = (byte) (((toDigit(data[j], j) << 4) | toDigit(data[j2], j2)) & 255);
            i++;
        }
        return out;
    }

    public static char[] encodeHex(byte[] data) {
        return encodeHex(data, true);
    }

    public static char[] encodeHex(byte[] data, boolean toLowerCase) {
        return encodeHex(data, toLowerCase ? a : b);
    }

    protected static char[] encodeHex(byte[] data, char[] toDigits) {
        int l = data.length;
        char[] out = new char[(l << 1)];
        int j = 0;
        for (int i = 0; i < l; i++) {
            int j2 = j + 1;
            out[j] = toDigits[(data[i] & 240) >>> 4];
            j = j2 + 1;
            out[j2] = toDigits[data[i] & 15];
        }
        return out;
    }

    public static String bytesToHexString(byte[] data) {
        return new String(encodeHex(data));
    }

    protected static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit != -1) {
            return digit;
        }
        throw new Exception("Illegal hexadecimal character " + ch + " at index " + index);
    }

    public HexStringUtil() {
        this.c = DEFAULT_CHARSET;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public HexStringUtil(Charset charset) {
        this.c = charset;
    }

    public HexStringUtil(String charsetName) {
        this(Charset.forName(charsetName));
    }

    public byte[] decode(byte[] array) {
        return decodeHex(new String(array, getCharsetName()).toCharArray());
    }

    public Object decode(Object object) {
        try {
            return decodeHex(object instanceof String ? ((String) object).toCharArray() : (char[]) object);
        } catch (ClassCastException e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    public byte[] encode(byte[] array) {
        return bytesToHexString(array).getBytes(getCharsetName());
    }

    public Object encode(Object object) {
        try {
            return encodeHex(object instanceof String ? ((String) object).getBytes(getCharsetName()) : (byte[]) object);
        } catch (ClassCastException e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    public Charset getCharset() {
        return this.c;
    }

    public String getCharsetName() {
        return this.c.name();
    }

    public String toString() {
        return super.toString() + "[charsetName=" + this.c + "]";
    }
}
