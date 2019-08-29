package com.alipay.android.phone.mobilesdk.permission.utils;

import android.text.TextUtils;
import java.lang.reflect.Field;

/* compiled from: CommonUtil */
public final class b {
    public static String a = "";
    public static String b = "";

    public static String a() {
        if (!TextUtils.isEmpty(b)) {
            return b;
        }
        try {
            Field versionFld = Class.forName("com.alipay.android.phone.mobilesdk.permission.BuildConfig").getField("BUNDLE_NAME");
            versionFld.setAccessible(true);
            b = (String) versionFld.get(null);
        } catch (Throwable th) {
            b = "";
        }
        return b;
    }
}
