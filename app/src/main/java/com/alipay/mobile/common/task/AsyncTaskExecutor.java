package com.alipay.mobile.common.task;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.task.pipeline.NamedRunnable;
import com.alipay.mobile.common.task.transaction.Transaction;
import com.alipay.mobile.common.task.transaction.TransactionExecutor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AsyncTaskExecutor {
    public static AsyncTaskExecutor INSTANCE = new AsyncTaskExecutor();
    public static final String TAG = "AsyncTaskExecutor";
    private static final int a;
    private static final int b;
    private static final ThreadFactory c = new ThreadFactory() {
        private final AtomicInteger a = new AtomicInteger(0);

        {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }

        public final Thread newThread(Runnable r) {
            String name = "AsyncTaskExecutor_thread_" + this.a.incrementAndGet();
            Log.w(AsyncTaskExecutor.TAG, "ThreadFactory.newThread(" + name + ")");
            Thread thread = new Thread(r, name);
            thread.setPriority(1);
            return thread;
        }
    };
    ThreadPoolExecutor PARALLEL_EXECUTOR;
    ScheduledThreadPoolExecutor SCHEDULED_EXECUTOR;
    SerialExecutor SERIAL_EXECUTOR;
    final TransactionExecutor TRANSACTION_EXECUTOR = new TransactionExecutor();

    static {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        a = availableProcessors;
        b = availableProcessors + 1;
    }

    private AsyncTaskExecutor() {
        try {
            Class QUINOX_ASYNCTASK_EXECUTOR = Class.forName("com.alipay.mobile.quinox.asynctask.AsyncTaskExecutor");
            Method getInstance = QUINOX_ASYNCTASK_EXECUTOR.getDeclaredMethod("getInstance", new Class[0]);
            Method getExecutor = QUINOX_ASYNCTASK_EXECUTOR.getDeclaredMethod("getExecutor", new Class[0]);
            Object quinoxAsyncTaskExecutor = getInstance.invoke(null, new Object[0]);
            this.PARALLEL_EXECUTOR = (ThreadPoolExecutor) getExecutor.invoke(quinoxAsyncTaskExecutor, new Object[0]);
            this.SERIAL_EXECUTOR = new SerialExecutor(this.PARALLEL_EXECUTOR);
            Field QUINOX_SCHEDULED_EXECUTOR = QUINOX_ASYNCTASK_EXECUTOR.getDeclaredField("SCHEDULED_EXECUTOR");
            QUINOX_SCHEDULED_EXECUTOR.setAccessible(true);
            this.SCHEDULED_EXECUTOR = (ScheduledThreadPoolExecutor) QUINOX_SCHEDULED_EXECUTOR.get(quinoxAsyncTaskExecutor);
            LoggerFactory.getTraceLogger().info(TAG, "AsyncTaskExecutor in common share with quinox success!");
        } catch (Throwable th) {
            this.PARALLEL_EXECUTOR = (ThreadPoolExecutor) Executors.newCachedThreadPool(c);
            this.SERIAL_EXECUTOR = new SerialExecutor(this.PARALLEL_EXECUTOR);
            this.SCHEDULED_EXECUTOR = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(b, c);
            LoggerFactory.getTraceLogger().info(TAG, "AsyncTaskExecutor in common share with quinox failed, use default.");
        }
        this.SCHEDULED_EXECUTOR.setKeepAliveTime(10, TimeUnit.MILLISECONDS);
        this.SCHEDULED_EXECUTOR.allowCoreThreadTimeOut(true);
        this.SCHEDULED_EXECUTOR.setRejectedExecutionHandler(new CallerRunsPolicy());
        this.PARALLEL_EXECUTOR.setRejectedExecutionHandler(new CallerRunsPolicy());
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static AsyncTaskExecutor getInstance() {
        return INSTANCE;
    }

    public final Executor getExecutor() {
        return this.PARALLEL_EXECUTOR;
    }

    @Deprecated
    public void executeSerially(Runnable task) {
        executeSerially(task, "");
    }

    public void executeSerially(Runnable task, String threadName) {
        Log.v(TAG, "AsyncTaskExecutor.executeSerially(Runnable, threadName=" + threadName + ")");
        this.SERIAL_EXECUTOR.execute(NamedRunnable.TASK_POOL.obtain(task, threadName));
    }

    @Deprecated
    public void execute(Runnable task) {
        execute(task, "");
    }

    public void execute(Runnable task, String threadName) {
        Log.v(TAG, "AsyncTaskExecutor.execute(Runnable, threadName=" + threadName + ")");
        this.PARALLEL_EXECUTOR.execute(NamedRunnable.TASK_POOL.obtain(task, threadName));
    }

    @Deprecated
    public ScheduledFuture<?> schedule(Runnable task, long delay, TimeUnit unit) {
        return schedule(task, "", delay, unit);
    }

    public ScheduledFuture<?> schedule(Runnable task, String threadName, long delay, TimeUnit unit) {
        Log.v(TAG, "AsyncTaskExecutor.schedule(Runnable, threadName=" + threadName + ")");
        return this.SCHEDULED_EXECUTOR.schedule(NamedRunnable.TASK_POOL.obtain(task, threadName), delay, unit);
    }

    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long initialDelay, long period, TimeUnit unit) {
        Log.v(TAG, "AsyncTaskExecutor.scheduleAtFixedRate(Runnable)");
        return this.SCHEDULED_EXECUTOR.scheduleAtFixedRate(task, initialDelay, period, unit);
    }

    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, long initialDelay, long delay, TimeUnit unit) {
        return this.SCHEDULED_EXECUTOR.scheduleWithFixedDelay(task, initialDelay, delay, unit);
    }

    public void shutdown() {
        this.TRANSACTION_EXECUTOR.shutdown();
        this.SERIAL_EXECUTOR.shutdown();
        this.PARALLEL_EXECUTOR.shutdown();
        this.SCHEDULED_EXECUTOR.shutdown();
    }

    public String addTransaction(Transaction tr) {
        return this.TRANSACTION_EXECUTOR.addTransaction(tr);
    }

    public void removeTransaction(String id) {
        this.TRANSACTION_EXECUTOR.removeTransaction(id);
    }
}
