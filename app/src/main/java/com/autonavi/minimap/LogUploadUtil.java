package com.autonavi.minimap;

import android.app.Application;

public class LogUploadUtil {
    public static final String ANR = "anr";
    public static boolean enable = false;
    private static a mInterface;

    public interface a {
    }

    public static void init(Application application, boolean z) {
    }

    public static void recordLog(String str, String str2) {
    }

    public static void recordTime(String str, String str2, String str3) {
    }

    public static void setImpl(a aVar) {
        if (aVar == null) {
            throw new NullPointerException("ILogUploadUtil can not be null");
        }
        mInterface = aVar;
    }
}
