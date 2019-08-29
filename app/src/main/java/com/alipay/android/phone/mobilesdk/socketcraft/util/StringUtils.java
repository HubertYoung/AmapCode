package com.alipay.android.phone.mobilesdk.socketcraft.util;

public class StringUtils {
    public static final boolean isEmpty(String str) {
        return "".equals(str) || str == null || str.trim().length() <= 0;
    }
}
