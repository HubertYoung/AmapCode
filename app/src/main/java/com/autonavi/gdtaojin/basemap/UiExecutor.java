package com.autonavi.gdtaojin.basemap;

import android.os.Handler;
import android.os.Looper;

public class UiExecutor {
    private static final Handler sHandler = new Handler(Looper.getMainLooper());

    private UiExecutor() {
    }

    public static void post(Runnable runnable) {
        if (runnable != null) {
            sHandler.post(runnable);
        }
    }

    public static void postDelayed(Runnable runnable, long j) {
        if (runnable != null) {
            sHandler.postDelayed(runnable, j);
        }
    }

    public static void removeCallbacks(Runnable runnable) {
        if (runnable != null) {
            sHandler.removeCallbacks(runnable);
        }
    }
}
