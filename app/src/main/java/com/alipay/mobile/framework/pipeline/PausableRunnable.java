package com.alipay.mobile.framework.pipeline;

import android.os.Process;
import android.os.SystemClock;
import android.text.TextUtils;
import com.alipay.mobile.framework.pipeline.analysis.AnalysedRunnable;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PausableRunnable extends AnalysedRunnable {
    private static boolean a = false;
    private static ReentrantLock b;
    private static Condition c;
    private static long d = -1;
    private static AtomicBoolean e = new AtomicBoolean(false);
    private static List<String> f = new ArrayList();
    private static int h = 7;
    private String g;

    static {
        ReentrantLock reentrantLock = new ReentrantLock();
        b = reentrantLock;
        c = reentrantLock.newCondition();
    }

    public PausableRunnable(Runnable runnable) {
        this(runnable, "");
    }

    public PausableRunnable(Runnable runnable, String threadName) {
        super(runnable);
        this.g = threadName;
    }

    public void run() {
        if (!f.contains(this.g)) {
            a();
            if (e.get()) {
                Process.setThreadPriority(19);
                b();
            }
        }
        super.run();
    }

    private static void a() {
        b.lock();
        try {
            if (a) {
                if (d <= 0 || SystemClock.elapsedRealtime() - d <= TimeUnit.SECONDS.toMillis((long) h)) {
                    c.await((long) h, TimeUnit.SECONDS);
                } else {
                    resume();
                    try {
                        Class.forName("com.alipay.mobile.monitor.api.ClientMonitorAgent").getDeclaredMethod("addAppIdExtraInfo", new Class[]{String.class, String.class, String.class}).invoke(null, new Object[]{H5Utils.SCAN_APP_ID, "pauseOverTime", "true"});
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    return;
                }
            }
            b.unlock();
        } catch (InterruptedException e3) {
            Thread.currentThread().interrupt();
        } finally {
            b.unlock();
        }
    }

    public static void syncWhiteList(String whiteListStr) {
        if (TextUtils.isEmpty(whiteListStr)) {
            return;
        }
        if (!whiteListStr.contains(",")) {
            f.add(whiteListStr);
            return;
        }
        String[] threadNames = whiteListStr.split(",");
        if (threadNames.length > 1) {
            Collections.addAll(f, threadNames);
        }
    }

    public static void pause() {
        b.lock();
        try {
            d = SystemClock.elapsedRealtime();
            a = true;
        } finally {
            b.unlock();
        }
    }

    public static void resume() {
        b.lock();
        try {
            d = -1;
            a = false;
            c.signalAll();
        } finally {
            b.unlock();
        }
    }

    public static void setAwaitTime(int awaitTime) {
        h = awaitTime;
    }

    public static void setAdjustPriority(boolean adjust) {
        e.set(adjust);
    }

    private static void b() {
        try {
            Thread.sleep((long) (new Random().nextInt(500) + 100));
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }
}
