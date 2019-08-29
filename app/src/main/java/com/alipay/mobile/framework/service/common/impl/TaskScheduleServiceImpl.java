package com.alipay.mobile.framework.service.common.impl;

import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.beehive.util.MiscUtil;
import com.alipay.mobile.common.logging.api.DeviceHWInfo;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.task.AsyncTaskExecutor;
import com.alipay.mobile.common.utils.SharedSwitchUtil;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.msg.MsgCodeConstants;
import com.alipay.mobile.framework.pipeline.Pipeline;
import com.alipay.mobile.framework.service.common.OrderedExecutor;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;
import com.alipay.mobile.framework.service.common.TaskScheduleService.Transaction;
import com.alipay.mobile.framework.service.common.threadpool.LifoBlockingDeque;
import com.alipay.mobile.framework.service.common.threadpool.ProcessCpuTracker;
import com.alipay.mobile.framework.service.common.threadpool.ScheduledPoolExecutor;
import com.alipay.mobile.framework.service.common.threadpool.TaskFactory;
import com.alipay.mobile.framework.service.common.threadpool.TaskPoolDiagnose;
import com.alipay.mobile.framework.service.common.threadpool.TaskPoolExecutor;
import com.alipay.mobile.framework.service.common.threadpool.TaskPoolRunnable.TaskType;
import com.alipay.sdk.util.h;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

public class TaskScheduleServiceImpl extends TaskScheduleService {
    private static final String a = TaskScheduleServiceImpl.class.getName();
    private static final long b = TimeUnit.SECONDS.toMillis(10);
    private PoolCfg c;
    private PoolCfg d;
    private PoolCfg e;
    private PoolCfg f;
    private PoolCfg g;
    private PoolCfg h;
    private PoolCfg i;
    private PoolCfg j;
    private int k;
    private AsyncTaskExecutor l;
    /* access modifiers changed from: private */
    public ScheduledFuture<?> m;
    private HashMap<ScheduleType, ThreadPoolExecutor> n = new HashMap<>();
    private ScheduledThreadPoolExecutor o;
    private ThreadPoolExecutor p;
    private OrderedExecutor<String> q;
    private int r;
    private boolean s;

    class DiscardOldestPolicyWithStatics extends DiscardOldestPolicy {
        TaskType taskType;

        DiscardOldestPolicyWithStatics(TaskType taskType2) {
            this.taskType = taskType2;
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }

        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            super.rejectedExecution(r, e);
            TaskPoolDiagnose.taskWasDiscard(this.taskType, r);
        }
    }

    class IdleCheckTask implements Runnable {
        private ProcessCpuTracker a;
        private int b;
        public ScheduledFuture<?> taskFuture;

        private IdleCheckTask() {
            this.a = new ProcessCpuTracker().update();
            this.b = 0;
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }

        public void run() {
            float idlePercent = this.a.update().getCpuIdlePercent();
            LoggerFactory.getTraceLogger().info("TaskScheduleService", "CPU idle: " + idlePercent);
            if (0.0f >= idlePercent || idlePercent >= 50.0f) {
                this.b++;
                if (this.b >= 2) {
                    LoggerFactory.getTraceLogger().info("TaskScheduleService", "executeIdleTasks() by Reason: cpu idle");
                    try {
                        TaskScheduleServiceImpl.this.d();
                        if (this.taskFuture != null) {
                            try {
                                this.taskFuture.cancel(false);
                            } catch (Throwable t) {
                                LoggerFactory.getTraceLogger().error("TaskScheduleService", "IdleCheckTask", t);
                            }
                        }
                    } catch (Throwable t2) {
                        LoggerFactory.getTraceLogger().error("TaskScheduleService", "IdleCheckTask", t2);
                    }
                }
            }
        }
    }

    class PoolCfg {
        public boolean allowCoreTimeout = true;
        public int coreSize;
        public ThreadFactory factory;
        public int keepAlive;
        public int maxSize;
        public int pushed_coreSize = -1;
        public int pushed_keepAlive = -1;
        public int pushed_maxSize = -1;
        public int pushed_workQueue = -1;
        public RejectedExecutionHandler rejectHandler;
        public TaskType taskType;
        public TimeUnit timeunit = TimeUnit.SECONDS;
        public BlockingQueue<Runnable> workQueue;

        public PoolCfg(TaskType taskType2) {
            this.taskType = taskType2;
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }

        public String toString() {
            return "PoolCfg{taskType=" + this.taskType + ",coreSize=" + this.coreSize + ",maxSize=" + this.maxSize + ",keepAlive=" + this.keepAlive + ",timeunit=" + this.timeunit + ",allowCoreTimeout=" + this.allowCoreTimeout + ",workQueueSize=" + (this.workQueue == null ? 0 : this.workQueue.size()) + ",factory=" + (this.factory == null ? "null" : this.factory.getClass().getName()) + ",rejectHandler=" + (this.rejectHandler == null ? "null" : this.rejectHandler.getClass().getName()) + ",pushed_coreSize=" + this.pushed_coreSize + ",pushed_maxSize=" + this.pushed_maxSize + ",pushed_workQueue=" + this.pushed_workQueue + ",pushed_keepAlive=" + this.pushed_keepAlive + h.d;
        }
    }

    public TaskScheduleServiceImpl() {
        try {
            this.k = DeviceHWInfo.getNumberOfCPUCores();
        } catch (Throwable t) {
            LoggerFactory.getTraceLogger().error("TaskScheduleService", "in constructor", t);
        }
        if (this.k <= 0) {
            LoggerFactory.getTraceLogger().error((String) "TaskScheduleService", "revise to dual core, cpuCoresNumber: " + this.k);
            this.k = 2;
        }
        this.l = AsyncTaskExecutor.getInstance();
        a();
        acquireScheduledExecutor().schedule(new Runnable() {
            {
                if (Boolean.FALSE.booleanValue()) {
                    Log.v("hackbyte ", ClassVerifier.class.toString());
                }
            }

            public void run() {
                if (TaskScheduleServiceImpl.this.m != null) {
                    try {
                        TaskScheduleServiceImpl.this.m.cancel(true);
                    } catch (Throwable t) {
                        LoggerFactory.getTraceLogger().error("TaskScheduleService", "cancel check idle", t);
                    }
                }
                LoggerFactory.getTraceLogger().info("TaskScheduleService", "executeIdleTasks() by Reason: timeout");
                try {
                    TaskScheduleServiceImpl.this.d();
                } catch (Throwable t2) {
                    LoggerFactory.getTraceLogger().error((String) "TaskScheduleService", t2);
                }
            }
        }, 180, TimeUnit.SECONDS);
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void serialExecute(Runnable command) {
        this.l.executeSerially(command);
    }

    public void parallelExecute(Runnable command) {
        this.l.execute(command);
    }

    public void serialExecute(Runnable command, String threadName) {
        this.l.executeSerially(command, threadName);
    }

    public void parallelExecute(Runnable command, String threadName) {
        this.l.execute(command, threadName);
    }

    public ScheduledFuture<?> schedule(Runnable task, String threadName, long delay, TimeUnit unit) {
        return this.l.schedule(task, threadName, delay, unit);
    }

    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, String threadName, long initialDelay, long period, TimeUnit unit) {
        return this.l.scheduleAtFixedRate(task, initialDelay, period, unit);
    }

    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, String threadName, long initialDelay, long delay, TimeUnit unit) {
        return this.l.scheduleWithFixedDelay(task, initialDelay, delay, unit);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle params) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle params) {
        a((Object) this.l);
        a((Object) this.o);
        a((Object) this.p);
        synchronized (this.n) {
            for (ThreadPoolExecutor a2 : this.n.values()) {
                a((Object) a2);
            }
        }
    }

    private static void a(Object executor) {
        if (executor != null) {
            try {
                if (executor instanceof TaskPoolExecutor) {
                    ((TaskPoolExecutor) executor).shutdownValidly();
                } else if (executor instanceof ScheduledPoolExecutor) {
                    ((ScheduledPoolExecutor) executor).shutdownValidly();
                } else if (executor instanceof ThreadPoolExecutor) {
                    ((ThreadPoolExecutor) executor).shutdown();
                } else if (executor instanceof AsyncTaskExecutor) {
                    ((AsyncTaskExecutor) executor).shutdown();
                } else {
                    LoggerFactory.getTraceLogger().error((String) "TaskScheduleService", "shutdownExecutorCommonly, no such type: " + executor.getClass().getName());
                }
            } catch (Throwable t) {
                LoggerFactory.getTraceLogger().error((String) "TaskScheduleService", t);
            }
        }
    }

    public String addTransaction(Transaction transaction) {
        return this.l.addTransaction(transaction);
    }

    public void removeTransaction(String id) {
        this.l.removeTransaction(id);
    }

    private void a() {
        this.c = new PoolCfg(TaskType.IO);
        this.d = new PoolCfg(TaskType.URGENT);
        this.e = new PoolCfg(TaskType.NORMAL);
        this.f = new PoolCfg(TaskType.RPC);
        this.g = new PoolCfg(TaskType.MMS_HTTP);
        this.h = new PoolCfg(TaskType.MMS_DJANGO);
        this.i = new PoolCfg(TaskType.ORDERED);
        this.j = new PoolCfg(TaskType.URGENT_DISPLAY);
        try {
            b();
        } catch (Throwable t) {
            LoggerFactory.getTraceLogger().error("TaskScheduleService", "initializeThreadPools", t);
        }
        this.c = a(this.c);
        this.d = c(this.d);
        this.e = d(this.e);
        this.f = e(this.f);
        this.g = a(this.g, "HTTP");
        this.h = a(this.h, "DJANGO");
        this.i = f(this.i);
        this.j = b(this.j);
    }

    private void b() {
        Application application = LauncherApplicationAgent.getInstance().getApplicationContext();
        if (application == null) {
            LoggerFactory.getTraceLogger().error((String) "TaskScheduleService", (String) "initializePoolCfgs: application is NULL");
            return;
        }
        String poolConfigJson = SharedSwitchUtil.getSharedSwitch(application, SharedSwitchUtil.THREAD_POOL_CONFIG);
        if (!TextUtils.isEmpty(poolConfigJson)) {
            JSONObject poolConfigObject = new JSONObject(poolConfigJson);
            a(this.c, poolConfigObject, (String) "io");
            a(this.d, poolConfigObject, (String) "urgent");
            a(this.e, poolConfigObject, (String) "normal");
            a(this.f, poolConfigObject, (String) "rpc");
            a(this.g, poolConfigObject, (String) "mmsHttp");
            a(this.h, poolConfigObject, (String) "mmsDjango");
            a(this.i, poolConfigObject, (String) "ordered");
            a(this.j, poolConfigObject, (String) "urgentDisplay");
        }
    }

    private static void a(PoolCfg poolCfg, JSONObject jsonObject, String key) {
        if (poolCfg != null && !TextUtils.isEmpty(key) && jsonObject.has(key)) {
            try {
                JSONObject keyObject = jsonObject.getJSONObject(key);
                poolCfg.pushed_coreSize = a(keyObject, (String) "coreSize", poolCfg.pushed_coreSize);
                poolCfg.pushed_maxSize = a(keyObject, (String) "maxSize", poolCfg.pushed_maxSize);
                poolCfg.pushed_workQueue = a(keyObject, (String) "queueSize", poolCfg.pushed_workQueue);
                poolCfg.pushed_keepAlive = a(keyObject, (String) "keepAlive", poolCfg.pushed_keepAlive);
            } catch (Throwable t) {
                LoggerFactory.getTraceLogger().error("TaskScheduleService", "setValueFromJson: " + key, t);
            }
        }
    }

    private static int a(JSONObject jsonObject, String key, int defaultValue) {
        if (jsonObject == null || TextUtils.isEmpty(key) || !jsonObject.has(key)) {
            return defaultValue;
        }
        try {
            return jsonObject.getInt(key);
        } catch (Throwable t) {
            LoggerFactory.getTraceLogger().error("TaskScheduleService", "setValueFromJson: " + key, t);
            return defaultValue;
        }
    }

    private PoolCfg a(PoolCfg cfg) {
        if (cfg.pushed_coreSize >= 0) {
            cfg.coreSize = Math.max(this.k, cfg.pushed_coreSize);
        } else if (this.k <= 2) {
            cfg.coreSize = this.k;
        } else {
            cfg.coreSize = this.k;
        }
        if (cfg.pushed_maxSize >= 0) {
            cfg.maxSize = Math.max(cfg.coreSize, cfg.pushed_maxSize);
        } else if (this.k <= 2) {
            cfg.maxSize = Math.max(cfg.coreSize, this.k + 1);
        } else {
            cfg.maxSize = Math.max(cfg.coreSize, this.k * 2);
        }
        if (cfg.pushed_keepAlive < 0) {
            cfg.keepAlive = 45;
        } else {
            cfg.keepAlive = cfg.pushed_keepAlive;
        }
        if (cfg.pushed_workQueue < 0) {
            cfg.workQueue = new LinkedBlockingQueue();
        } else {
            cfg.workQueue = new LinkedBlockingQueue(cfg.pushed_workQueue);
        }
        cfg.rejectHandler = new DiscardOldestPolicyWithStatics(cfg.taskType);
        cfg.factory = new TaskFactory("IO_THREAD_", 1);
        return cfg;
    }

    private PoolCfg b(PoolCfg cfg) {
        if (cfg.pushed_coreSize < 0) {
            if (this.k <= 2) {
                cfg.coreSize = this.k;
            } else {
                cfg.coreSize = this.k * 2;
            }
            if (cfg.coreSize > 16) {
                cfg.coreSize = 16;
            }
        } else {
            cfg.coreSize = Math.max(this.k, cfg.pushed_coreSize);
        }
        if (cfg.pushed_maxSize < 0) {
            cfg.maxSize = 32;
        } else {
            cfg.maxSize = Math.max(cfg.coreSize, cfg.pushed_maxSize);
        }
        if (cfg.pushed_keepAlive < 0) {
            cfg.keepAlive = 3;
        } else {
            cfg.keepAlive = cfg.pushed_keepAlive;
        }
        if (cfg.pushed_workQueue < 0) {
            cfg.workQueue = new ArrayBlockingQueue(1);
        } else {
            cfg.workQueue = new ArrayBlockingQueue(cfg.pushed_workQueue, true);
        }
        cfg.rejectHandler = new DiscardOldestPolicyWithStatics(cfg.taskType);
        cfg.factory = new TaskFactory("URGENT_DISPLAY_THREAD_", 10);
        return cfg;
    }

    private PoolCfg c(PoolCfg cfg) {
        if (cfg.pushed_coreSize < 0) {
            cfg.coreSize = this.k;
            if (cfg.coreSize > 8) {
                cfg.coreSize = 8;
            }
        } else {
            cfg.coreSize = Math.max(this.k, cfg.pushed_coreSize);
        }
        if (cfg.pushed_maxSize < 0) {
            cfg.maxSize = 32;
        } else {
            cfg.maxSize = Math.max(cfg.coreSize, cfg.pushed_maxSize);
        }
        if (cfg.pushed_keepAlive < 0) {
            cfg.keepAlive = 3;
        } else {
            cfg.keepAlive = cfg.pushed_keepAlive;
        }
        if (cfg.pushed_workQueue < 0) {
            cfg.workQueue = new ArrayBlockingQueue(1);
        } else {
            cfg.workQueue = new ArrayBlockingQueue(cfg.pushed_workQueue, true);
        }
        cfg.rejectHandler = new DiscardOldestPolicyWithStatics(cfg.taskType);
        cfg.factory = new TaskFactory("URGENT_THREAD_", 5);
        return cfg;
    }

    private PoolCfg d(PoolCfg cfg) {
        if (cfg.pushed_coreSize < 0) {
            cfg.coreSize = Math.min(4, this.k);
        } else {
            cfg.coreSize = Math.max(this.k, cfg.pushed_coreSize);
        }
        if (cfg.pushed_maxSize < 0) {
            cfg.maxSize = Integer.MAX_VALUE;
        } else {
            cfg.maxSize = Math.max(cfg.coreSize, cfg.pushed_maxSize);
        }
        if (cfg.pushed_keepAlive < 0) {
            cfg.keepAlive = 5;
        } else {
            cfg.keepAlive = cfg.pushed_keepAlive;
        }
        if (cfg.pushed_workQueue < 0) {
            cfg.workQueue = new LinkedBlockingQueue();
        } else {
            cfg.workQueue = new LinkedBlockingQueue(cfg.pushed_workQueue);
        }
        cfg.rejectHandler = new DiscardOldestPolicyWithStatics(cfg.taskType);
        cfg.factory = new TaskFactory("NORMAL_THREAD_", 1);
        return cfg;
    }

    private PoolCfg e(PoolCfg cfg) {
        if (cfg.pushed_coreSize < 0) {
            cfg.coreSize = this.k;
            if (cfg.coreSize > 8) {
                cfg.coreSize = 8;
            }
        } else {
            cfg.coreSize = Math.max(this.k, cfg.pushed_coreSize);
        }
        if (cfg.pushed_maxSize < 0) {
            cfg.maxSize = 32;
        } else {
            cfg.maxSize = Math.max(cfg.coreSize, cfg.pushed_maxSize);
        }
        if (cfg.pushed_keepAlive < 0) {
            cfg.keepAlive = 10;
        } else {
            cfg.keepAlive = cfg.pushed_keepAlive;
        }
        if (cfg.pushed_workQueue < 0) {
            cfg.workQueue = new ArrayBlockingQueue(1);
        } else {
            cfg.workQueue = new LinkedBlockingQueue(cfg.pushed_workQueue);
        }
        cfg.rejectHandler = new DiscardOldestPolicyWithStatics(cfg.taskType);
        cfg.factory = new TaskFactory("RPC_INVOKER_THREAD_", 1);
        return cfg;
    }

    private PoolCfg a(PoolCfg cfg, String prefix) {
        if (cfg.pushed_coreSize >= 0) {
            cfg.coreSize = Math.min(this.k, cfg.pushed_coreSize);
        } else if (this.k <= 2) {
            cfg.coreSize = Math.min(this.k, 3);
        } else {
            cfg.coreSize = Math.min(this.k, 3);
        }
        if (cfg.pushed_maxSize >= 0) {
            cfg.maxSize = Math.min(this.k, cfg.pushed_maxSize);
        } else if (this.k <= 2) {
            cfg.maxSize = Math.min(this.k, 3);
        } else {
            cfg.maxSize = Math.min(this.k, 3);
        }
        if (cfg.pushed_keepAlive < 0) {
            cfg.keepAlive = 20;
        } else {
            cfg.keepAlive = cfg.pushed_keepAlive;
        }
        cfg.workQueue = new LifoBlockingDeque();
        cfg.rejectHandler = new DiscardOldestPolicyWithStatics(cfg.taskType);
        cfg.factory = new TaskFactory("MMS_" + prefix + "_THREAD_", 1);
        return cfg;
    }

    private PoolCfg f(PoolCfg cfg) {
        if (cfg.pushed_coreSize >= 0) {
            cfg.coreSize = Math.max(this.k, cfg.pushed_coreSize);
        } else if (this.k <= 2) {
            cfg.coreSize = Math.max(this.k, 4);
        } else {
            cfg.coreSize = Math.max(this.k, 4);
        }
        if (cfg.pushed_maxSize >= 0) {
            cfg.maxSize = Math.max(this.k, cfg.pushed_maxSize);
        } else if (this.k <= 2) {
            cfg.maxSize = 4;
        } else {
            cfg.maxSize = this.k * 2;
        }
        if (cfg.pushed_keepAlive < 0) {
            cfg.keepAlive = 10;
        } else {
            cfg.keepAlive = cfg.pushed_keepAlive;
        }
        if (cfg.pushed_workQueue < 0) {
            cfg.workQueue = new ArrayBlockingQueue(60);
        } else {
            cfg.workQueue = new ArrayBlockingQueue(cfg.pushed_workQueue);
        }
        cfg.rejectHandler = new DiscardOldestPolicyWithStatics(cfg.taskType);
        cfg.factory = new TaskFactory("ORDERED_THREAD_", 1);
        return cfg;
    }

    public ThreadPoolExecutor acquireExecutor(ScheduleType scheduleType) {
        if (scheduleType == ScheduleType.URGENT_DISPLAY) {
            LoggerFactory.getTraceLogger().warn((String) "TaskScheduleService", (String) "acquire URGENT_DISPLAY executor, pls ensure your usage!!");
            Log.w("TaskScheduleService", "acquire URGENT_DISPLAY executor, pls ensure your usage!!");
        }
        ThreadPoolExecutor executor = this.n.get(scheduleType);
        if (executor != null) {
            return executor;
        }
        synchronized (this.n) {
            ThreadPoolExecutor executor2 = this.n.get(scheduleType);
            if (executor2 != null) {
                return executor2;
            }
            LoggerFactory.getTraceLogger().info("TaskScheduleService", "acquireExecutor: " + scheduleType);
            switch (scheduleType) {
                case IO:
                    executor2 = g(this.c);
                    break;
                case URGENT_DISPLAY:
                    executor2 = g(this.j);
                    break;
                case URGENT:
                    executor2 = g(this.d);
                    break;
                case NORMAL:
                    executor2 = g(this.e);
                    break;
                case RPC:
                    executor2 = g(this.f);
                    break;
                case SYNC:
                    throw new IllegalArgumentException("The ThreadPool of type SYNC is not supported yet, please considering another type!");
                case MMS_HTTP:
                    executor2 = g(this.g);
                    break;
                case MMS_DJANGO:
                    executor2 = g(this.h);
                    break;
            }
            if (executor2 == null) {
                throw new IllegalStateException("create executor of type: " + scheduleType + " failed!");
            }
            this.n.put(scheduleType, executor2);
            return executor2;
        }
    }

    private static ThreadPoolExecutor g(PoolCfg cfg) {
        if (cfg == null) {
            throw new IllegalArgumentException("cfg is null");
        }
        LoggerFactory.getTraceLogger().info("TaskScheduleService", "createExecutor: " + cfg);
        return new TaskPoolExecutor(cfg.taskType, cfg.coreSize, cfg.maxSize, (long) cfg.keepAlive, cfg.timeunit, cfg.allowCoreTimeout, cfg.workQueue, cfg.factory, cfg.rejectHandler);
    }

    public ScheduledThreadPoolExecutor acquireScheduledExecutor() {
        int corePoolSize = 8;
        if (this.o == null) {
            synchronized (this) {
                if (this.o == null) {
                    ThreadFactory factory = new TaskFactory("SCHEDULED_THREAD_", 5);
                    RejectedExecutionHandler handler = new DiscardOldestPolicy();
                    if (this.k <= 8) {
                        corePoolSize = this.k;
                    }
                    this.o = new ScheduledPoolExecutor(TaskType.SCHEDULED, corePoolSize, factory, handler);
                }
            }
        }
        return this.o;
    }

    public OrderedExecutor<String> acquireOrderedExecutor() {
        if (this.q == null) {
            synchronized (this) {
                if (this.q == null) {
                    this.p = g(this.i);
                    this.q = new OrderedExecutor<>(this.p);
                }
            }
        }
        return this.q;
    }

    public ThreadPoolExecutor getOrderedExecutorCore() {
        acquireOrderedExecutor();
        return this.p;
    }

    private ScheduledFuture<?> c() {
        IdleCheckTask idleCheckTask = new IdleCheckTask();
        ScheduledFuture idleCheckFuture = acquireScheduledExecutor().scheduleAtFixedRate(idleCheckTask, 10, 10, TimeUnit.SECONDS);
        idleCheckTask.taskFuture = idleCheckFuture;
        return idleCheckFuture;
    }

    @Deprecated
    public boolean addIdleTask(Runnable task) {
        return addIdleTask(task, task == null ? "no task" : task.getClass().getName(), 0);
    }

    public boolean addIdleTask(Runnable task, String threadName, int taskWeight) {
        boolean pipelineAvailable = false;
        if (task == null) {
            throw new IllegalArgumentException("The task is null!");
        } else if (TextUtils.isEmpty(threadName)) {
            throw new IllegalArgumentException("The thread name is empty!");
        } else {
            String taskName = task.getClass().getName();
            MicroApplicationContext microApp = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
            if (microApp == null) {
                LoggerFactory.getTraceLogger().error((String) "TaskScheduleService", taskName + ", addIdleTask: MicroApplicationContext is null");
            } else {
                if (taskWeight > 10) {
                    taskWeight = 10;
                    LoggerFactory.getTraceLogger().error((String) "TaskScheduleService", taskName + ", addIdleTask: taskWeight > MAX_TASK_WEIGHT");
                }
                Pipeline pipeline = microApp.getPipelineByName(a, b);
                if (pipeline != null) {
                    pipelineAvailable = true;
                }
                if (pipelineAvailable) {
                    pipeline.addTask(task, threadName, taskWeight);
                }
                StringBuilder message = new StringBuilder("addIdleTask");
                message.append(", taskName: ").append(taskName);
                message.append(", threadName: ").append(threadName);
                message.append(", taskWeight: ").append(taskWeight);
                if (!pipelineAvailable) {
                    message.append(", there is no such pipeline whose type is ").append(a);
                }
                LoggerFactory.getTraceLogger().info("TaskScheduleService", message.toString());
            }
            return pipelineAvailable;
        }
    }

    public synchronized void onPipelineFinished(String type) {
        if (type != null) {
            LoggerFactory.getTraceLogger().info("TaskScheduleService", "pipeline(event: " + type + ") has finished");
            if (type.equals("com.alipay.mobile.framework.INITED")) {
                this.r |= 1;
            } else if (type.equals(MsgCodeConstants.PIPELINE_FRAMEWORK_CLIENT_STARTED)) {
                this.r |= 2;
            } else if (type.equals(MsgCodeConstants.PIPELINE_TABLAUNCHER_ACTIVATED)) {
                this.r |= 4;
            }
            if (this.r == 7 && this.m == null) {
                LoggerFactory.getTraceLogger().info("TaskScheduleService", "prepareIdleCheckTask as all pipelines have finished!");
                this.m = c();
            }
        }
    }

    /* access modifiers changed from: private */
    public void d() {
        if (this.s) {
            LoggerFactory.getTraceLogger().info("TaskScheduleService", "executeIdleTasks: already executed");
            return;
        }
        this.s = true;
        MicroApplicationContext microApp = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
        if (microApp == null) {
            LoggerFactory.getTraceLogger().error((String) "TaskScheduleService", (String) "executeIdleTasks: MicroApplicationContext is NULL");
            return;
        }
        Pipeline pipeline = microApp.getPipelineByName(a, b);
        pipeline.addIdleListener(new Runnable() {
            private long a;

            {
                if (Boolean.FALSE.booleanValue()) {
                    Log.v("hackbyte ", ClassVerifier.class.toString());
                }
            }

            public void run() {
                this.a++;
                LoggerFactory.getTraceLogger().info("TaskScheduleService", "idle tasks are all terminated, count: " + this.a);
            }
        });
        LoggerFactory.getTraceLogger().info("TaskScheduleService", "idle tasks are started");
        pipeline.start();
        e();
    }

    private void e() {
        ThreadPoolExecutor executor = this.n.get(ScheduleType.NORMAL);
        if (executor != null && (executor instanceof TaskPoolExecutor)) {
            ((TaskPoolExecutor) executor).setThreadPriority(3);
        }
    }

    public Bundle dump() {
        String scheduledThreadPoolExecutor;
        String obj;
        Bundle bundle = new Bundle();
        synchronized (this.n) {
            for (ScheduleType type : this.n.keySet()) {
                bundle.putString(type.toString(), this.n.get(type).toString());
            }
        }
        if (this.o == null) {
            scheduledThreadPoolExecutor = MiscUtil.NULL_STR;
        } else {
            scheduledThreadPoolExecutor = this.o.toString();
        }
        bundle.putString("SCHEDULED_EXECUTOR", scheduledThreadPoolExecutor);
        if (this.q == null) {
            obj = MiscUtil.NULL_STR;
        } else {
            obj = this.q.toString();
        }
        bundle.putString("GLOBAL_HANDLER_THREAD", obj);
        return bundle;
    }

    public void pause(ScheduleType type) {
        try {
            ThreadPoolExecutor threadPoolExecutor = acquireExecutor(type);
            if (threadPoolExecutor instanceof TaskPoolExecutor) {
                ((TaskPoolExecutor) threadPoolExecutor).pause();
            }
        } catch (IllegalStateException e2) {
            if (type != ScheduleType.ORDERED) {
                LoggerFactory.getTraceLogger().error("TaskScheduleService", "pause executor:" + type + " failed.", e2);
            }
        }
        if (ScheduleType.ORDERED.equals(type)) {
            Executor executor = acquireOrderedExecutor().getExecutor();
            if (executor instanceof TaskPoolExecutor) {
                ((TaskPoolExecutor) executor).pause();
            }
        }
    }

    public void resume(ScheduleType type) {
        try {
            ThreadPoolExecutor threadPoolExecutor = acquireExecutor(type);
            if (threadPoolExecutor instanceof TaskPoolExecutor) {
                ((TaskPoolExecutor) threadPoolExecutor).resume();
            }
        } catch (IllegalStateException e2) {
            if (type != ScheduleType.ORDERED) {
                LoggerFactory.getTraceLogger().error("TaskScheduleService", "resume executor:" + type + " failed.", e2);
            }
        }
        if (ScheduleType.ORDERED.equals(type)) {
            Executor executor = acquireOrderedExecutor().getExecutor();
            if (executor instanceof TaskPoolExecutor) {
                ((TaskPoolExecutor) executor).resume();
            }
        }
    }

    public void yield(ScheduleType type) {
        try {
            ThreadPoolExecutor threadPoolExecutor = acquireExecutor(type);
            if (threadPoolExecutor instanceof TaskPoolExecutor) {
                ((TaskPoolExecutor) threadPoolExecutor).yield();
            }
        } catch (IllegalStateException e2) {
            if (type != ScheduleType.ORDERED) {
                LoggerFactory.getTraceLogger().error("TaskScheduleService", "resume executor:" + type + " failed.", e2);
            }
        }
        if (ScheduleType.ORDERED.equals(type)) {
            Executor executor = acquireOrderedExecutor().getExecutor();
            if (executor instanceof TaskPoolExecutor) {
                ((TaskPoolExecutor) executor).yield();
            }
        }
    }

    public void restore(ScheduleType type) {
        try {
            ThreadPoolExecutor threadPoolExecutor = acquireExecutor(type);
            if (threadPoolExecutor instanceof TaskPoolExecutor) {
                ((TaskPoolExecutor) threadPoolExecutor).restore();
            }
        } catch (IllegalStateException e2) {
            if (type != ScheduleType.ORDERED) {
                LoggerFactory.getTraceLogger().error("TaskScheduleService", "resume executor:" + type + " failed.", e2);
            }
        }
        if (ScheduleType.ORDERED.equals(type)) {
            Executor executor = acquireOrderedExecutor().getExecutor();
            if (executor instanceof TaskPoolExecutor) {
                ((TaskPoolExecutor) executor).restore();
            }
        }
    }
}
