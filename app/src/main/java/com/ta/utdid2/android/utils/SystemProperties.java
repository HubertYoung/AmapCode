package com.ta.utdid2.android.utils;

import com.ta.audid.utils.UtdidLogger;

public class SystemProperties {
    public static String get(String str) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod("get", new Class[]{String.class}).invoke(cls, new Object[]{str});
        } catch (Exception e) {
            UtdidLogger.se("", e, new Object[0]);
            r7 = "";
            return "";
        }
    }

    public static String get(String str, String str2) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod("get", new Class[]{String.class, String.class}).invoke(cls, new Object[]{str, str2});
        } catch (Exception e) {
            UtdidLogger.se("", e, new Object[0]);
            return str2;
        }
    }
}
