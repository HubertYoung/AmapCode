package com.sina.weibo.sdk.utils;

import android.content.Context;
import com.alipay.android.phone.a.a.a;
import com.alipay.android.phone.inside.sdk.util.GlobalConstants;

public class NetworkHelper {
    public static boolean hasInternetPermission(Context context) {
        return context == null || context.checkCallingOrSelfPermission("android.permission.INTERNET") == 0;
    }

    public static String generateUA(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(a.a);
        sb.append("__");
        sb.append("weibo");
        sb.append("__");
        sb.append(GlobalConstants.EXCEPTIONTYPE);
        sb.append("__");
        try {
            sb.append(context.getPackageManager().getPackageInfo(context.getPackageName(), 16).versionName.replaceAll("\\s+", "_"));
        } catch (Exception unused) {
            sb.append("unknown");
        }
        return sb.toString();
    }
}
