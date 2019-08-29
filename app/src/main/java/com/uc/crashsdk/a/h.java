package com.uc.crashsdk.a;

/* compiled from: ProGuard */
public final class h {
    public static boolean a(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean a(StringBuffer stringBuffer) {
        return stringBuffer == null || stringBuffer.length() == 0;
    }

    public static boolean b(String str) {
        return !a(str);
    }

    public static long c(String str) {
        if (a(str)) {
            return 0;
        }
        try {
            long parseLong = Long.parseLong(str.trim());
            if (parseLong < 0) {
                return 0;
            }
            return parseLong;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
