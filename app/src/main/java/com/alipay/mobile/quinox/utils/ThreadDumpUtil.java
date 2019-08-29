package com.alipay.mobile.quinox.utils;

import android.text.TextUtils;
import java.util.Map.Entry;

public class ThreadDumpUtil {
    private static final String TAG = "ThreadDumpUtil";

    public static void logAllThreadsTraces() {
        try {
            String threadsStackTrace = getThreadsStackTrace();
            if (!TextUtils.isEmpty(threadsStackTrace)) {
                TraceLogger.w((String) TAG, "All Threads Traces: ###".concat(String.valueOf(threadsStackTrace)));
            }
        } catch (Throwable th) {
            TraceLogger.i(TAG, "logAllThreadsTraces failed", th);
        }
    }

    public static String getThreadsStackTrace() {
        try {
            StringBuilder sb = new StringBuilder();
            for (Entry next : Thread.getAllStackTraces().entrySet()) {
                String name = ((Thread) next.getKey()).getName();
                sb.append(10);
                sb.append("ThreadName=");
                sb.append(name);
                sb.append("\n");
                for (StackTraceElement valueOf : (StackTraceElement[]) next.getValue()) {
                    sb.append(String.valueOf(valueOf));
                    sb.append("\n");
                }
                sb.append("\n");
            }
            return sb.toString();
        } catch (Throwable th) {
            TraceLogger.e(TAG, "getThreadsStackTrace", th);
            return "";
        }
    }
}
