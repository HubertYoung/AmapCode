package com.alipay.android.phone.mobilesdk.permission.b;

import android.os.SystemClock;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: PausableThreadPoolExecutor */
public final class d extends ThreadPoolExecutor {
    private static int e = 7;
    private boolean a;
    private ReentrantLock b = new ReentrantLock();
    private Condition c = this.b.newCondition();
    private long d = -1;

    public d(TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(0, Integer.MAX_VALUE, 60, unit, workQueue, threadFactory);
    }

    /* access modifiers changed from: protected */
    public final void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        this.b.lock();
        try {
            if (this.a) {
                if (this.d <= 0 || SystemClock.elapsedRealtime() - this.d <= TimeUnit.SECONDS.toMillis((long) e)) {
                    this.c.await((long) e, TimeUnit.SECONDS);
                } else {
                    a();
                    return;
                }
            }
            this.b.unlock();
        } catch (InterruptedException e2) {
            t.interrupt();
        } finally {
            this.b.unlock();
        }
    }

    private void a() {
        this.b.lock();
        try {
            this.a = false;
            this.d = -1;
            this.c.signalAll();
        } finally {
            this.b.unlock();
        }
    }
}
