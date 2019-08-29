package defpackage;

import java.security.MessageDigest;

/* renamed from: cz reason: default package */
/* compiled from: StringUtils */
public final class cz {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String a(String str) {
        return str == null ? "" : str;
    }

    public static String a(String str, String str2) {
        StringBuilder sb = new StringBuilder(str.length() + str2.length());
        sb.append(str);
        sb.append(str2);
        return sb.toString();
    }

    public static String a(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder(str.length() + str2.length() + str3.length());
        sb.append(str);
        sb.append(str2);
        sb.append(str3);
        return sb.toString();
    }

    public static String b(String str, String str2) {
        return a(str, "://", str2);
    }

    public static String b(String str) {
        if (str == null) {
            return null;
        }
        try {
            return a(MessageDigest.getInstance("MD5").digest(str.getBytes("utf-8")));
        } catch (Exception unused) {
            return null;
        }
    }

    private static String a(byte[] bArr, char[] cArr) {
        int length = bArr.length;
        char[] cArr2 = new char[(length << 1)];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i + 1;
            cArr2[i] = cArr[(bArr[i2] & 240) >>> 4];
            i = i3 + 1;
            cArr2[i3] = cArr[bArr[i2] & 15];
        }
        return new String(cArr2);
    }

    public static String a(byte[] bArr) {
        return bArr == null ? "" : a(bArr, a);
    }

    public static boolean c(String str) {
        if (str != null) {
            int length = str.length();
            if (length != 0) {
                for (int i = 0; i < length; i++) {
                    if (!Character.isWhitespace(str.charAt(i))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return true;
    }

    public static boolean c(String str, String str2) {
        return (str == null && str2 == null) || (str != null && str.equals(str2));
    }
}
