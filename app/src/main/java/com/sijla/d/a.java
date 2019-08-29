package com.sijla.d;

import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class a {
    private static final char[] a = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

    public static String a(byte[] bArr) {
        int length = bArr.length;
        StringBuilder sb = new StringBuilder((bArr.length * 3) / 2);
        int i = length - 3;
        int i2 = 0;
        loop0:
        while (true) {
            int i3 = 0;
            while (i2 <= i) {
                byte b = ((bArr[i2] & 255) << 16) | ((bArr[i2 + 1] & 255) << 8) | (bArr[i2 + 2] & 255);
                sb.append(a[(b >> 18) & 63]);
                sb.append(a[(b >> ClientRpcPack.SYMMETRIC_ENCRYPT_3DES) & 63]);
                sb.append(a[(b >> 6) & 63]);
                sb.append(a[b & 63]);
                i2 += 3;
                int i4 = i3 + 1;
                if (i3 >= 14) {
                    sb.append(Token.SEPARATOR);
                } else {
                    i3 = i4;
                }
            }
            break loop0;
        }
        int i5 = length + 0;
        if (i2 == i5 - 2) {
            int i6 = ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2] & 255) << 16);
            sb.append(a[(i6 >> 18) & 63]);
            sb.append(a[(i6 >> 12) & 63]);
            sb.append(a[(i6 >> 6) & 63]);
            sb.append("=");
        } else if (i2 == i5 - 1) {
            int i7 = (bArr[i2] & 255) << 16;
            sb.append(a[(i7 >> 18) & 63]);
            sb.append(a[(i7 >> 12) & 63]);
            sb.append("==");
        }
        return sb.toString();
    }

    private static int a(char c) {
        if (c >= 'A' && c <= 'Z') {
            return c - 'A';
        }
        if (c >= 'a' && c <= 'z') {
            return (c - 'a') + 26;
        }
        if (c >= '0' && c <= '9') {
            return (c - '0') + 26 + 26;
        }
        if (c == '+') {
            return 62;
        }
        if (c == '/') {
            return 63;
        }
        if (c == '=') {
            return 0;
        }
        throw new RuntimeException("unexpected code: ".concat(String.valueOf(c)));
    }

    public static byte[] a(String str) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            a(str, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            try {
                return byteArray;
            } catch (IOException unused) {
                return byteArray;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                byteArrayOutputStream.close();
            } catch (IOException unused2) {
            }
        }
    }

    private static void a(String str, OutputStream outputStream) {
        int length = str.length();
        int i = 0;
        while (true) {
            if (i < length && str.charAt(i) <= ' ') {
                i++;
            } else if (i != length) {
                int i2 = i + 2;
                int i3 = i + 3;
                int a2 = (a(str.charAt(i)) << 18) + (a(str.charAt(i + 1)) << 12) + (a(str.charAt(i2)) << 6) + a(str.charAt(i3));
                outputStream.write((a2 >> 16) & 255);
                if (str.charAt(i2) != '=') {
                    outputStream.write((a2 >> 8) & 255);
                    if (str.charAt(i3) != '=') {
                        outputStream.write(a2 & 255);
                        i += 4;
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }
}
