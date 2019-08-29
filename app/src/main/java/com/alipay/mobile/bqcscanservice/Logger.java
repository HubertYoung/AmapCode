package com.alipay.mobile.bqcscanservice;

import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Logger {
    public static final String PTAG = "[Scan][Performance]";
    public static final int SB_BUFFER_LEN = 1024;
    public static final int SLOG_MAX_TIME = 12;
    public static final String TAG = "[Scan]";
    private static Map<String, StringBuffer> a = new ConcurrentHashMap();
    public static final boolean debug = true;
    public static HandlerThread handlerThread = new HandlerThread("Scan-Log-Handler");
    public static ThreadLocal<Integer> integerThreadLocal = new ThreadLocal<>();
    public static Handler scanLogHandler = new Handler(handlerThread.getLooper());
    public static volatile boolean whetherUploadAll = false;

    static {
        handlerThread.start();
    }

    public static void p(String tag, String msg) {
        d(new StringBuilder(PTAG).append(tag).toString(), msg);
    }

    public static void d(String tag, String msg) {
        LoggerFactory.getTraceLogger().debug(new StringBuilder(TAG).append(tag).toString(), msg);
    }

    public static void i(String tag, String msg) {
        LoggerFactory.getTraceLogger().info(new StringBuilder(TAG).append(tag).toString(), msg);
    }

    public static void w(String tag, String msg) {
        LoggerFactory.getTraceLogger().warn(new StringBuilder(TAG).append(tag).toString(), msg);
    }

    public static void e(String tag, String msg) {
        LoggerFactory.getTraceLogger().error(new StringBuilder(TAG).append(tag).toString(), msg);
    }

    public static void e(String tag, String msg, Throwable t) {
        LoggerFactory.getTraceLogger().error(tag, msg, t);
    }

    private static void a(final String result) {
        scanLogHandler.post(new Runnable() {
            public final void run() {
                LoggerFactory.getTraceLogger().warn((String) "[Scan]NewLog", result);
            }
        });
    }

    public static void updateAll() {
        if (!whetherUploadAll) {
            whetherUploadAll = true;
            for (String key : a.keySet()) {
                StringBuffer value = a.get(key);
                String result = value.toString();
                value.setLength(0);
                if (!TextUtils.isEmpty(result)) {
                    a(result);
                }
            }
            whetherUploadAll = false;
        }
    }
}
