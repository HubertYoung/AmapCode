package com.autonavi.minimap.ajx3.util;

import com.autonavi.minimap.alc.model.ALCLogLevel;
import java.io.PrintWriter;
import java.io.StringWriter;

public class AjxLog {
    private static final String LOG_BELONG = "AJX_BELONG";
    private static final String LOG_GROUP = "AJX_GROUP";
    private static final String LOG_PAGE = "AJX_PAGE";
    private static final String TAG_AJX3 = "AJX3";

    public static void aspectJLog(String str, String str2) {
    }

    public static void v(String str) {
        v(TAG_AJX3, str);
    }

    public static void v(String str, String str2) {
        cjy.a(ALCLogLevel.P5, (String) LOG_GROUP, (String) LOG_BELONG, (String) LOG_PAGE, str, str2);
    }

    public static void d(String str) {
        d(TAG_AJX3, str);
    }

    public static void d(String str, String str2) {
        cjy.a(ALCLogLevel.P4, (String) LOG_GROUP, (String) LOG_BELONG, (String) LOG_PAGE, str, str2);
    }

    public static void i(String str) {
        i(TAG_AJX3, str);
    }

    public static void i(String str, String str2) {
        cjy.a(ALCLogLevel.P3, (String) LOG_GROUP, (String) LOG_BELONG, (String) LOG_PAGE, str, str2);
    }

    public static void w(String str) {
        w(TAG_AJX3, str);
    }

    public static void w(String str, String str2) {
        cjy.a(ALCLogLevel.P2, (String) LOG_GROUP, (String) LOG_BELONG, (String) LOG_PAGE, str, str2);
    }

    public static void e(String str) {
        e(TAG_AJX3, str);
    }

    public static void e(String str, String str2) {
        cjy.a(ALCLogLevel.P1, (String) LOG_GROUP, (String) LOG_BELONG, (String) LOG_PAGE, str, str2);
    }

    public static void e(String str, String str2, Exception exc) {
        ALCLogLevel aLCLogLevel = ALCLogLevel.P1;
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(",");
        sb.append(getString(exc));
        cjy.a(aLCLogLevel, (String) LOG_GROUP, (String) LOG_BELONG, (String) LOG_PAGE, str, sb.toString());
    }

    public static String getString(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
}
