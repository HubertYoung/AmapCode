package com.alipay.mobile.framework.service.common.threadpool;

import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class TaskPoolRunnable implements Runnable {
    private static final long a = TimeUnit.SECONDS.toMillis(20);
    private static final long b = TimeUnit.SECONDS.toMillis(60);
    private static final long c = TimeUnit.SECONDS.toMillis(10);
    private static final long d = TimeUnit.MINUTES.toMillis(2);
    private Runnable e;
    private TaskType f;
    private int g;
    private String h;
    private long i;
    private long j;
    private long k;
    private long l;
    private long m;
    private long n;
    private long o;
    private long p;
    private Set<Integer> q;

    public enum TaskType {
        UNKNOWN,
        URGENT_DISPLAY,
        URGENT,
        NORMAL,
        IO,
        RPC,
        MMS_HTTP,
        MMS_DJANGO,
        ORDERED,
        SCHEDULED
    }

    public TaskPoolRunnable(Runnable runnable, TaskType taskType, int threadPriority) {
        if (runnable == null) {
            throw new IllegalArgumentException("runnable is null");
        }
        this.e = runnable;
        this.f = taskType;
        this.g = threadPriority;
        this.h = this.e.getClass().getName();
        this.i = System.currentTimeMillis();
        this.j = SystemClock.uptimeMillis();
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public TaskPoolRunnable(Runnable runnable, TaskType taskType, int threadPriority, Set<Integer> tids) {
        this(runnable, taskType, threadPriority);
        this.q = tids;
    }

    public void run() {
        int i2;
        if (this.q != null) {
            try {
                this.q.add(Integer.valueOf(Process.myTid()));
            } catch (Throwable th) {
            }
        }
        if (this.g > 0 && this.g <= 10) {
            Thread.currentThread().setPriority(this.g);
        }
        this.k = SystemClock.uptimeMillis();
        this.l = System.currentTimeMillis();
        this.o = this.k - this.j;
        if (this.o > a) {
            a(false);
            if (this.o > b) {
                b(false);
            }
        }
        try {
            this.e.run();
            if (i2 <= 0) {
                return;
            }
        } finally {
            this.m = SystemClock.uptimeMillis();
            this.p = this.m - this.k;
            if (this.p > c) {
                a(true);
                if (this.p > d) {
                    b(true);
                }
            }
        }
    }

    public String getTaskName() {
        return this.h;
    }

    private void a(boolean isSpend) {
        String prefix;
        if (isSpend) {
            this.n = System.currentTimeMillis();
            prefix = "spendLongTime ";
        } else if (this.f != TaskType.SCHEDULED) {
            prefix = "waitLongTime ";
        } else {
            return;
        }
        StringBuilder message = new StringBuilder();
        message.append(prefix).append(this.h);
        message.append(", scheduleType: ").append(this.f);
        message.append(", spendTime: ").append(this.p);
        message.append(", waitTime: ").append(this.o);
        message.append(", submitTime: ").append(this.i);
        message.append(", startTime: ").append(this.l);
        message.append(", endTime: ").append(this.n);
        LoggerFactory.getTraceLogger().warn((String) "TaskScheduleService", message.toString());
    }

    private void b(boolean isSpend) {
        if (isSpend || this.f != TaskType.SCHEDULED) {
            TaskPoolDiagnose.waitOrSpendLongTime(isSpend, this.f, "TaskPoolRunnable", this.h, this.o, this.p);
        }
    }
}
