package com.alipay.mobile.common.nbnet.biz.util;

import android.text.TextUtils;
import com.alipay.mobile.common.nbnet.biz.exception.NBNetAssertionException;

public class AssertUtil {
    public static void a(boolean condition) {
        if (!condition) {
            a((String) "");
        }
    }

    public static void a(String message, boolean condition) {
        if (!condition) {
            a(message);
        }
    }

    private static void a(String message) {
        if (TextUtils.isEmpty(message)) {
            throw new NBNetAssertionException();
        }
        throw new NBNetAssertionException(message);
    }
}
