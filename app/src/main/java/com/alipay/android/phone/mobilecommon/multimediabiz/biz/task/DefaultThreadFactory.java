package com.alipay.android.phone.mobilecommon.multimediabiz.biz.task;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultThreadFactory implements ThreadFactory {
    private static final AtomicInteger a = new AtomicInteger(1);
    private final AtomicInteger b;
    private final String c;
    private final int d;

    public DefaultThreadFactory() {
        this(3, "mmtask-");
    }

    public DefaultThreadFactory(String threadName) {
        this(3, threadName);
    }

    public DefaultThreadFactory(int threadPriority, String threadNamePrefix) {
        this.b = new AtomicInteger(1);
        this.d = threadPriority;
        this.c = threadNamePrefix + "-" + a.getAndIncrement() + "-t-";
    }

    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, this.c + this.b.getAndIncrement());
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        t.setPriority(this.d);
        return t;
    }
}
