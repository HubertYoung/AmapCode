package com.alipay.diskcache.utils;

import com.alipay.alipaylogger.Log;

public class LogHelper {
    public static void v(String tag, String msg) {
        try {
            Log.v(tag, msg);
        } catch (Throwable th) {
        }
    }

    public static void d(String tag, String msg) {
        try {
            Log.d(tag, msg);
        } catch (Throwable th) {
        }
    }

    public static void i(String tag, String msg) {
        try {
            Log.i(tag, msg);
        } catch (Throwable th) {
        }
    }

    public static void w(String tag, String msg) {
        try {
            Log.w(tag, msg);
        } catch (Throwable th) {
        }
    }

    public static void e(String tag, String msg) {
        try {
            Log.e(tag, msg);
        } catch (Throwable th) {
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        try {
            Log.e(tag, msg, tr);
        } catch (Throwable th) {
        }
    }
}
