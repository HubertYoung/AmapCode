package com.alibaba.analytics.utils;

import android.text.TextUtils;
import java.util.Map;
import java.util.Map.Entry;

public class Logger {
    private static final int LOG_LEVLE_D = 4;
    private static final int LOG_LEVLE_E = 1;
    private static final int LOG_LEVLE_I = 3;
    private static final int LOG_LEVLE_L = 0;
    private static final int LOG_LEVLE_V = 5;
    private static final int LOG_LEVLE_W = 2;
    private static String TAG = "Analytics";
    private static final String TAG_ENABLE_LOG = "enablelog";
    private static final String TAG_LOG_PREFIX = "Analytics.";
    private static boolean isDebug = false;
    private static ILogger mLogger;

    @Deprecated
    public interface ILog {
        int e(String str, String str2);

        int e(String str, String str2, Throwable th);
    }

    @Deprecated
    public static void setLogAdapter(ILog iLog) {
    }

    public static void setLogger(ILogger iLogger) {
        mLogger = iLogger;
    }

    public static boolean isDebug() {
        return isDebug;
    }

    public static void setDebug(boolean z) {
        isDebug = z;
    }

    private static boolean isExtendLogValid(int i) {
        return mLogger != null && mLogger.isValid() && i < mLogger.getLogLevel();
    }

    private static boolean isDefaultLogValid() {
        return isDebug;
    }

    public static void d() {
        if (isExtendLogValid(4)) {
            mLogger.logd(buildLogTag(), buildLogMsg((String) null, new Object[0]));
            return;
        }
        if (isDefaultLogValid()) {
            buildLogTag();
            buildLogMsg((String) null, new Object[0]);
        }
    }

    public static void d(String str, Map<String, String> map) {
        if (isExtendLogValid(4)) {
            mLogger.logd(buildLogTag(), buildLogMsg(str, map));
            return;
        }
        if (isDefaultLogValid()) {
            buildLogTag();
            buildLogMsg(str, map);
        }
    }

    public static void d(String str, Object... objArr) {
        if (isExtendLogValid(4)) {
            mLogger.logd(buildLogTag(), buildLogMsg(str, objArr));
            return;
        }
        if (isDefaultLogValid()) {
            buildLogTag();
            buildLogMsg(str, objArr);
        }
    }

    public static void w() {
        if (isExtendLogValid(2)) {
            mLogger.logw(buildLogTag(), buildLogMsg((String) null, new Object[0]));
            return;
        }
        if (isDefaultLogValid()) {
            buildLogTag();
            buildLogMsg((String) null, new Object[0]);
        }
    }

    public static void w(String str, Map<String, String> map) {
        if (isExtendLogValid(2)) {
            mLogger.logw(buildLogTag(), buildLogMsg(str, map));
            return;
        }
        if (isDefaultLogValid()) {
            buildLogTag();
            buildLogMsg(str, map);
        }
    }

    public static void w(String str, Object... objArr) {
        if (isExtendLogValid(2)) {
            mLogger.logw(buildLogTag(), buildLogMsg(str, objArr));
            return;
        }
        if (isDefaultLogValid()) {
            buildLogTag();
            buildLogMsg(str, objArr);
        }
    }

    public static void w(String str, Throwable th, Object... objArr) {
        if (isExtendLogValid(2)) {
            mLogger.logw(buildLogTag(), buildLogMsg(str, objArr), th);
            return;
        }
        if (isDefaultLogValid()) {
            buildLogTag();
            buildLogMsg(str, objArr);
        }
    }

    public static void i() {
        if (isExtendLogValid(3)) {
            mLogger.logi(buildLogTag(), buildLogMsg((String) null, new Object[0]));
            return;
        }
        if (isDefaultLogValid()) {
            buildLogTag();
            buildLogMsg((String) null, new Object[0]);
        }
    }

    public static void i(String str, Map<String, String> map) {
        if (isExtendLogValid(3)) {
            mLogger.logi(buildLogTag(), buildLogMsg(str, map));
            return;
        }
        if (isDefaultLogValid()) {
            buildLogTag();
            buildLogMsg(str, map);
        }
    }

    public static void i(String str, Object... objArr) {
        if (isExtendLogValid(3)) {
            mLogger.logi(buildLogTag(), buildLogMsg(str, objArr));
            return;
        }
        if (isDefaultLogValid()) {
            buildLogTag();
            buildLogMsg(str, objArr);
        }
    }

    public static void e() {
        if (isExtendLogValid(1)) {
            mLogger.loge(buildLogTag(), buildLogMsg((String) null, new Object[0]));
            return;
        }
        if (isDefaultLogValid()) {
            buildLogTag();
            buildLogMsg((String) null, new Object[0]);
        }
    }

    public static void e(String str, Map<String, String> map) {
        if (isExtendLogValid(1)) {
            mLogger.loge(buildLogTag(), buildLogMsg(str, map));
            return;
        }
        if (isDefaultLogValid()) {
            buildLogTag();
            buildLogMsg(str, map);
        }
    }

    public static void e(String str, Object... objArr) {
        if (isExtendLogValid(1)) {
            mLogger.loge(buildLogTag(), buildLogMsg(str, objArr));
            return;
        }
        if (isDefaultLogValid()) {
            buildLogTag();
            buildLogMsg(str, objArr);
        }
    }

    public static void e(String str, Throwable th, Object... objArr) {
        if (isExtendLogValid(1)) {
            mLogger.loge(buildLogTag(), buildLogMsg(str, objArr), th);
            return;
        }
        if (isDefaultLogValid()) {
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
        if (!isDebug) {
            return TAG;
        }
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
        StringBuilder sb = new StringBuilder(TAG_LOG_PREFIX);
        sb.append(str);
        sb.append(".");
        sb.append(str2);
        return sb.toString();
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
        if (map != null) {
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
        }
        return sb.toString();
    }

    private static StackTraceElement getStackTrace() {
        StackTraceElement[] stackTrace;
        if (!isDebug) {
            return null;
        }
        try {
            for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
                if (!stackTraceElement.isNativeMethod() && !stackTraceElement.getClassName().equals(Thread.class.getName()) && !stackTraceElement.getClassName().equals(Logger.class.getName())) {
                    return stackTraceElement;
                }
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }
}
