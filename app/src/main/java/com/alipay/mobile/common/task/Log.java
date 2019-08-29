package com.alipay.mobile.common.task;

import com.alipay.android.hackbyte.ClassVerifier;

public class Log {
    private static boolean a = false;

    public Log() {
        if (Boolean.FALSE.booleanValue()) {
            android.util.Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static void v(String tag, String msg) {
        if (a) {
            android.util.Log.v(tag, msg);
        }
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (a) {
            android.util.Log.v(tag, msg, tr);
        }
    }

    public static void d(String tag, String msg) {
        if (a) {
            android.util.Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (a) {
            android.util.Log.d(tag, msg, tr);
        }
    }

    public static void i(String tag, String msg) {
        if (a) {
            android.util.Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (a) {
            android.util.Log.i(tag, msg, tr);
        }
    }

    public static void w(String tag, String msg) {
        if (a) {
            android.util.Log.w(tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (a) {
            android.util.Log.w(tag, msg, tr);
        }
    }

    public static void e(String tag, String msg) {
        if (a) {
            android.util.Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (a) {
            android.util.Log.e(tag, msg, tr);
        }
    }
}
