package com.alipay.android.phone.inside.common.util;

import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.log.api.LoggerFactory;

public class DebugUtil {
    private static boolean a = false;
    private static boolean b = false;

    public static boolean a() {
        if (!a) {
            try {
                b = (LauncherApplication.a().getApplicationContext().getApplicationInfo().flags & 2) == 2;
            } catch (Throwable th) {
                LoggerFactory.f().b("inside", "init isDebug", th);
            }
        }
        return b;
    }
}
