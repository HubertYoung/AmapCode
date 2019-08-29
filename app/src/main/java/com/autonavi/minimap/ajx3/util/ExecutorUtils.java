package com.autonavi.minimap.ajx3.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

public class ExecutorUtils {
    private static final Handler sDefaultHandler = new Handler(new DefaultThread().getLooper());
    private static final Handler sHandler = new Handler(Looper.getMainLooper());

    static class DefaultThread extends HandlerThread {
        DefaultThread() {
            super("AJX#ExecutorUtilsThread");
            start();
        }
    }

    public static void postOnUI(Runnable runnable) {
        if (runnable != null) {
            sHandler.post(runnable);
        }
    }

    public static void postDelayedOnUI(Runnable runnable, long j) {
        if (runnable != null) {
            sHandler.postDelayed(runnable, j);
        }
    }

    public static void removeOnUI(Runnable runnable) {
        if (runnable != null) {
            sHandler.removeCallbacks(runnable);
        }
    }

    public static void post(Runnable runnable) {
        if (runnable != null) {
            sDefaultHandler.post(runnable);
        }
    }

    public static void postDelayed(Runnable runnable, long j) {
        if (runnable != null) {
            sDefaultHandler.postDelayed(runnable, j);
        }
    }

    public static void remove(Runnable runnable) {
        if (runnable != null) {
            sDefaultHandler.removeCallbacks(runnable);
        }
    }
}
