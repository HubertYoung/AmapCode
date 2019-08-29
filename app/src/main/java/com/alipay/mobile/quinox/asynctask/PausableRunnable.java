package com.alipay.mobile.quinox.asynctask;

import android.os.Process;
import android.os.SystemClock;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PausableRunnable implements Runnable {
    private static boolean isPaused = false;
    private static long lastPauseTime = -1;
    private static ReentrantLock pauseLock = null;
    private static AtomicBoolean sAdjustPriority = new AtomicBoolean(false);
    private static int sAwaitTime = 7;
    private static Condition unPaused;
    private Runnable mRunnable;

    static {
        ReentrantLock reentrantLock = new ReentrantLock();
        pauseLock = reentrantLock;
        unPaused = reentrantLock.newCondition();
    }

    public PausableRunnable(Runnable runnable) {
        this.mRunnable = runnable;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003f, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        java.lang.Thread.currentThread().interrupt();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0049, code lost:
        pauseLock.unlock();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x004e, code lost:
        throw r0;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:16:0x0041 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void timeWait() {
        /*
            r5 = this;
            java.util.concurrent.locks.ReentrantLock r0 = pauseLock
            r0.lock()
            boolean r0 = isPaused     // Catch:{ InterruptedException -> 0x0041 }
            if (r0 == 0) goto L_0x0039
            long r0 = lastPauseTime     // Catch:{ InterruptedException -> 0x0041 }
            r2 = 0
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 <= 0) goto L_0x002f
            long r0 = android.os.SystemClock.elapsedRealtime()     // Catch:{ InterruptedException -> 0x0041 }
            long r2 = lastPauseTime     // Catch:{ InterruptedException -> 0x0041 }
            r4 = 0
            long r0 = r0 - r2
            java.util.concurrent.TimeUnit r2 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ InterruptedException -> 0x0041 }
            int r3 = sAwaitTime     // Catch:{ InterruptedException -> 0x0041 }
            long r3 = (long) r3     // Catch:{ InterruptedException -> 0x0041 }
            long r2 = r2.toMillis(r3)     // Catch:{ InterruptedException -> 0x0041 }
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 <= 0) goto L_0x002f
            resume()     // Catch:{ InterruptedException -> 0x0041 }
            java.util.concurrent.locks.ReentrantLock r0 = pauseLock
            r0.unlock()
            return
        L_0x002f:
            java.util.concurrent.locks.Condition r0 = unPaused     // Catch:{ InterruptedException -> 0x0041 }
            int r1 = sAwaitTime     // Catch:{ InterruptedException -> 0x0041 }
            long r1 = (long) r1     // Catch:{ InterruptedException -> 0x0041 }
            java.util.concurrent.TimeUnit r3 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ InterruptedException -> 0x0041 }
            r0.await(r1, r3)     // Catch:{ InterruptedException -> 0x0041 }
        L_0x0039:
            java.util.concurrent.locks.ReentrantLock r0 = pauseLock
            r0.unlock()
            return
        L_0x003f:
            r0 = move-exception
            goto L_0x0049
        L_0x0041:
            java.lang.Thread r0 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x003f }
            r0.interrupt()     // Catch:{ all -> 0x003f }
            goto L_0x0039
        L_0x0049:
            java.util.concurrent.locks.ReentrantLock r1 = pauseLock
            r1.unlock()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.quinox.asynctask.PausableRunnable.timeWait():void");
    }

    public static void pause() {
        pauseLock.lock();
        try {
            lastPauseTime = SystemClock.elapsedRealtime();
            isPaused = true;
        } finally {
            pauseLock.unlock();
        }
    }

    public static void resume() {
        pauseLock.lock();
        try {
            lastPauseTime = -1;
            isPaused = false;
            unPaused.signalAll();
        } finally {
            pauseLock.unlock();
        }
    }

    public static void setAwaitTime(int i) {
        sAwaitTime = i;
    }

    public void run() {
        timeWait();
        if (sAdjustPriority.get()) {
            Process.setThreadPriority(19);
            randomDelay();
        }
        if (this.mRunnable != null) {
            this.mRunnable.run();
        }
    }

    public static void setAdjustPriority(boolean z) {
        sAdjustPriority.set(z);
    }

    private void randomDelay() {
        try {
            Thread.sleep((long) (new Random().nextInt(500) + 100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
