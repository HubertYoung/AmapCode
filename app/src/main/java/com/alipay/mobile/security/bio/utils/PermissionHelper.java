package com.alipay.mobile.security.bio.utils;

import android.content.Context;
import android.os.Binder;

public class PermissionHelper {
    public static int checkCallingOrSelfPermission(Context context, String str) {
        if (str != null && context != null) {
            return context.checkPermission(str, Binder.getCallingPid(), Binder.getCallingUid());
        }
        throw new IllegalArgumentException("context or permission is null");
    }
}
