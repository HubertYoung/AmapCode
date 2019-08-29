package com.alipay.mobile.quinox.utils;

import android.content.ContentResolver;
import android.os.Build.VERSION;
import java.lang.reflect.Field;

public final class ContentResolvers {
    static final String M_TARGETSDKVERSION = "mTargetSdkVersion";
    static Field sTargetSdkField;

    static {
        if (VERSION.SDK_INT >= 26) {
            try {
                Field declaredField = ContentResolver.class.getDeclaredField(M_TARGETSDKVERSION);
                sTargetSdkField = declaredField;
                declaredField.setAccessible(true);
            } catch (Throwable unused) {
            }
        }
    }

    public static void fixTargetSdkInParallel(ContentResolver contentResolver) {
        if (sTargetSdkField != null && SystemUtil.isParallelUserId()) {
            try {
                sTargetSdkField.setInt(contentResolver, 23);
            } catch (Throwable unused) {
            }
        }
    }
}
