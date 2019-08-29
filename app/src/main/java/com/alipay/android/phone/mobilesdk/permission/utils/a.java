package com.alipay.android.phone.mobilesdk.permission.utils;

/* compiled from: Assertions */
public final class a {
    public static <T> T a(T t, String explaination) {
        if (t != null) {
            return t;
        }
        throw new AssertionError(explaination);
    }
}
