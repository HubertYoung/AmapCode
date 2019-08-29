package com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.util;

public final class TextUtils {
    public static boolean isEmpty(CharSequence s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isBlank(CharSequence s) {
        if (s == null) {
            return true;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
