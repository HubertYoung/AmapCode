package com.xiaomi.metoknlp.a;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.xiaomi.metoknlp.a;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class e {
    private static String a;
    private static String b;
    private static String c;

    public static String a() {
        if (a != null) {
            return a;
        }
        String b2 = b(d.b());
        if (b2 == null) {
            return b(c.a("ro.ril.miui.imei", ""));
        }
        a = b2;
        return b2;
    }

    public static String a(String str) {
        byte[] digest;
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] bytes = str.getBytes();
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bytes);
            char[] cArr2 = new char[(r1 * 2)];
            int i = 0;
            for (byte b2 : instance.digest()) {
                int i2 = i + 1;
                cArr2[i] = cArr[(b2 >>> 4) & 15];
                i = i2 + 1;
                cArr2[i2] = cArr[b2 & 15];
            }
            return new String(cArr2);
        } catch (Exception unused) {
            return null;
        }
    }

    public static boolean a(int i) {
        return i == 1;
    }

    public static String b() {
        if (b != null && !b.isEmpty()) {
            return b;
        }
        String a2 = c.a("ro.product.model", "");
        b = a2;
        String replaceAll = a2.replaceAll(Token.SEPARATOR, "");
        b = replaceAll;
        return replaceAll;
    }

    private static String b(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        if (str.startsWith(",") || str.endsWith(",")) {
            str = str.replace(",", "");
        }
        if (str.startsWith("0")) {
            try {
                if (Long.parseLong(str) == 0) {
                    return null;
                }
            } catch (Exception unused) {
            }
        }
        return str;
    }

    public static String c() {
        if (c != null && !c.isEmpty()) {
            return c;
        }
        String a2 = c.a("ro.build.version.incremental", "");
        c = a2;
        return a2;
    }

    public static String d() {
        return !a.a() ? a.b() : a.c() ? "cts" : !c.a("ro.product.locale.region", "CN").equals("CN") ? "global" : a.d() ? "alpha" : a.e() ? "dev" : a.f() ? "stable" : "alpha";
    }

    public static int e() {
        String a2 = d.a();
        if (a2 == null) {
            return -1;
        }
        int length = a2.length();
        if (!a2.isEmpty() && length > 1) {
            try {
                return Integer.parseInt(a2.substring(0, 3));
            } catch (Exception unused) {
            }
        }
        return -1;
    }

    public static int f() {
        String a2 = d.a();
        if (a2 == null) {
            return -1;
        }
        int length = a2.length();
        if (!a2.isEmpty() && length > 1) {
            try {
                return Integer.parseInt(a2.substring(3));
            } catch (Exception unused) {
            }
        }
        return -1;
    }

    public static String g() {
        try {
            return a.a().getPackageManager().getPackageInfo(a.a().getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String h() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
}
