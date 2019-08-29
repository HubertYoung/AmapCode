package com.alipay.android.phone.mobilecommon.multimediabiz.biz.common;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.DefaultThreadFactory;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.others.TaskUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.taskqueue.LIFOLinkedBlockingDeque;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.mobile.framework.service.common.OrderedExecutor;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskScheduleManager {
    private static TaskScheduleManager a = new TaskScheduleManager();
    private ExecutorService b;
    private ExecutorService c;
    private ExecutorService d;
    private ExecutorService e;
    private ExecutorService f;
    private ExecutorService g;
    private Handler h;
    private Looper i;
    private OrderedExecutor j;
    private int k = CommonUtils.getTaskOccurs(4);

    private TaskScheduleManager() {
        if (this.k <= 1) {
            this.k = 2;
        } else if (this.k > 4) {
            this.k = 4;
        }
    }

    public static synchronized TaskScheduleManager get() {
        TaskScheduleManager taskScheduleManager;
        synchronized (TaskScheduleManager.class) {
            try {
                taskScheduleManager = a;
            }
        }
        return taskScheduleManager;
    }

    public synchronized ExecutorService djangoImageExecutor() {
        if (this.b == null) {
            TaskScheduleService scheduleService = AppUtils.getTaskScheduleService();
            if (scheduleService != null) {
                this.b = scheduleService.acquireExecutor(ScheduleType.MMS_DJANGO);
            }
            if (this.b == null) {
                this.b = a((String) "dj");
            }
        }
        return this.b;
    }

    public synchronized ExecutorService localImageExecutor() {
        if (this.c == null) {
            this.c = a("lo", false, TaskUtils.getTaskOccurs(ConfigManager.getInstance().getCommonConfigItem().taskConf.localMaxOccurs));
        }
        return this.c;
    }

    public synchronized ExecutorService loadImageExecutor() {
        if (this.e == null) {
            this.e = a("mm-load", false, TaskUtils.getTaskOccurs(ConfigManager.getInstance().getCommonConfigItem().taskConf.loadMaxOccurs));
        }
        return this.e;
    }

    public synchronized ExecutorService commonExecutor() {
        try {
            if (this.d == null) {
                if (ConfigManager.getInstance().getCommonConfigItem().taskConf.commonTaskPoolSwitch == 1) {
                    Logger.D("TaskScheduleManager", "commonExecutor by local mMaxOccurs=" + this.k, new Object[0]);
                    this.d = a("cm", false, this.k);
                } else {
                    TaskScheduleService scheduleService = AppUtils.getTaskScheduleService();
                    if (scheduleService != null) {
                        this.d = scheduleService.acquireExecutor(ScheduleType.NORMAL);
                    }
                    if (this.d == null) {
                        this.d = a("cm", false, 2);
                    }
                }
            }
        }
        return this.d;
    }

    public synchronized ExecutorService localSingleExecutor() {
        if (this.g == null) {
            this.g = Executors.newSingleThreadExecutor();
            a(this.g);
        }
        return this.g;
    }

    public synchronized ExecutorService localPingSingleExecutor() {
        if (this.f == null) {
            this.f = Executors.newSingleThreadExecutor();
            a(this.f);
        }
        return this.f;
    }

    public synchronized Handler commonHandler() {
        if (this.h == null) {
            this.h = new Handler(commonLooper());
        }
        return this.h;
    }

    public synchronized Looper commonLooper() {
        if (this.i == null) {
            HandlerThread handlerThread = new HandlerThread("mm-handler");
            handlerThread.setPriority(1);
            handlerThread.start();
            this.i = handlerThread.getLooper();
        }
        return this.i;
    }

    public synchronized OrderedExecutor orderedExecutor() {
        TaskScheduleService scheduleService = AppUtils.getTaskScheduleService();
        if (scheduleService != null) {
            this.j = scheduleService.acquireOrderedExecutor();
        }
        if (this.j == null) {
            this.j = new OrderedExecutor(Executors.newCachedThreadPool());
        }
        return this.j;
    }

    public synchronized void execute(Runnable task) {
        orderedExecutor().submit("mm", task);
    }

    public synchronized void schedule(Runnable task, long delay) {
        TaskScheduleService scheduleService = AppUtils.getTaskScheduleService();
        if (scheduleService != null) {
            scheduleService.schedule(task, "mm-schedule", delay, TimeUnit.MILLISECONDS);
        }
    }

    private static void a(ExecutorService executor) {
        if (executor instanceof ThreadPoolExecutor) {
            try {
                ((ThreadPoolExecutor) executor).allowCoreThreadTimeOut(true);
            } catch (Throwable e2) {
                Logger.D("TaskScheduleManager", "allTimeout exp=" + e2.toString(), new Object[0]);
            }
        }
    }

    private static ExecutorService a(String name, boolean lifo, int maximumPoolSize) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(CommonUtils.getCoreSize(Math.min(4, maximumPoolSize)), maximumPoolSize, 60, TimeUnit.SECONDS, lifo ? new LIFOLinkedBlockingDeque() : new LinkedBlockingDeque(), TextUtils.isEmpty(name) ? new DefaultThreadFactory() : new DefaultThreadFactory(name));
        a((ExecutorService) executor);
        return executor;
    }

    private static ExecutorService a(String name) {
        return a(name, true, 50);
    }
}
