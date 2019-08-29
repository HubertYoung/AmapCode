package com.alibaba.appmonitor.delegate;

import com.alibaba.analytics.utils.Logger;
import com.alibaba.analytics.utils.TaskExecutor;
import com.alibaba.appmonitor.event.EventRepo;
import java.util.concurrent.ScheduledFuture;

class CleanTask implements Runnable {
    private static final String TAG = "CleanTask";
    private static CleanTask cleanTask = null;
    private static ScheduledFuture future = null;
    private static boolean init = false;
    private static final long timeout = 300000;

    private CleanTask() {
    }

    static void init() {
        if (!init) {
            Logger.d((String) TAG, "init TimeoutEventManager");
            cleanTask = new CleanTask();
            future = TaskExecutor.getInstance().scheduleAtFixedRate(future, cleanTask, 300000);
            init = true;
        }
    }

    static void destroy() {
        if (future != null && !future.isDone()) {
            future.cancel(true);
        }
        init = false;
        cleanTask = null;
    }

    public void run() {
        Logger.d((String) TAG, "clean TimeoutEvent");
        EventRepo.getRepo().cleanExpiredEvent();
    }
}
