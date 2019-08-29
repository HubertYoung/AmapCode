package com.alipay.android.phone.mobilesdk.socketcraft.integrated.threadpool;

import com.alipay.android.phone.mobilesdk.socketcraft.platform.threadpool.SCNetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MPaaSNetworkAsyncTaskExecutor implements SCNetworkAsyncTaskExecutor {
    public void executeIO(Runnable runnable) {
        NetworkAsyncTaskExecutor.executeIO(runnable);
    }

    public void executeLowPri(Runnable runnable) {
        NetworkAsyncTaskExecutor.executeLowPri(runnable);
    }

    public void executeLazy(Runnable runnable) {
        NetworkAsyncTaskExecutor.executeLazy(runnable);
    }

    public void execute(Runnable runnable) {
        NetworkAsyncTaskExecutor.execute(runnable);
    }

    public Future<?> submit(Runnable runnable) {
        return NetworkAsyncTaskExecutor.submit(runnable);
    }

    public Future<?> submitLazy(Runnable runnable) {
        return NetworkAsyncTaskExecutor.submitLazy(runnable);
    }

    public void executeSerial(Runnable runnable) {
        NetworkAsyncTaskExecutor.executeSerial(runnable);
    }

    public Future<?> submitSerial(Runnable runnable) {
        return NetworkAsyncTaskExecutor.submitSerial(runnable);
    }

    public <T> Future<T> submit(Callable<T> callable) {
        return NetworkAsyncTaskExecutor.submit(callable);
    }

    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        return NetworkAsyncTaskExecutor.schedule(command, delay, unit);
    }

    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        return NetworkAsyncTaskExecutor.schedule(callable, delay, unit);
    }

    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        return NetworkAsyncTaskExecutor.scheduleAtFixedRate(command, initialDelay, period, unit);
    }

    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        return NetworkAsyncTaskExecutor.scheduleWithFixedDelay(command, initialDelay, delay, unit);
    }
}
