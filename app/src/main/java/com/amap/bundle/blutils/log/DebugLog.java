package com.amap.bundle.blutils.log;

import android.text.TextUtils;
import android.util.Log;
import java.io.PrintWriter;
import java.io.StringWriter;

public class DebugLog {
    public static boolean DEBUG = bno.a;
    private static int logLevel = 2;

    public static void e(String str, String str2, Throwable th) {
    }

    public static boolean isDebug() {
        return bno.a;
    }

    public static boolean isRelease() {
        return !bno.a;
    }

    private static String generateTag(StackTraceElement stackTraceElement) {
        String className = stackTraceElement.getClassName();
        String substring = className.substring(className.lastIndexOf(".") + 1);
        if (substring.length() <= 23) {
            return substring;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(substring.substring(0, 20));
        sb.append("...");
        return sb.toString();
    }

    public static void debug(Object obj, Throwable th) {
        if (DEBUG) {
            log(3, generateTag(getCallerStackTraceElement()), obj, th);
        }
    }

    public static void debug(Object obj) {
        if (DEBUG) {
            log(3, generateTag(getCallerStackTraceElement()), obj, null);
        }
    }

    public static void info(Object obj, Throwable th) {
        if (DEBUG) {
            log(4, generateTag(getCallerStackTraceElement()), obj, th);
        }
    }

    public static void info(Object obj) {
        if (DEBUG) {
            log(4, generateTag(getCallerStackTraceElement()), obj, null);
        }
    }

    public static void warn(Object obj, Throwable th) {
        if (DEBUG) {
            log(5, generateTag(getCallerStackTraceElement()), obj, th);
        }
    }

    public static void warn(Object obj) {
        if (DEBUG) {
            log(5, generateTag(getCallerStackTraceElement()), obj, null);
        }
    }

    public static void error(Object obj, Throwable th) {
        if (DEBUG) {
            log(6, generateTag(getCallerStackTraceElement()), obj, th);
        }
    }

    public static void error(Object obj) {
        if (DEBUG) {
            log(6, generateTag(getCallerStackTraceElement()), obj, null);
        }
    }

    public static void fatal(Object obj, Throwable th) {
        if (DEBUG) {
            log(7, generateTag(getCallerStackTraceElement()), obj, th);
        }
    }

    public static void fatal(Object obj) {
        if (DEBUG) {
            log(7, generateTag(getCallerStackTraceElement()), obj, null);
        }
    }

    private static void log(int i, String str, Object obj, Throwable th) {
        String str2;
        if (DEBUG && i >= logLevel) {
            try {
                Log.isLoggable(str, i);
                if (th != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(String.valueOf(obj));
                    sb.append(10);
                    sb.append(Log.getStackTraceString(th));
                    str2 = sb.toString();
                } else if (obj instanceof Throwable) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(String.valueOf(obj));
                    sb2.append(10);
                    sb2.append(Log.getStackTraceString((Throwable) obj));
                    str2 = sb2.toString();
                } else {
                    str2 = String.valueOf(obj);
                }
                Log.println(i, str, str2);
            } catch (Throwable unused) {
            }
        }
    }

    private static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }

    public static void p(String str, String str2) {
        if (DEBUG) {
            Log.getStackTraceString(new Exception(str2));
        }
    }

    public static void logBigStr(String str, String str2) {
        if (DEBUG && !TextUtils.isEmpty(str2)) {
            int i = 0;
            int length = (str2.length() - 2048) + 1;
            while (i < length) {
                int i2 = i + 2048;
                str2.substring(i, i2);
                i = i2;
            }
            if (i < str2.length()) {
                str2.substring(i);
            }
        }
    }

    public static String getPrintStackTraceString(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
}
