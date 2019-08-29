package com.sina.weibo.sdk.utils;

public class LogUtil {
    public static boolean sIsLogEnable = false;

    public static void enableLog() {
        sIsLogEnable = true;
    }

    public static void disableLog() {
        sIsLogEnable = false;
    }

    public static void d(String str, String str2) {
        if (sIsLogEnable) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            StringBuilder sb = new StringBuilder();
            sb.append(stackTraceElement.getFileName());
            sb.append("(");
            sb.append(stackTraceElement.getLineNumber());
            sb.append(") ");
            sb.append(stackTraceElement.getMethodName());
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append(": ");
            sb3.append(str2);
        }
    }

    public static void i(String str, String str2) {
        if (sIsLogEnable) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            StringBuilder sb = new StringBuilder();
            sb.append(stackTraceElement.getFileName());
            sb.append("(");
            sb.append(stackTraceElement.getLineNumber());
            sb.append(") ");
            sb.append(stackTraceElement.getMethodName());
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append(": ");
            sb3.append(str2);
        }
    }

    public static void e(String str, String str2) {
        if (sIsLogEnable) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            StringBuilder sb = new StringBuilder();
            sb.append(stackTraceElement.getFileName());
            sb.append("(");
            sb.append(stackTraceElement.getLineNumber());
            sb.append(") ");
            sb.append(stackTraceElement.getMethodName());
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append(": ");
            sb3.append(str2);
        }
    }

    public static void w(String str, String str2) {
        if (sIsLogEnable) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            StringBuilder sb = new StringBuilder();
            sb.append(stackTraceElement.getFileName());
            sb.append("(");
            sb.append(stackTraceElement.getLineNumber());
            sb.append(") ");
            sb.append(stackTraceElement.getMethodName());
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append(": ");
            sb3.append(str2);
        }
    }

    public static void v(String str, String str2) {
        if (sIsLogEnable) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            StringBuilder sb = new StringBuilder();
            sb.append(stackTraceElement.getFileName());
            sb.append("(");
            sb.append(stackTraceElement.getLineNumber());
            sb.append(") ");
            sb.append(stackTraceElement.getMethodName());
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append(": ");
            sb3.append(str2);
        }
    }

    public static String getStackTraceMsg() {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        StringBuilder sb = new StringBuilder();
        sb.append(stackTraceElement.getFileName());
        sb.append("(");
        sb.append(stackTraceElement.getLineNumber());
        sb.append(") ");
        sb.append(stackTraceElement.getMethodName());
        return sb.toString();
    }
}
