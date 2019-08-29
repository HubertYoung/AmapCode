package com.alipay.mobile.security.bio.utils;

import android.util.Log;
import com.alipay.mobile.security.bio.config.Constant;

public final class BioLog {
    private static Logger a = new a(0);

    private static final class a extends Logger {
        private a() {
        }

        /* synthetic */ a(byte b) {
            this();
        }

        public final int verbose(String str, String str2) {
            return Log.v(str, str2);
        }

        public final int debug(String str, String str2) {
            return Log.d(str, str2);
        }

        public final int info(String str, String str2) {
            return Log.i(str, str2);
        }

        public final int warn(String str, String str2) {
            return Log.w(str, str2);
        }

        public final int error(String str, String str2) {
            return Log.e(str, str2);
        }

        /* access modifiers changed from: protected */
        public final String getStackTraceString(Throwable th) {
            return Log.getStackTraceString(th);
        }
    }

    private BioLog() {
    }

    public static void setLogger(Logger logger) {
        a = logger;
    }

    public static void v(String str) {
        a.v((String) Constant.DEBUG_LOG_TAG, str);
    }

    public static void v(String str, String str2) {
        a.v(str, str2);
    }

    public static void d(String str) {
        a.d((String) Constant.DEBUG_LOG_TAG, str);
    }

    public static void d(String str, String str2) {
        a.d(str, str2);
    }

    public static void i(String str) {
        a.i((String) Constant.DEBUG_LOG_TAG, str);
    }

    public static void i(String str, String str2) {
        a.i(str, str2);
    }

    public static void w(String str) {
        a.w((String) Constant.DEBUG_LOG_TAG, str);
    }

    public static void w(String str, String str2) {
        a.w(str, str2);
    }

    public static void w(Throwable th) {
        a.w((String) Constant.DEBUG_LOG_TAG, th);
    }

    public static void w(String str, Throwable th) {
        a.w(Constant.DEBUG_LOG_TAG, str, th);
    }

    public static void w(String str, String str2, Throwable th) {
        a.w(str, str2, th);
    }

    public static void e(String str) {
        a.e((String) Constant.DEBUG_LOG_TAG, str);
    }

    public static void e(String str, String str2) {
        a.e(str, str2);
    }

    public static void e(Throwable th) {
        a.e((String) Constant.DEBUG_LOG_TAG, th);
    }

    public static void e(String str, Throwable th) {
        a.e(Constant.DEBUG_LOG_TAG, str, th);
    }

    public static void e(String str, String str2, Throwable th) {
        a.e(str, str2, th);
    }
}
