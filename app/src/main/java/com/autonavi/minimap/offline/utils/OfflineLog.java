package com.autonavi.minimap.offline.utils;

public final class OfflineLog {
    private static final String SDK_TAG = "OfflineSDK";

    public static void d(String str) {
    }

    public static void e(String str) {
    }

    public static void e(String str, Throwable th) {
    }

    public static void i(String str) {
    }

    public static void v(String str) {
    }

    public static void w(String str) {
    }

    public static void i(String str, String str2) {
        StringBuilder sb = new StringBuilder("[");
        sb.append(str);
        sb.append("] ");
        sb.append(str2);
    }

    public static void d(String str, String str2) {
        StringBuilder sb = new StringBuilder("[");
        sb.append(str);
        sb.append("] ");
        sb.append(str2);
    }

    public static void w(String str, String str2) {
        StringBuilder sb = new StringBuilder("[");
        sb.append(str);
        sb.append("] ");
        sb.append(str2);
    }

    public static void v(String str, String str2) {
        StringBuilder sb = new StringBuilder("[");
        sb.append(str);
        sb.append("] ");
        sb.append(str2);
    }

    public static void e(String str, String str2) {
        StringBuilder sb = new StringBuilder("[");
        sb.append(str);
        sb.append("] ");
        sb.append(str2);
    }

    public static void e(String str, String str2, Throwable th) {
        StringBuilder sb = new StringBuilder("[");
        sb.append(str);
        sb.append("] ");
        sb.append(str2);
    }
}
