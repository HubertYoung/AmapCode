package com.xiaomi.smack.util;

import com.j256.ormlite.stmt.query.SimpleComparison;
import com.xiaomi.channel.commonutils.string.a;
import java.util.Random;

public class d {
    private static final char[] a = "&quot;".toCharArray();
    private static final char[] b = "&apos;".toCharArray();
    private static final char[] c = "&amp;".toCharArray();
    private static final char[] d = "&lt;".toCharArray();
    private static final char[] e = "&gt;".toCharArray();
    private static Random f = new Random();
    private static char[] g = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public static String a(int i) {
        if (i <= 0) {
            return null;
        }
        char[] cArr = new char[i];
        for (int i2 = 0; i2 < i; i2++) {
            cArr[i2] = g[f.nextInt(71)];
        }
        return new String(cArr);
    }

    public static String a(String str) {
        char[] cArr;
        if (str == null) {
            return null;
        }
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        StringBuilder sb = new StringBuilder((int) (((double) length) * 1.3d));
        int i = 0;
        int i2 = 0;
        while (i < length) {
            char c2 = charArray[i];
            if (c2 <= '>') {
                if (c2 == '<') {
                    if (i > i2) {
                        sb.append(charArray, i2, i - i2);
                    }
                    i2 = i + 1;
                    cArr = d;
                } else if (c2 == '>') {
                    if (i > i2) {
                        sb.append(charArray, i2, i - i2);
                    }
                    i2 = i + 1;
                    cArr = e;
                } else if (c2 == '&') {
                    if (i > i2) {
                        sb.append(charArray, i2, i - i2);
                    }
                    int i3 = i + 5;
                    if (length <= i3 || charArray[i + 1] != '#' || !Character.isDigit(charArray[i + 2]) || !Character.isDigit(charArray[i + 3]) || !Character.isDigit(charArray[i + 4]) || charArray[i3] != ';') {
                        i2 = i + 1;
                        cArr = c;
                    }
                } else if (c2 == '\"') {
                    if (i > i2) {
                        sb.append(charArray, i2, i - i2);
                    }
                    i2 = i + 1;
                    cArr = a;
                } else if (c2 == '\'') {
                    if (i > i2) {
                        sb.append(charArray, i2, i - i2);
                    }
                    i2 = i + 1;
                    cArr = b;
                }
                sb.append(cArr);
            }
            i++;
        }
        if (i2 == 0) {
            return str;
        }
        if (i > i2) {
            sb.append(charArray, i2, i - i2);
        }
        return sb.toString();
    }

    public static final String a(String str, String str2, String str3) {
        if (str == null) {
            return null;
        }
        int indexOf = str.indexOf(str2, 0);
        if (indexOf >= 0) {
            char[] charArray = str.toCharArray();
            char[] charArray2 = str3.toCharArray();
            int length = str2.length();
            StringBuilder sb = new StringBuilder(charArray.length);
            sb.append(charArray, 0, indexOf);
            sb.append(charArray2);
            int i = indexOf + length;
            while (true) {
                int indexOf2 = str.indexOf(str2, i);
                if (indexOf2 <= 0) {
                    break;
                }
                sb.append(charArray, i, indexOf2 - i);
                sb.append(charArray2);
                i = indexOf2 + length;
            }
            sb.append(charArray, i, charArray.length - i);
            str = sb.toString();
        }
        return str;
    }

    public static String a(byte[] bArr) {
        return String.valueOf(a.a(bArr));
    }

    public static final String b(String str) {
        return a(a(a(a(a(str, "&lt;", SimpleComparison.LESS_THAN_OPERATION), "&gt;", SimpleComparison.GREATER_THAN_OPERATION), "&quot;", "\""), "&apos;", "'"), "&amp;", "&");
    }
}
