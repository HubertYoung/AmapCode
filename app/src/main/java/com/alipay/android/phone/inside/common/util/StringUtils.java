package com.alipay.android.phone.inside.common.util;

public class StringUtils {
    public static boolean a(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean b(String str) {
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
}
