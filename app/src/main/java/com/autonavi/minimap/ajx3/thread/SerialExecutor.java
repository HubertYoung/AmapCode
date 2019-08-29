package com.autonavi.minimap.ajx3.thread;

import android.os.AsyncTask;
import java.util.ArrayDeque;
import java.util.concurrent.Executor;

public class SerialExecutor implements Executor {
    private Runnable mActive;
    private final ArrayDeque<Runnable> mTasks = new ArrayDeque<>();

    public synchronized void execute(final Runnable runnable) {
        this.mTasks.offer(new Runnable() {
            public void run() {
                try {
                    runnable.run();
                } finally {
                    SerialExecutor.this.scheduleNext();
                }
            }
        });
        if (this.mActive == null) {
            scheduleNext();
        }
    }

    /* access modifiers changed from: protected */
    public synchronized void scheduleNext() {
        Runnable poll = this.mTasks.poll();
        this.mActive = poll;
        if (poll != null) {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(this.mActive);
        }
    }
}
