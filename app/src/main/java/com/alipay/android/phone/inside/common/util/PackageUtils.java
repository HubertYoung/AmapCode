package com.alipay.android.phone.inside.common.util;

import android.content.Context;
import com.alipay.android.phone.inside.log.api.LoggerFactory;

public class PackageUtils {
    public static String a(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
            r3 = "";
            return "";
        }
    }
}
