package com.alipay.mobile.nebula.wallet;

import com.alipay.mobile.nebula.provider.H5ThreadPoolProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class H5ThreadPoolFactory {
    private static final String TAG = "H5ThreadPoolFactory";
    private static Executor orderedExecutor;
    private static ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;
    private static ThreadPoolExecutor threadPoolExecutor;

    public static class DiscardOldestPolicy extends java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy {
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            super.rejectedExecution(r, e);
            H5Log.d(H5ThreadPoolFactory.TAG, "Reject runnable " + r + " in " + e);
        }
    }

    public static class H5SingleThreadFactory implements ThreadFactory {
        private String threadName;

        public H5SingleThreadFactory(String name) {
            this.threadName = name;
        }

        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName(this.threadName + "_" + thread.getId());
            thread.setPriority(5);
            thread.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
                public void uncaughtException(Thread thread, Throwable ex) {
                    H5Log.e((String) H5ThreadPoolFactory.TAG, "H5SingleThreadFactory catchUncaughtException " + ex);
                }
            });
            return thread;
        }
    }

    static ThreadPoolExecutor getExecutor(String type) {
        H5ThreadPoolProvider provider = (H5ThreadPoolProvider) H5Utils.getProvider(H5ThreadPoolProvider.class.getName());
        if (provider != null) {
            ThreadPoolExecutor executor = provider.getExecutor(type);
            threadPoolExecutor = executor;
            if (executor != null) {
                return threadPoolExecutor;
            }
        }
        return getDefaultExecutor();
    }

    public static synchronized ThreadPoolExecutor getDefaultExecutor() {
        ThreadPoolExecutor threadPoolExecutor2;
        synchronized (H5ThreadPoolFactory.class) {
            try {
                if (threadPoolExecutor == null) {
                    threadPoolExecutor = new ThreadPoolExecutor(8, 32, 3, TimeUnit.SECONDS, new ArrayBlockingQueue(1), new ThreadFactory() {
                        public final Thread newThread(Runnable runnable) {
                            Thread result = new Thread(runnable);
                            result.setName("H5_background_executor_" + result.getId());
                            result.setDaemon(true);
                            return result;
                        }
                    }, new DiscardOldestPolicy());
                }
                threadPoolExecutor2 = threadPoolExecutor;
            }
        }
        return threadPoolExecutor2;
    }

    static ScheduledThreadPoolExecutor getScheduledExecutor() {
        H5ThreadPoolProvider provider = (H5ThreadPoolProvider) H5Utils.getProvider(H5ThreadPoolProvider.class.getName());
        if (provider != null) {
            ScheduledThreadPoolExecutor scheduledExecutor = provider.getScheduledExecutor();
            scheduledThreadPoolExecutor = scheduledExecutor;
            if (scheduledExecutor != null) {
                return scheduledThreadPoolExecutor;
            }
        }
        return getDefaultScheduledExecutor();
    }

    public static synchronized ScheduledThreadPoolExecutor getDefaultScheduledExecutor() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor2;
        synchronized (H5ThreadPoolFactory.class) {
            try {
                if (scheduledThreadPoolExecutor == null) {
                    scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(3, new ThreadFactory() {
                        public final Thread newThread(Runnable runnable) {
                            Thread result = new Thread(runnable);
                            result.setName("H5 Schedule background executor_" + result.getId());
                            result.setDaemon(true);
                            return result;
                        }
                    }, new DiscardOldestPolicy());
                }
                scheduledThreadPoolExecutor2 = scheduledThreadPoolExecutor;
            }
        }
        return scheduledThreadPoolExecutor2;
    }

    public static synchronized Executor getSingleThreadExecutor() {
        Executor executor;
        synchronized (H5ThreadPoolFactory.class) {
            try {
                if (orderedExecutor == null) {
                    orderedExecutor = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, new LinkedBlockingQueue(40), new H5SingleThreadFactory("H5_SingleThreadExecutor_for_log"), new DiscardOldestPolicy());
                }
                executor = orderedExecutor;
            }
        }
        return executor;
    }
}
