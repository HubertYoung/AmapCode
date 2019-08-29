package com.alipay.mobile.framework.service.common;

import android.os.Bundle;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.framework.service.CommonService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class TaskScheduleService extends CommonService {
    public static final int MAX_TASK_WEIGHT = 10;
    public static final int MIN_TASK_WEIGHT = -10;
    public static final int NORMAL_TASK_WEIGHT = 0;

    public enum ScheduleType {
        UNKNOWN,
        URGENT_DISPLAY,
        URGENT,
        NORMAL,
        IO,
        RPC,
        SYNC,
        MMS_HTTP,
        MMS_DJANGO,
        ORDERED
    }

    public abstract class Transaction extends com.alipay.mobile.common.task.transaction.Transaction {
        public Transaction() {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }
    }

    public abstract ThreadPoolExecutor acquireExecutor(ScheduleType scheduleType);

    public abstract OrderedExecutor acquireOrderedExecutor();

    public abstract ScheduledThreadPoolExecutor acquireScheduledExecutor();

    @Deprecated
    public abstract boolean addIdleTask(Runnable runnable);

    public abstract boolean addIdleTask(Runnable runnable, String str, int i);

    public abstract String addTransaction(Transaction transaction);

    public abstract Bundle dump();

    public abstract ThreadPoolExecutor getOrderedExecutorCore();

    public abstract void onPipelineFinished(String str);

    @Deprecated
    public abstract void parallelExecute(Runnable runnable);

    public abstract void parallelExecute(Runnable runnable, String str);

    public abstract void pause(ScheduleType scheduleType);

    public abstract void removeTransaction(String str);

    public abstract void restore(ScheduleType scheduleType);

    public abstract void resume(ScheduleType scheduleType);

    public abstract ScheduledFuture<?> schedule(Runnable runnable, String str, long j, TimeUnit timeUnit);

    public abstract ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, String str, long j, long j2, TimeUnit timeUnit);

    public abstract ScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable, String str, long j, long j2, TimeUnit timeUnit);

    @Deprecated
    public abstract void serialExecute(Runnable runnable);

    public abstract void serialExecute(Runnable runnable, String str);

    public abstract void yield(ScheduleType scheduleType);

    public TaskScheduleService() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }
}
