package com.alipay.mobile.framework.service.common.threadpool;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.framework.service.common.threadpool.TaskPoolRunnable.TaskType;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ScheduledPoolExecutor extends ScheduledThreadPoolExecutor {
    private TaskType a;
    private int b = -1;

    public ScheduledPoolExecutor(TaskType taskType, int corePoolSize, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, threadFactory, handler);
        super.setKeepAliveTime(10, TimeUnit.MILLISECONDS);
        super.allowCoreThreadTimeOut(true);
        this.a = taskType;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void setThreadPriority(int threadPriority) {
        this.b = threadPriority;
    }

    public void shutdownValidly() {
        super.shutdown();
    }

    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        return super.schedule(new TaskPoolRunnable(command, this.a, this.b), delay, unit);
    }

    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        return super.scheduleAtFixedRate(new TaskPoolRunnable(command, this.a, this.b), initialDelay, period, unit);
    }

    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        return super.scheduleWithFixedDelay(new TaskPoolRunnable(command, this.a, this.b), initialDelay, delay, unit);
    }

    public <T> Future<T> submit(Runnable command, T result) {
        return super.submit(new TaskPoolRunnable(command, this.a, this.b), result);
    }

    public void shutdown() {
        TaskPoolDiagnose.shouldNotBeInvoked(this.a, "ScheduledPoolExecutor", "shutdown");
        super.shutdown();
    }

    public List<Runnable> shutdownNow() {
        TaskPoolDiagnose.shouldNotBeInvoked(this.a, "ScheduledPoolExecutor", "shutdownNow");
        return super.shutdownNow();
    }

    public void purge() {
        TaskPoolDiagnose.shouldNotBeInvoked(this.a, "ScheduledPoolExecutor", "purge");
        super.purge();
    }

    public void setCorePoolSize(int corePoolSize) {
        TaskPoolDiagnose.shouldNotBeInvoked(this.a, "ScheduledPoolExecutor", "setCorePoolSize");
        super.setCorePoolSize(corePoolSize);
    }

    public void setKeepAliveTime(long time, TimeUnit unit) {
        TaskPoolDiagnose.shouldNotBeInvoked(this.a, "ScheduledPoolExecutor", "setKeepAliveTime");
        super.setKeepAliveTime(time, unit);
    }

    public void setThreadFactory(ThreadFactory threadFactory) {
        TaskPoolDiagnose.shouldNotBeInvoked(this.a, "ScheduledPoolExecutor", "setThreadFactory");
        super.setThreadFactory(threadFactory);
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        TaskPoolDiagnose.shouldNotBeInvoked(this.a, "ScheduledPoolExecutor", "setMaximumPoolSize");
        super.setMaximumPoolSize(maximumPoolSize);
    }

    public void setRejectedExecutionHandler(RejectedExecutionHandler handler) {
        TaskPoolDiagnose.shouldNotBeInvoked(this.a, "ScheduledPoolExecutor", "setRejectedExecutionHandler");
        super.setRejectedExecutionHandler(handler);
    }

    public void allowCoreThreadTimeOut(boolean value) {
        TaskPoolDiagnose.shouldNotBeInvoked(this.a, "ScheduledPoolExecutor", "allowCoreThreadTimeOut");
        super.allowCoreThreadTimeOut(value);
    }
}
