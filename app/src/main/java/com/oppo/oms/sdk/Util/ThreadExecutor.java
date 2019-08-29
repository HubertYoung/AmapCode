package com.oppo.oms.sdk.Util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadExecutor {
    private static final int CORE_POOL_SIZE = (Runtime.getRuntime().availableProcessors() * 2);
    private static final int KEEP_ALIVE_TIME = 60;
    private static final int MAX_POOL_SIZE = Integer.MAX_VALUE;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    private static final BlockingQueue<Runnable> WORK_QUEUE = new LinkedBlockingQueue();
    private static volatile ThreadExecutor sThreadExecutor;
    private Executor mExecutor;

    private ThreadExecutor() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, Integer.MAX_VALUE, 60, TIME_UNIT, WORK_QUEUE);
        this.mExecutor = threadPoolExecutor;
    }

    public void execute(Runnable runnable) {
        if (this.mExecutor != null) {
            this.mExecutor.execute(runnable);
        }
    }

    public static ThreadExecutor getInstance() {
        if (sThreadExecutor == null) {
            sThreadExecutor = new ThreadExecutor();
        }
        return sThreadExecutor;
    }
}
