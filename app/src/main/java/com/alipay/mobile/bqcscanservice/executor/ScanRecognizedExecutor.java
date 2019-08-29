package com.alipay.mobile.bqcscanservice.executor;

import android.util.Log;
import com.alipay.mobile.bqcscanservice.Logger;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ScanRecognizedExecutor {
    public static final String TAG = "ScanExecutor";
    private static ThreadPoolExecutor a;

    public static void open() {
        a = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        Logger.d(TAG, "Open Successfully : " + a);
    }

    public static void execute(Runnable runnable) {
        if (a != null) {
            a.execute(runnable);
        } else {
            Log.w(TAG, "Executor is dead", new Throwable());
        }
    }

    public static boolean isEmpty() {
        if (a == null || a.getActiveCount() != 0) {
            return false;
        }
        return true;
    }

    public static void close() {
        if (a != null && !a.isShutdown()) {
            try {
                a.shutdownNow();
                Logger.d(TAG, "Shutdown Successfully : " + a);
                a = null;
            } catch (Exception e) {
                Logger.e(TAG, "Shutdown executor failed");
            }
        }
    }
}
