package com.alipay.android.phone.mobilesdk.permission.b;

import android.os.Process;
import android.os.SystemClock;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: PausableRunnable */
public final class b implements Runnable {
    private static boolean b = false;
    private static ReentrantLock c;
    private static Condition d;
    private static AtomicBoolean e = new AtomicBoolean(false);
    private static long f = -1;
    private static int g = 7;
    private Runnable a;

    static {
        ReentrantLock reentrantLock = new ReentrantLock();
        c = reentrantLock;
        d = reentrantLock.newCondition();
    }

    public b(Runnable runnable) {
        this.a = runnable;
    }

    private static void a() {
        c.lock();
        try {
            if (b) {
                if (f <= 0 || SystemClock.elapsedRealtime() - f <= TimeUnit.SECONDS.toMillis((long) g)) {
                    d.await((long) g, TimeUnit.SECONDS);
                } else {
                    b();
                    return;
                }
            }
            c.unlock();
        } catch (InterruptedException e2) {
            Thread.currentThread().interrupt();
        } finally {
            c.unlock();
        }
    }

    private static void b() {
        c.lock();
        try {
            f = -1;
            b = false;
            d.signalAll();
        } finally {
            c.unlock();
        }
    }

    public final void run() {
        a();
        if (e.get()) {
            Process.setThreadPriority(19);
            c();
        }
        if (this.a != null) {
            this.a.run();
        }
    }

    private static void c() {
        try {
            Thread.sleep((long) (new Random().nextInt(500) + 100));
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }
}
