package com.alipay.android.phone.mobilesdk.socketcraft.platform.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public interface SCNetworkAsyncTaskExecutor {
    void execute(Runnable runnable);

    void executeIO(Runnable runnable);

    void executeLazy(Runnable runnable);

    void executeLowPri(Runnable runnable);

    void executeSerial(Runnable runnable);

    ScheduledFuture<?> schedule(Runnable runnable, long j, TimeUnit timeUnit);

    <V> ScheduledFuture<V> schedule(Callable<V> callable, long j, TimeUnit timeUnit);

    ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long j, long j2, TimeUnit timeUnit);

    ScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable, long j, long j2, TimeUnit timeUnit);

    Future<?> submit(Runnable runnable);

    <T> Future<T> submit(Callable<T> callable);

    Future<?> submitLazy(Runnable runnable);

    Future<?> submitSerial(Runnable runnable);
}
