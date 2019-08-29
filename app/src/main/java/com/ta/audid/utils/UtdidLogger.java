package com.ta.audid.utils;

import android.os.Process;
import android.text.TextUtils;
import java.util.Map;
import java.util.Map.Entry;

public class UtdidLogger {
    private static final String TAG_LOG_PREFIX = "Utdid.";
    private static boolean isDebug = false;
    private static boolean isSDebug = false;

    public static void setDebug(boolean z) {
        isDebug = z;
    }

    public static boolean isDebug() {
        return isDebug;
    }

    public static void d() {
        if (isDebug) {
            buildLogTag();
            buildLogMsg((String) null, new Object[0]);
        }
    }

    public static void e() {
        if (isDebug) {
            buildLogTag();
            buildLogMsg((String) null, new Object[0]);
        }
    }

    public static void i() {
        if (isDebug) {
            buildLogTag();
            buildLogMsg((String) null, new Object[0]);
        }
    }

    public static void d(String str, Map<String, String> map) {
        if (isDebug) {
            buildLogTag();
            buildLogMsg(str, map);
        }
    }

    public static void d(String str, Object... objArr) {
        if (isDebug) {
            buildLogTag();
            buildLogMsg(str, objArr);
        }
    }

    public static void sd(String str, Object... objArr) {
        if (isSDebug) {
            buildLogTag();
            buildLogMsg(str, objArr);
        }
    }

    public static void i(String str, Object... objArr) {
        if (isDebug) {
            buildLogTag();
            buildLogMsg(str, objArr);
        }
    }

    public static void w(String str, Object... objArr) {
        if (isDebug) {
            buildLogTag();
            buildLogMsg(str, objArr);
        }
    }

    public static void w(String str, Throwable th, Object... objArr) {
        if (isDebug) {
            buildLogTag();
            buildLogMsg(str, objArr);
        }
    }

    public static void e(String str, Object... objArr) {
        if (isDebug) {
            buildLogTag();
            buildLogMsg(str, objArr);
        }
    }

    public static void e(String str, Throwable th, Object... objArr) {
        if (isDebug) {
            buildLogTag();
            buildLogMsg(str, objArr);
        }
    }

    public static void se(String str, Object... objArr) {
        if (isSDebug) {
            buildLogTag();
            buildLogMsg(str, objArr);
        }
    }

    public static void se(String str, Throwable th, Object... objArr) {
        if (isSDebug) {
            buildLogTag();
            buildLogMsg(str, objArr);
        }
    }

    private static String formatKv(Object obj, Object obj2) {
        Object[] objArr = new Object[2];
        if (obj == null) {
            obj = "";
        }
        objArr[0] = obj;
        if (obj2 == null) {
            obj2 = "";
        }
        objArr[1] = obj2;
        return String.format("%s:%s", objArr);
    }

    private static String buildLogTag() {
        StackTraceElement stackTrace = getStackTrace();
        String str = "";
        String str2 = "";
        if (stackTrace != null) {
            String className = stackTrace.getClassName();
            if (!TextUtils.isEmpty(className)) {
                str = className.substring(className.lastIndexOf(46) + 1);
            }
            str2 = stackTrace.getMethodName();
        }
        String valueOf = String.valueOf(Process.myPid());
        StringBuilder sb = new StringBuilder();
        sb.append(Thread.currentThread().getId());
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder(TAG_LOG_PREFIX);
        sb3.append(str);
        sb3.append(".");
        sb3.append(str2);
        sb3.append(".");
        sb3.append(valueOf);
        sb3.append(".");
        sb3.append(sb2);
        return sb3.toString();
    }

    private static String buildLogMsg(String str, Object... objArr) {
        if (str == null && objArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Object[] objArr2 = new Object[1];
        if (str == null) {
            str = "-";
        }
        int i = 0;
        objArr2[0] = str;
        sb.append(String.format("[%s] ", objArr2));
        if (objArr != null) {
            int length = objArr.length;
            while (true) {
                int i2 = i + 1;
                if (i2 >= objArr.length) {
                    break;
                }
                sb.append(formatKv(objArr[i], objArr[i2]));
                if (i2 < length - 1) {
                    sb.append(",");
                }
                i = i2 + 1;
            }
            if (i == objArr.length - 1) {
                sb.append(objArr[i]);
            }
        }
        return sb.toString();
    }

    private static String buildLogMsg(String str, Map<String, String> map) {
        if (str == null && map == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Object[] objArr = new Object[1];
        if (str == null) {
            str = "-";
        }
        int i = 0;
        objArr[0] = str;
        sb.append(String.format("[%s] ", objArr));
        int size = map.size();
        for (Entry next : map.entrySet()) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append((String) next.getKey());
            sb2.append(" : ");
            sb2.append((String) next.getValue());
            sb.append(sb2.toString());
            i++;
            if (i < size) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    private static StackTraceElement getStackTrace() {
        StackTraceElement[] stackTrace;
        try {
            for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
                if (!stackTraceElement.isNativeMethod() && !stackTraceElement.getClassName().equals(Thread.class.getName()) && !stackTraceElement.getClassName().equals(UtdidLogger.class.getName())) {
                    return stackTraceElement;
                }
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }
}
