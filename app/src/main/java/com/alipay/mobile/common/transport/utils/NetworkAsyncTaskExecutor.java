package com.alipay.mobile.common.transport.utils;

import android.annotation.TargetApi;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.TimeUnit;

@TargetApi(9)
public class NetworkAsyncTaskExecutor {
    private static final ThreadPoolExecutor a = new ThreadPoolExecutor(7, Integer.MAX_VALUE, 2, TimeUnit.SECONDS, new SynchronousQueue(), new DiscardOldestPolicy());
    private static final ThreadPoolExecutor b = new ThreadPoolExecutor(4, 6, 10, TimeUnit.SECONDS, new LinkedBlockingQueue(60), new DiscardOldestPolicy());
    private static final ThreadPoolExecutor c = new ThreadPoolExecutor(1, 1, 30, TimeUnit.SECONDS, new LinkedBlockingQueue(60), new DiscardOldestPolicy());
    private static final ThreadPoolExecutor d = new ThreadPoolExecutor(3, 6, 10, TimeUnit.SECONDS, new LinkedBlockingQueue(60), new DiscardOldestPolicy());
    private static final ThreadPoolExecutor e = new ThreadPoolExecutor(3, 3, 10, TimeUnit.SECONDS, new LinkedBlockingQueue(60), new DiscardOldestPolicy());
    private static final ThreadPoolExecutor f = new ThreadPoolExecutor(1, 1, 30, TimeUnit.SECONDS, new LinkedBlockingQueue(60), new DiscardOldestPolicy());
    private static final ThreadPoolExecutor g = new ThreadPoolExecutor(1, 1, 30, TimeUnit.SECONDS, new LinkedBlockingQueue(60), new DiscardOldestPolicy());
    private static final ScheduledThreadPoolExecutor h = new ScheduledThreadPoolExecutor(1);
    /* access modifiers changed from: private */
    public static volatile int i = 0;

    class NetworkCallable<V> implements Callable<V> {
        Callable<V> callable;

        private NetworkCallable(Callable<V> callable2) {
            this.callable = callable2;
        }

        public V call() {
            try {
                if (NetworkAsyncTaskExecutor.i >= Integer.MAX_VALUE) {
                    NetworkAsyncTaskExecutor.i = 0;
                }
                int taskId = NetworkAsyncTaskExecutor.access$204();
                LogCatUtil.debug("NetworkRunnable", "taskId=[" + taskId + "] start execute. class=[" + this.callable.getClass().getName() + "]");
                V call = this.callable.call();
                LogCatUtil.debugOrLose("NetworkRunnable", "taskId=[" + taskId + "] execute finish.");
                return call;
            } catch (Exception e) {
                LogCatUtil.error((String) "NetworkRunnable", "taskId=[" + -1 + "] call exception. " + e.toString());
                throw e;
            } catch (Throwable th) {
                LogCatUtil.debugOrLose("NetworkRunnable", "taskId=[" + -1 + "] execute finish.");
                throw th;
            }
        }
    }

    class NetworkRunnable implements Runnable {
        Runnable runnable;

        private NetworkRunnable(Runnable runnable2) {
            this.runnable = runnable2;
        }

        public void run() {
            Thread.currentThread().setPriority(5);
            int taskId = -1;
            try {
                if (this.runnable == null) {
                    LogCatUtil.error((String) "NetworkRunnable", (String) " this.runnable is null !");
                    LogCatUtil.debugOrLose("NetworkRunnable", "taskId=[-1] execute finish.");
                    return;
                }
                if (NetworkAsyncTaskExecutor.i >= Integer.MAX_VALUE) {
                    NetworkAsyncTaskExecutor.i = 0;
                }
                taskId = NetworkAsyncTaskExecutor.access$204();
                LogCatUtil.debug("NetworkRunnable", "taskId=[" + taskId + "] start execute. class=[" + this.runnable.getClass().getName() + "]");
                this.runnable.run();
            } catch (Throwable e) {
                LogCatUtil.error("NetworkRunnable", "taskId=[" + taskId + "] run exception. ", e);
            } finally {
                r3 = "NetworkRunnable";
                r5 = "taskId=[";
                r5 = "] execute finish.";
                LogCatUtil.debugOrLose(r3, taskId + r5);
            }
        }
    }

    class NetworkThreadFactory implements ThreadFactory {
        public String name = "";
        public ThreadPoolExecutor threadPoolExecutor;

        NetworkThreadFactory(String name2, ThreadPoolExecutor threadPoolExecutor2) {
            this.name = name2;
            this.threadPoolExecutor = threadPoolExecutor2;
        }

        public Thread newThread(Runnable r) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.name);
            if (this.threadPoolExecutor != null) {
                stringBuilder.append(new StringBuilder(MetaRecord.LOG_SEPARATOR).append(this.threadPoolExecutor.getActiveCount() + 1).toString());
            }
            return new Thread(r, stringBuilder.toString());
        }
    }

    static /* synthetic */ int access$204() {
        int i2 = i + 1;
        i = i2;
        return i2;
    }

    static {
        a.setThreadFactory(new NetworkThreadFactory("NetworkThread", a));
        a.allowCoreThreadTimeOut(true);
        h.setThreadFactory(new NetworkThreadFactory("NetworkSchedule", h));
        h.setKeepAliveTime(6, TimeUnit.SECONDS);
        h.allowCoreThreadTimeOut(true);
        c.setThreadFactory(new NetworkThreadFactory("SeriNetworkThread", c));
        c.allowCoreThreadTimeOut(true);
        g.setThreadFactory(new NetworkThreadFactory("HSeriNetworkThread", g));
        g.allowCoreThreadTimeOut(true);
        b.setThreadFactory(new NetworkThreadFactory("IONetworkThread", b));
        b.allowCoreThreadTimeOut(true);
        d.setThreadFactory(new NetworkThreadFactory("LowPriNetworkThread", d));
        d.allowCoreThreadTimeOut(true);
        e.setThreadFactory(new NetworkThreadFactory("LazyNetworkThread", e));
        e.allowCoreThreadTimeOut(true);
        f.setThreadFactory(new NetworkThreadFactory("DispatchNetworkThread", f));
        f.allowCoreThreadTimeOut(true);
    }

    public static final void executeIO(Runnable runnable) {
        try {
            b.execute(new NetworkRunnable(runnable));
        } catch (Throwable e2) {
            LogCatUtil.warn("NetworkAsyncTaskExecutor", "executeIO fail", e2);
        }
    }

    public static final void executeLowPri(Runnable runnable) {
        try {
            d.execute(new NetworkRunnable(runnable));
        } catch (Throwable e2) {
            LogCatUtil.warn("NetworkAsyncTaskExecutor", "executeLowPri fail", e2);
        }
    }

    public static final void executeLazy(Runnable runnable) {
        try {
            e.execute(new NetworkRunnable(runnable));
        } catch (Throwable e2) {
            LogCatUtil.warn("NetworkAsyncTaskExecutor", "executeLazy fail", e2);
        }
    }

    public static final void executeDispatch(Runnable runnable) {
        try {
            f.execute(new NetworkRunnable(runnable));
        } catch (Throwable e2) {
            LogCatUtil.warn("NetworkAsyncTaskExecutor", "executeDispatch fail", e2);
        }
    }

    public static final void execute(Runnable runnable) {
        try {
            a.execute(new NetworkRunnable(runnable));
        } catch (Throwable e2) {
            LogCatUtil.warn("NetworkAsyncTaskExecutor", "execute fail", e2);
        }
    }

    public static final Future<?> submit(Runnable runnable) {
        return a.submit(new NetworkRunnable(runnable));
    }

    public static final Future<?> submitLazy(Runnable runnable) {
        return e.submit(new NetworkRunnable(runnable));
    }

    public static <T> Future<T> submit(Callable<T> callable) {
        return a.submit(new NetworkCallable(callable));
    }

    public static final void executeSerial(Runnable runnable) {
        try {
            c.execute(new NetworkRunnable(runnable));
        } catch (Throwable e2) {
            LogCatUtil.warn("NetworkAsyncTaskExecutor", "executeSerial fail", e2);
        }
    }

    public static final void executeHighSerial(Runnable runnable) {
        try {
            g.execute(new NetworkRunnable(runnable));
        } catch (Throwable e2) {
            LogCatUtil.warn("NetworkAsyncTaskExecutor", "executeHighSerial fail", e2);
        }
    }

    public static final Future<?> submitSerial(Runnable runnable) {
        return c.submit(new NetworkRunnable(runnable));
    }

    public static <T> Future<T> submitSerial(Callable<T> callable) {
        return c.submit(new NetworkCallable(callable));
    }

    public static ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        return h.schedule(new NetworkRunnable(command), delay, unit);
    }

    public static <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        return h.schedule(new NetworkCallable(callable), delay, unit);
    }

    public static ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        return h.scheduleAtFixedRate(new NetworkRunnable(command), initialDelay, period, unit);
    }

    public static ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        return h.scheduleWithFixedDelay(new NetworkRunnable(command), initialDelay, delay, unit);
    }
}
