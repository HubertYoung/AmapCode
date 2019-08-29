package com.alipay.multimedia.adapter.alipay;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;
import com.alipay.multimedia.adapter.AdapterFactory.Executor;
import com.alipay.multimedia.utils.ServiceUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class AlipayExecutor implements Executor {
    private Map<String, Looper> mLooperCache = new HashMap(5);
    private Handler mUIHandler = new Handler(Looper.getMainLooper());

    public void io(Runnable runnable) {
        ServiceUtils.taskScheduleService().acquireExecutor(ScheduleType.IO).execute(runnable);
    }

    public <V> Future<V> io(Callable<V> callable) {
        return ServiceUtils.taskScheduleService().acquireExecutor(ScheduleType.IO).submit(callable);
    }

    public void net(Runnable runnable) {
        ServiceUtils.taskScheduleService().acquireExecutor(ScheduleType.IO).execute(runnable);
    }

    public <V> Future<V> net(Callable<V> callable) {
        return ServiceUtils.taskScheduleService().acquireExecutor(ScheduleType.IO).submit(callable);
    }

    public void execute(Runnable runnable) {
        ServiceUtils.taskScheduleService().acquireExecutor(ScheduleType.NORMAL).execute(runnable);
    }

    public <V> Future<V> execute(Callable<V> callable) {
        return ServiceUtils.taskScheduleService().acquireExecutor(ScheduleType.NORMAL).submit(callable);
    }

    public void ui(Runnable runnable) {
        this.mUIHandler.post(runnable);
    }

    public Looper singleLooper(String name) {
        Looper looper;
        Looper looper2 = this.mLooperCache.get(name);
        if (looper2 != null && !looper2.getThread().isAlive()) {
            this.mLooperCache.remove(name);
            looper2 = null;
        }
        if (looper == null) {
            synchronized (this) {
                looper = this.mLooperCache.get(name);
                if (looper == null) {
                    HandlerThread handlerThread = new HandlerThread(name, 1);
                    handlerThread.start();
                    looper = handlerThread.getLooper();
                    this.mLooperCache.put(name, looper);
                }
            }
        }
        return looper;
    }
}
