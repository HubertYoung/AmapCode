package com.alipay.mobile.quinox.utils;

import android.util.Log;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.quinox.log.Logger;

public class TraceLogger {

    static class InternalTraceLogger extends Logger {
        private InternalTraceLogger() {
        }

        public int verbose(String str, String str2) {
            LoggerFactory.getTraceLogger().verbose(str, str2);
            return 0;
        }

        public int debug(String str, String str2) {
            LoggerFactory.getTraceLogger().debug(str, str2);
            return 0;
        }

        public int info(String str, String str2) {
            LoggerFactory.getTraceLogger().info(str, str2);
            return 0;
        }

        public int warn(String str, String str2) {
            LoggerFactory.getTraceLogger().warn(str, str2);
            return 0;
        }

        public int error(String str, String str2) {
            LoggerFactory.getTraceLogger().error(str, str2);
            return 0;
        }

        public String getStackTraceString(Throwable th) {
            return Log.getStackTraceString(th);
        }
    }

    static {
        com.alipay.mobile.quinox.log.Log.setLogger(new InternalTraceLogger());
    }

    public static void v(String str, String str2) {
        com.alipay.mobile.quinox.log.Log.v(str, str2);
    }

    public static void v(String str, Throwable th) {
        com.alipay.mobile.quinox.log.Log.v(str, th);
    }

    public static void v(String str, String str2, Throwable th) {
        com.alipay.mobile.quinox.log.Log.v(str, str2, th);
    }

    public static void d(String str, String str2) {
        com.alipay.mobile.quinox.log.Log.d(str, str2);
    }

    public static void d(String str, Throwable th) {
        com.alipay.mobile.quinox.log.Log.d(str, th);
    }

    public static void d(String str, String str2, Throwable th) {
        com.alipay.mobile.quinox.log.Log.d(str, str2, th);
    }

    public static void i(String str, String str2) {
        com.alipay.mobile.quinox.log.Log.i(str, str2);
    }

    public static void i(String str, String str2, Throwable th) {
        com.alipay.mobile.quinox.log.Log.i(str, str2, th);
    }

    public static void i(String str, Throwable th) {
        com.alipay.mobile.quinox.log.Log.i(str, th);
    }

    public static void w(String str, String str2) {
        com.alipay.mobile.quinox.log.Log.w(str, str2);
    }

    public static void w(String str, Throwable th) {
        com.alipay.mobile.quinox.log.Log.w(str, th);
    }

    public static void w(String str, String str2, Throwable th) {
        com.alipay.mobile.quinox.log.Log.w(str, str2, th);
    }

    public static void e(String str, String str2) {
        com.alipay.mobile.quinox.log.Log.e(str, str2);
    }

    public static void e(String str, Throwable th) {
        com.alipay.mobile.quinox.log.Log.e(str, th);
    }

    public static void e(String str, String str2, Throwable th) {
        com.alipay.mobile.quinox.log.Log.e(str, str2, th);
    }
}
