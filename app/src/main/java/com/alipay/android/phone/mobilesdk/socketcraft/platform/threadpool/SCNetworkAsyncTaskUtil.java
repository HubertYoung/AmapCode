package com.alipay.android.phone.mobilesdk.socketcraft.platform.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SCNetworkAsyncTaskUtil {
    public static final void executeIO(Runnable runnable) {
        getThreadPool().executeIO(runnable);
    }

    public static final void executeLowPri(Runnable runnable) {
        getThreadPool().executeLowPri(runnable);
    }

    public static final void executeLazy(Runnable runnable) {
        getThreadPool().executeLazy(runnable);
    }

    public static final void execute(Runnable runnable) {
        getThreadPool().execute(runnable);
    }

    public static final Future<?> submit(Runnable runnable) {
        return getThreadPool().submit(runnable);
    }

    public static final Future<?> submitLazy(Runnable runnable) {
        return getThreadPool().submitLazy(runnable);
    }

    public static final void executeSerial(Runnable runnable) {
        getThreadPool().executeSerial(runnable);
    }

    public static final Future<?> submitSerial(Runnable runnable) {
        return getThreadPool().submitSerial(runnable);
    }

    public static final <T> Future<T> submit(Callable<T> callable) {
        return getThreadPool().submit(callable);
    }

    public static final ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        return getThreadPool().schedule(command, delay, unit);
    }

    public static final <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        return getThreadPool().schedule(callable, delay, unit);
    }

    public static final ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        return getThreadPool().scheduleAtFixedRate(command, initialDelay, period, unit);
    }

    public static final ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        return getThreadPool().scheduleWithFixedDelay(command, initialDelay, delay, unit);
    }

    public static final SCNetworkAsyncTaskExecutor getThreadPool() {
        return SCNetworkAsyncTaskExecutorFactory.getInstance();
    }
}
