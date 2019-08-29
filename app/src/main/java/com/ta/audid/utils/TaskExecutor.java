package com.ta.audid.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskExecutor {
    private static TaskExecutor instance;
    /* access modifiers changed from: private */
    public static final AtomicInteger integer = new AtomicInteger();
    private static ScheduledExecutorService threadPoolExecutor;

    static class TBThreadFactory implements ThreadFactory {
        private int priority;

        public TBThreadFactory(int i) {
            this.priority = i;
        }

        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "utdid:".concat(String.valueOf(TaskExecutor.integer.getAndIncrement())));
            thread.setPriority(this.priority);
            return thread;
        }
    }

    private static synchronized ScheduledExecutorService getDefaultThreadPoolExecutor() {
        ScheduledExecutorService scheduledExecutorService;
        synchronized (TaskExecutor.class) {
            if (threadPoolExecutor == null) {
                threadPoolExecutor = Executors.newScheduledThreadPool(3, new TBThreadFactory(1));
            }
            scheduledExecutorService = threadPoolExecutor;
        }
        return scheduledExecutorService;
    }

    public static synchronized TaskExecutor getInstance() {
        TaskExecutor taskExecutor;
        synchronized (TaskExecutor.class) {
            try {
                if (instance == null) {
                    instance = new TaskExecutor();
                }
                taskExecutor = instance;
            }
        }
        return taskExecutor;
    }

    public final ScheduledFuture schedule(ScheduledFuture scheduledFuture, Runnable runnable, long j) {
        if (scheduledFuture != null && !scheduledFuture.isDone()) {
            scheduledFuture.cancel(true);
        }
        return getDefaultThreadPoolExecutor().schedule(runnable, j, TimeUnit.MILLISECONDS);
    }

    public final ScheduledFuture scheduleAtFixedRate(ScheduledFuture scheduledFuture, Runnable runnable, long j, long j2) {
        if (scheduledFuture != null && !scheduledFuture.isDone()) {
            scheduledFuture.cancel(false);
        }
        return getDefaultThreadPoolExecutor().scheduleAtFixedRate(runnable, j, j2, TimeUnit.MILLISECONDS);
    }

    public void submit(Runnable runnable) {
        try {
            getDefaultThreadPoolExecutor().submit(runnable);
        } catch (Throwable th) {
            UtdidLogger.e("", th, new Object[0]);
        }
    }
}
