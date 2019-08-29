package com.alibaba.analytics.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskExecutor {
    public static final int MSG_ABTEST = 3;
    public static final int MSG_BACKGROUND = 4;
    public static final int MSG_CLEAN = 5;
    public static final int MSG_CLOSE_DB = 9;
    public static final int MSG_COMMIT = 6;
    public static final int MSG_CONFIG = 7;
    public static final int MSG_ORANGE_CONFIG = 8;
    public static final int MSG_STORE = 1;
    public static final int MSG_UPLOAD = 2;
    protected static final String TAG = "TaskExecutor";
    public static TaskExecutor instance = null;
    /* access modifiers changed from: private */
    public static final AtomicInteger integer = new AtomicInteger();
    private static int prop = 1;
    private static ScheduledExecutorService threadPoolExecutor;

    static class TBThreadFactory implements ThreadFactory {
        private int priority;

        public TBThreadFactory(int i) {
            this.priority = i;
        }

        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "AppMonitor:".concat(String.valueOf(TaskExecutor.integer.getAndIncrement())));
            thread.setPriority(this.priority);
            return thread;
        }
    }

    private static synchronized ScheduledExecutorService getDefaulThreadPoolExecutor() {
        ScheduledExecutorService scheduledExecutorService;
        synchronized (TaskExecutor.class) {
            if (threadPoolExecutor == null) {
                threadPoolExecutor = Executors.newScheduledThreadPool(3, new TBThreadFactory(prop));
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
        return getDefaulThreadPoolExecutor().schedule(runnable, j, TimeUnit.MILLISECONDS);
    }

    public final ScheduledFuture scheduleAtFixedRate(ScheduledFuture scheduledFuture, Runnable runnable, long j) {
        if (scheduledFuture != null && !scheduledFuture.isDone()) {
            scheduledFuture.cancel(false);
        }
        return getDefaulThreadPoolExecutor().scheduleAtFixedRate(runnable, 1000, j, TimeUnit.MILLISECONDS);
    }

    public void submit(Runnable runnable) {
        try {
            getDefaulThreadPoolExecutor().submit(runnable);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
