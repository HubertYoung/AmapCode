package com.alipay.mobile.tinyappcommon.utils;

public final class TinyAppNumberUtils {
    public static int parseInt(String text) {
        return parseInt(text, 0);
    }

    public static int parseInt(String text, int defValue) {
        try {
            return Integer.parseInt(text);
        } catch (Exception e) {
            return defValue;
        }
    }

    public static long parseLong(String text) {
        return parseLong(text, 0);
    }

    public static long parseLong(String text, long defValue) {
        try {
            return Long.parseLong(text);
        } catch (Exception e) {
            return defValue;
        }
    }
}
