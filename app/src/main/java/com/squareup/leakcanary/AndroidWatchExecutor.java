package com.squareup.leakcanary;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.MessageQueue.IdleHandler;
import java.util.concurrent.Executor;

public final class AndroidWatchExecutor implements Executor {
    private static final int DELAY_MILLIS = 5000;
    static final String LEAK_CANARY_THREAD_NAME = "LeakCanary-Heap-Dump";
    /* access modifiers changed from: private */
    public final Handler backgroundHandler;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public AndroidWatchExecutor() {
        HandlerThread handlerThread = new HandlerThread(LEAK_CANARY_THREAD_NAME);
        handlerThread.start();
        this.backgroundHandler = new Handler(handlerThread.getLooper());
    }

    public final void execute(final Runnable runnable) {
        if (isOnMainThread()) {
            executeDelayedAfterIdleUnsafe(runnable);
        } else {
            this.mainHandler.post(new Runnable() {
                public void run() {
                    AndroidWatchExecutor.this.executeDelayedAfterIdleUnsafe(runnable);
                }
            });
        }
    }

    private boolean isOnMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    /* access modifiers changed from: private */
    public void executeDelayedAfterIdleUnsafe(final Runnable runnable) {
        Looper.myQueue().addIdleHandler(new IdleHandler() {
            public boolean queueIdle() {
                AndroidWatchExecutor.this.backgroundHandler.postDelayed(runnable, 5000);
                return false;
            }
        });
    }
}
