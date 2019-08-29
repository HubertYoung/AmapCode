package com.alipay.mobile.quinox.asynctask;

import android.os.SystemClock;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PausableScheduledThreadPool extends ScheduledThreadPoolExecutor {
    private static volatile boolean isPaused = false;
    private static volatile long lastPauseTime = -1;
    private static int mAwaitTime = 7;
    private static ReentrantLock pauseLock;
    private static Condition unPaused;

    static {
        ReentrantLock reentrantLock = new ReentrantLock();
        pauseLock = reentrantLock;
        unPaused = reentrantLock.newCondition();
    }

    public PausableScheduledThreadPool(int i, ThreadFactory threadFactory) {
        super(i, threadFactory);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Can't wrap try/catch for region: R(2:16|17) */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0042, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        r5.interrupt();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0048, code lost:
        pauseLock.unlock();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x004d, code lost:
        throw r5;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:16:0x0044 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void beforeExecute(java.lang.Thread r5, java.lang.Runnable r6) {
        /*
            r4 = this;
            super.beforeExecute(r5, r6)
            java.util.concurrent.locks.ReentrantLock r6 = pauseLock
            r6.lock()
            boolean r6 = isPaused     // Catch:{ InterruptedException -> 0x0044 }
            if (r6 == 0) goto L_0x003c
            long r0 = lastPauseTime     // Catch:{ InterruptedException -> 0x0044 }
            r2 = 0
            int r6 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r6 <= 0) goto L_0x0032
            long r0 = android.os.SystemClock.elapsedRealtime()     // Catch:{ InterruptedException -> 0x0044 }
            long r2 = lastPauseTime     // Catch:{ InterruptedException -> 0x0044 }
            r6 = 0
            long r0 = r0 - r2
            java.util.concurrent.TimeUnit r6 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ InterruptedException -> 0x0044 }
            int r2 = mAwaitTime     // Catch:{ InterruptedException -> 0x0044 }
            long r2 = (long) r2     // Catch:{ InterruptedException -> 0x0044 }
            long r2 = r6.toMillis(r2)     // Catch:{ InterruptedException -> 0x0044 }
            int r6 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r6 <= 0) goto L_0x0032
            resume()     // Catch:{ InterruptedException -> 0x0044 }
            java.util.concurrent.locks.ReentrantLock r5 = pauseLock
            r5.unlock()
            return
        L_0x0032:
            java.util.concurrent.locks.Condition r6 = unPaused     // Catch:{ InterruptedException -> 0x0044 }
            int r0 = mAwaitTime     // Catch:{ InterruptedException -> 0x0044 }
            long r0 = (long) r0     // Catch:{ InterruptedException -> 0x0044 }
            java.util.concurrent.TimeUnit r2 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ InterruptedException -> 0x0044 }
            r6.await(r0, r2)     // Catch:{ InterruptedException -> 0x0044 }
        L_0x003c:
            java.util.concurrent.locks.ReentrantLock r5 = pauseLock
            r5.unlock()
            return
        L_0x0042:
            r5 = move-exception
            goto L_0x0048
        L_0x0044:
            r5.interrupt()     // Catch:{ all -> 0x0042 }
            goto L_0x003c
        L_0x0048:
            java.util.concurrent.locks.ReentrantLock r6 = pauseLock
            r6.unlock()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.quinox.asynctask.PausableScheduledThreadPool.beforeExecute(java.lang.Thread, java.lang.Runnable):void");
    }

    public static void pause() {
        pauseLock.lock();
        try {
            isPaused = true;
            lastPauseTime = SystemClock.elapsedRealtime();
        } finally {
            pauseLock.unlock();
        }
    }

    public static void resume() {
        pauseLock.lock();
        try {
            isPaused = false;
            lastPauseTime = -1;
            unPaused.signalAll();
        } finally {
            pauseLock.unlock();
        }
    }

    public static void setAwaitTime(int i) {
        mAwaitTime = i;
    }
}
