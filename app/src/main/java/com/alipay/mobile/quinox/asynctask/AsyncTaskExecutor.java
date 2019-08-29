package com.alipay.mobile.quinox.asynctask;

import com.alipay.mobile.quinox.utils.StringUtil;
import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public final class AsyncTaskExecutor {
    static final int CORE_POOL_SIZE;
    static final int CPU_COUNT;
    private static AsyncTaskExecutor INSTANCE = new AsyncTaskExecutor();
    static final int MAXIMUM_POOL_SIZE = ((CPU_COUNT * 3) + 1);
    static final String NAME_GLOBAL_STANDARD_PIPELINE = "GlobalStandardPipeline";
    public static final String TAG = "AsyTskExecutor";
    private static final ThreadFactory THREADFACTORY = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(0);

        public final Thread newThread(Runnable runnable) {
            if (runnable == null) {
                throw new IllegalArgumentException("null == runnable");
            }
            StringBuilder sb = new StringBuilder("AsyTskExecutor_thread#");
            sb.append(this.mCount.incrementAndGet());
            sb.append("_");
            Thread thread = new Thread(new DelayableRunnable(runnable), sb.toString());
            thread.setPriority(1);
            return thread;
        }
    };
    /* access modifiers changed from: private */
    public static volatile AtomicBoolean sDelayRun = new AtomicBoolean(false);
    final ThreadPoolExecutor PARALLEL_EXECUTOR;
    final ScheduledThreadPoolExecutor SCHEDULED_EXECUTOR = new PausableScheduledThreadPool(CORE_POOL_SIZE, THREADFACTORY);
    final ScheduleTimer SCHEDULED_TIMER = new ScheduleTimer();
    final ConcurrentHashMap<String, StandardPipeline> SERIAL_EXECUTORS = new ConcurrentHashMap<>();
    StandardPipeline mGlobalStandardPipline;
    TransactionPipeline mTransactionExecutor;

    static class DelayableRunnable implements Runnable {
        private Runnable mRunnable;

        DelayableRunnable(Runnable runnable) {
            this.mRunnable = runnable;
        }

        public void run() {
            try {
                if (AsyncTaskExecutor.sDelayRun.get()) {
                    Thread.sleep((long) (new Random().nextInt(500) + 100));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.mRunnable.run();
        }
    }

    static {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        CPU_COUNT = availableProcessors;
        CORE_POOL_SIZE = availableProcessors + 1;
    }

    public static void setDelayEnable(boolean z) {
        sDelayRun.set(z);
    }

    private AsyncTaskExecutor() {
        PausableThreadPoolExecutor pausableThreadPoolExecutor = new PausableThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue(), THREADFACTORY);
        this.PARALLEL_EXECUTOR = pausableThreadPoolExecutor;
        this.SCHEDULED_EXECUTOR.setKeepAliveTime(60, TimeUnit.SECONDS);
        this.SCHEDULED_EXECUTOR.allowCoreThreadTimeOut(true);
        this.SCHEDULED_EXECUTOR.setRejectedExecutionHandler(new CallerRunsPolicy());
        this.PARALLEL_EXECUTOR.setRejectedExecutionHandler(new CallerRunsPolicy());
    }

    public static AsyncTaskExecutor getInstance() {
        return INSTANCE;
    }

    public final Executor getExecutor() {
        return this.PARALLEL_EXECUTOR;
    }

    public final void executeSerially(Runnable runnable, String str) {
        if (StringUtil.isEmpty(str)) {
            throw new IllegalArgumentException("The parameter \"threadName\" can't be empty.");
        }
        getGlobalStandardPipline().addTask(PipelineRunnable.TASK_POOL.obtain(runnable, str, true));
    }

    public final void executeSerially(Runnable runnable, String str, int i) {
        if (StringUtil.isEmpty(str)) {
            throw new IllegalArgumentException("The parameter \"threadName\" can't be empty.");
        }
        getGlobalStandardPipline().addTask(PipelineRunnable.TASK_POOL.obtain(runnable, str, i, true));
    }

    public final void executeSerially(String str, Runnable runnable, String str2) {
        if (StringUtil.isEmpty(str2)) {
            throw new IllegalArgumentException("The parameter \"threadName\" can't be empty.");
        } else if (StringUtil.isEmpty(str) || str.equalsIgnoreCase(NAME_GLOBAL_STANDARD_PIPELINE)) {
            getGlobalStandardPipline().addTask(PipelineRunnable.TASK_POOL.obtain(runnable, str2, true));
        } else {
            StandardPipeline standardPipeline = this.SERIAL_EXECUTORS.get(str);
            if (standardPipeline == null) {
                standardPipeline = new StandardPipeline(str, this.PARALLEL_EXECUTOR);
                standardPipeline.start();
                this.SERIAL_EXECUTORS.put(str, standardPipeline);
            }
            standardPipeline.addTask(PipelineRunnable.TASK_POOL.obtain(runnable, str2, true));
        }
    }

    public final void executeSerially(String str, Runnable runnable, String str2, int i) {
        if (StringUtil.isEmpty(str2)) {
            throw new IllegalArgumentException("The parameter \"threadName\" can't be empty.");
        } else if (StringUtil.isEmpty(str) || str.equalsIgnoreCase(NAME_GLOBAL_STANDARD_PIPELINE)) {
            getGlobalStandardPipline().addTask(PipelineRunnable.TASK_POOL.obtain(runnable, str2, i, true));
        } else {
            StandardPipeline standardPipeline = this.SERIAL_EXECUTORS.get(str);
            if (standardPipeline == null) {
                standardPipeline = new StandardPipeline(str, this.PARALLEL_EXECUTOR);
                standardPipeline.start();
                this.SERIAL_EXECUTORS.put(str, standardPipeline);
            }
            standardPipeline.addTask(PipelineRunnable.TASK_POOL.obtain(runnable, str2, i, true));
        }
    }

    public final void execute(Runnable runnable, String str) {
        if (StringUtil.isEmpty(str)) {
            throw new IllegalArgumentException("The parameter \"threadName\" can't be empty.");
        }
        this.PARALLEL_EXECUTOR.execute(PipelineRunnable.TASK_POOL.obtain(runnable, str));
    }

    public final ScheduledFuture<?> schedule(Runnable runnable, String str, long j, TimeUnit timeUnit) {
        if (!StringUtil.isEmpty(str)) {
            return this.SCHEDULED_EXECUTOR.schedule(PipelineRunnable.TASK_POOL.obtain(runnable, str), j, timeUnit);
        }
        throw new IllegalArgumentException("The parameter \"threadName\" can't be empty.");
    }

    public final TimerTask scheduleTimer(Runnable runnable, String str, long j) {
        return this.SCHEDULED_TIMER.schedule(runnable, str, j);
    }

    public final ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, String str, long j, long j2, TimeUnit timeUnit) {
        if (!StringUtil.isEmpty(str)) {
            return this.SCHEDULED_EXECUTOR.scheduleAtFixedRate(PipelineRunnable.TASK_POOL.obtain(runnable, str), j, j2, timeUnit);
        }
        throw new IllegalArgumentException("The parameter \"threadName\" can't be empty.");
    }

    public final ScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable, String str, long j, long j2, TimeUnit timeUnit) {
        if (!StringUtil.isEmpty(str)) {
            return this.SCHEDULED_EXECUTOR.scheduleWithFixedDelay(PipelineRunnable.TASK_POOL.obtain(runnable, str), j, j2, timeUnit);
        }
        throw new IllegalArgumentException("The parameter \"threadName\" can't be empty.");
    }

    public final String addTransaction(Runnable runnable, String str) {
        if (StringUtil.isEmpty(str)) {
            throw new IllegalArgumentException("The parameter \"threadName\" can't be empty.");
        }
        PipelineRunnable obtain = PipelineRunnable.TASK_POOL.obtain(runnable, str);
        getTransactionExecutor().addTask(obtain);
        return obtain.getId();
    }

    public final String addTransaction(Runnable runnable, String str, int i) {
        if (StringUtil.isEmpty(str)) {
            throw new IllegalArgumentException("The parameter \"threadName\" can't be empty.");
        }
        PipelineRunnable obtain = PipelineRunnable.TASK_POOL.obtain(runnable, str, i);
        getTransactionExecutor().addTask(obtain);
        return obtain.getId();
    }

    public final void removeTransaction(String str) {
        if (StringUtil.isEmpty(str)) {
            throw new IllegalArgumentException("The parameter \"id\" can't be empty.");
        }
        getTransactionExecutor().nextTransaction();
    }

    public final void shutdown() {
        getTransactionExecutor().stop();
        getGlobalStandardPipline().stop();
        for (StandardPipeline stop : this.SERIAL_EXECUTORS.values()) {
            stop.stop();
        }
        this.PARALLEL_EXECUTOR.shutdown();
        this.SCHEDULED_EXECUTOR.shutdown();
    }

    public final void pauseAll() {
        PausableRunnable.pause();
        PausableThreadPoolExecutor.pause();
        PausableScheduledThreadPool.pause();
    }

    public final void resumeAll() {
        PausableRunnable.resume();
        PausableThreadPoolExecutor.resume();
        PausableScheduledThreadPool.resume();
    }

    private TransactionPipeline getTransactionExecutor() {
        if (this.mTransactionExecutor != null) {
            return this.mTransactionExecutor;
        }
        synchronized (this) {
            if (this.mTransactionExecutor != null) {
                TransactionPipeline transactionPipeline = this.mTransactionExecutor;
                return transactionPipeline;
            }
            this.mTransactionExecutor = new TransactionPipeline("TransactionPipeline", this.PARALLEL_EXECUTOR);
            this.mTransactionExecutor.start();
            TransactionPipeline transactionPipeline2 = this.mTransactionExecutor;
            return transactionPipeline2;
        }
    }

    private StandardPipeline getGlobalStandardPipline() {
        if (this.mGlobalStandardPipline != null) {
            return this.mGlobalStandardPipline;
        }
        synchronized (this) {
            if (this.mGlobalStandardPipline != null) {
                StandardPipeline standardPipeline = this.mGlobalStandardPipline;
                return standardPipeline;
            }
            this.mGlobalStandardPipline = new StandardPipeline(NAME_GLOBAL_STANDARD_PIPELINE, this.PARALLEL_EXECUTOR);
            this.mGlobalStandardPipline.start();
            StandardPipeline standardPipeline2 = this.mGlobalStandardPipline;
            return standardPipeline2;
        }
    }
}
