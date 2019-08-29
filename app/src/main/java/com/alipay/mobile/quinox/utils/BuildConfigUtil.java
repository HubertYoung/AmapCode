package com.alipay.mobile.quinox.utils;

import android.content.Context;

public class BuildConfigUtil {
    private static final String META_DATA_PREFIX = "meta_data_";
    private static Class SBuildConfigClazz;

    public static String getMetaData(Context context, String str, String str2) {
        if (context == null || str == null) {
            return str2;
        }
        StringBuilder sb = new StringBuilder(META_DATA_PREFIX);
        sb.append(str.replace(".", "_"));
        return getString(context.getPackageName(), sb.toString(), str2);
    }

    public static String getString(String str, String str2, String str3) {
        try {
            if (SBuildConfigClazz == null) {
                ClassLoader classLoader = BuildConfigUtil.class.getClassLoader();
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(".BuildConfig");
                SBuildConfigClazz = classLoader.loadClass(sb.toString());
            }
        } catch (Throwable unused) {
            TraceLogger.w((String) "BuildConfigUtil", (String) "load BuildConfig fail");
        }
        if (SBuildConfigClazz != null) {
            try {
                return (String) SBuildConfigClazz.getField(str2).get(null);
            } catch (Throwable unused2) {
                TraceLogger.w((String) "BuildConfigUtil", "get field fail: ".concat(String.valueOf(str2)));
            }
        }
        return str3;
    }
}
