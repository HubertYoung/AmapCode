package com.alipay.mobile.common.transport.concurrent;

import android.annotation.TargetApi;
import android.content.Context;
import com.ali.auth.third.login.LoginConstants;
import com.alipay.mobile.common.transport.concurrent.NetThreadPoolExeFactory.NetThreadFactory;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.autonavi.minimap.bundle.apm.internal.report.ReportManager;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

public class TaskExecutorManager {
    public static final String TAG = "TaskExecutorManager";
    public static final int TASK_TYPE_AMR = 3;
    public static final int TASK_TYPE_AMR_URGENT = 8;
    public static final int TASK_TYPE_BG_RPC = 1;
    public static final int TASK_TYPE_FG_MULTIMEDIA = 5;
    public static final int TASK_TYPE_FG_RPC = 0;
    public static final int TASK_TYPE_H5 = 6;
    public static final int TASK_TYPE_IMG = 2;
    public static final int TASK_TYPE_LOG = 7;
    public static final int TASK_TYPE_URGENT = 4;
    private static TaskExecutorManager a = null;
    private ActThreadPoolExecutor b = null;
    private ActThreadPoolExecutor c = null;
    private ActThreadPoolExecutor d = null;
    private ActThreadPoolExecutor e = null;
    private ActThreadPoolExecutor f = null;
    private ActThreadPoolExecutor g = null;
    private ActThreadPoolExecutor h = null;
    private ActThreadPoolExecutor i = null;
    private ActThreadPoolExecutor j = null;
    private FIFOPolicy k = null;
    private Context l;
    private TaskDoneObserver m;
    private AtomicBoolean n = new AtomicBoolean(false);
    private Map<String, ZFutureTask> o = new ConcurrentHashMap();
    private Map<String, ZFutureTask> p = new ConcurrentHashMap();

    final class FIFOPolicy implements RejectedExecutionHandler {
        private FIFOPolicy() {
        }

        public final void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            if (!executor.isShutdown()) {
                Runnable runnable = (Runnable) executor.getQueue().poll();
                if (runnable instanceof ZFutureTask) {
                    ZFutureTask task = (ZFutureTask) runnable;
                    task.fail(new Exception("Time out."));
                    LogCatUtil.debug(TaskExecutorManager.TAG, "FIFOPolicy give up, taskId: " + task.getTaskId());
                }
                executor.execute(r);
            }
        }
    }

    class TaskDoneObserver implements Observer {
        TaskDoneObserver() {
        }

        public void update(Observable observable, Object data) {
            ZFutureTask task = (ZFutureTask) data;
            if (!task.isOtcWaitTask()) {
                TaskExecutorManager.this.a(task.getTaskId());
                TaskExecutorManager.this.c(task);
                TaskExecutorManager.this.a(task);
            }
        }
    }

    private TaskExecutorManager() {
    }

    private TaskExecutorManager(Context context) {
        this.l = context;
    }

    public static TaskExecutorManager getInstance(Context context) {
        if (a != null) {
            return a;
        }
        synchronized (TaskExecutorManager.class) {
            try {
                if (a == null) {
                    a = new TaskExecutorManager(context);
                }
            }
        }
        return a;
    }

    public FutureTask execute(ZFutureTask futureTask) {
        ActThreadPoolExecutor executor = a(futureTask.getTaskType());
        a(futureTask, executor);
        futureTask.addDoneObserver(b());
        b(futureTask);
        a(executor);
        try {
            executor.execute(futureTask);
            return futureTask;
        } catch (Exception e2) {
            LogCatUtil.error(TAG, "execute ex:" + executor.toString(), e2);
            a(futureTask.getTaskId());
            throw new RuntimeException(e2);
        }
    }

    private void a(ZFutureTask futureTask, ActThreadPoolExecutor executor) {
        LogCatUtil.info(TAG, b(executor) + "  TaskId: " + futureTask.getTaskId());
    }

    private void a(ActThreadPoolExecutor executor) {
        if (!NetworkUtils.is2GMobileNetwork(this.l) && !NetworkUtils.is3GMobileNetwork(this.l)) {
            return;
        }
        if (executor.isHighPriorityThreadPool() && !this.n.get()) {
            this.n.set(true);
            LogCatUtil.debug(TAG, "ifDoPauseBgExecutor mPauseBgExecutor=[" + this.n.get() + "]");
        } else if (this.n.get() && !executor.isHighPriorityThreadPool()) {
            synchronized (this) {
                if (this.n.get() && !executor.isPaused()) {
                    executor.pause();
                    LogCatUtil.debug(TAG, "ifDoPauseBgExecutor pause executor");
                }
            }
        }
    }

    public ActThreadPoolExecutor getFgExecutor() {
        return c(this.l, a());
    }

    public ActThreadPoolExecutor getH5Executor() {
        return d(this.l, a());
    }

    public ActThreadPoolExecutor getBgExecutor() {
        return a(this.l, (RejectedExecutionHandler) a());
    }

    public ActThreadPoolExecutor getImgExecutor() {
        return f(this.l, a());
    }

    public ActThreadPoolExecutor getAmrExecutor() {
        return g(this.l, a());
    }

    public ActThreadPoolExecutor getAmrUrgentExecutor() {
        return h(this.l, a());
    }

    public ActThreadPoolExecutor getUrgentExecutor() {
        return i(this.l, a());
    }

    public ActThreadPoolExecutor getFgMultimediaExecutor() {
        return e(this.l, a());
    }

    public ActThreadPoolExecutor getLogExecutor() {
        return b(this.l, a());
    }

    public synchronized void closeAllSingleThreadPool() {
        closeThreadPool(this.b);
        this.b = null;
        closeThreadPool(this.c);
        this.c = null;
        closeThreadPool(this.d);
        this.d = null;
        closeThreadPool(this.e);
        this.e = null;
        closeThreadPool(this.g);
        this.g = null;
        closeThreadPool(this.i);
        this.i = null;
        closeThreadPool(this.j);
        this.j = null;
        closeThreadPool(this.f);
        this.f = null;
    }

    public void closeThreadPool(ThreadPoolExecutor threadPoolExecutor) {
        if (threadPoolExecutor != null) {
            try {
                threadPoolExecutor.shutdown();
            } catch (Exception e2) {
                LogCatUtil.warn((String) TAG, "closeThreadPool exception : " + e2.toString());
            }
        }
    }

    private ActThreadPoolExecutor a(int taskType) {
        switch (taskType) {
            case 0:
                ActThreadPoolExecutor fgExecutor = getFgExecutor();
                fgExecutor.setTaskTypeName("TASK_TYPE_FG_RPC");
                fgExecutor.setTaskType(taskType);
                return fgExecutor;
            case 1:
                ActThreadPoolExecutor bgEexecutor = getBgExecutor();
                bgEexecutor.setTaskTypeName("TASK_TYPE_BG_RPC");
                bgEexecutor.setTaskType(taskType);
                return bgEexecutor;
            case 2:
                ActThreadPoolExecutor rsrcExecutor = getImgExecutor();
                rsrcExecutor.setTaskTypeName("TASK_TYPE_IMG");
                rsrcExecutor.setTaskType(taskType);
                return rsrcExecutor;
            case 4:
                ActThreadPoolExecutor urgentExecutor = getUrgentExecutor();
                urgentExecutor.setTaskTypeName("TASK_TYPE_URGENT");
                urgentExecutor.setTaskType(taskType);
                return urgentExecutor;
            case 5:
                ActThreadPoolExecutor fgMultimediaExecutor = getFgMultimediaExecutor();
                fgMultimediaExecutor.setTaskTypeName("TASK_TYPE_FG_MULTIMEDIA");
                fgMultimediaExecutor.setTaskType(taskType);
                return fgMultimediaExecutor;
            case 6:
                ActThreadPoolExecutor h5Executor = getH5Executor();
                h5Executor.setTaskTypeName("TASK_TYPE_H5");
                h5Executor.setTaskType(taskType);
                return h5Executor;
            case 7:
                ActThreadPoolExecutor logExecutor = getLogExecutor();
                logExecutor.setTaskTypeName("TASK_TYPE_LOG");
                logExecutor.setTaskType(taskType);
                return logExecutor;
            case 8:
                ActThreadPoolExecutor amrUrgentExecutor = getAmrUrgentExecutor();
                amrUrgentExecutor.setTaskTypeName("TASK_TYPE_AMR_URGENT");
                amrUrgentExecutor.setTaskType(taskType);
                return amrUrgentExecutor;
            default:
                ActThreadPoolExecutor amrExecutor = getAmrExecutor();
                amrExecutor.setTaskTypeName("TASK_TYPE_AMR");
                amrExecutor.setTaskType(taskType);
                return amrExecutor;
        }
    }

    private FIFOPolicy a() {
        if (this.k != null) {
            return this.k;
        }
        this.k = new FIFOPolicy();
        return this.k;
    }

    @TargetApi(9)
    private ActThreadPoolExecutor a(Context context, RejectedExecutionHandler handler) {
        if (this.b != null) {
            return this.b;
        }
        synchronized (this) {
            if (this.b == null) {
                this.b = NetThreadPoolExeFactory.getBgThreadPool(context, handler);
            }
        }
        return this.b;
    }

    @TargetApi(9)
    private ActThreadPoolExecutor b(Context context, RejectedExecutionHandler handler) {
        if (this.j != null) {
            return this.j;
        }
        synchronized (this) {
            if (this.j == null) {
                this.j = NetThreadPoolExeFactory.getBgThreadPool(context, handler);
                this.j.setThreadFactory(new NetThreadFactory(ReportManager.LOG_PATH));
            }
        }
        return this.j;
    }

    @TargetApi(9)
    private ActThreadPoolExecutor c(Context context, RejectedExecutionHandler handler) {
        if (this.c != null) {
            return this.c;
        }
        synchronized (this) {
            if (this.c == null) {
                this.c = NetThreadPoolExeFactory.getFgThreadPool(context, handler);
            }
        }
        return this.c;
    }

    @TargetApi(9)
    private ActThreadPoolExecutor d(Context context, RejectedExecutionHandler handler) {
        if (this.i != null) {
            return this.i;
        }
        synchronized (this) {
            if (this.i == null) {
                this.i = NetThreadPoolExeFactory.getH5ThreadPool(context, handler);
                this.i.setThreadFactory(new NetThreadFactory(LoginConstants.H5_LOGIN));
            }
        }
        return this.i;
    }

    @TargetApi(9)
    private ActThreadPoolExecutor e(Context context, RejectedExecutionHandler handler) {
        if (this.h != null) {
            return this.h;
        }
        synchronized (this) {
            if (this.h == null) {
                this.h = NetThreadPoolExeFactory.getFgMultimediaThreadPool(context, handler);
            }
        }
        return this.h;
    }

    @TargetApi(9)
    private ActThreadPoolExecutor f(Context context, RejectedExecutionHandler handler) {
        if (this.d != null) {
            return this.d;
        }
        synchronized (this) {
            if (this.d == null) {
                this.d = NetThreadPoolExeFactory.getImgThreadPool(context, handler);
            }
        }
        return this.d;
    }

    @TargetApi(9)
    private ActThreadPoolExecutor g(Context context, RejectedExecutionHandler handler) {
        if (this.e != null) {
            return this.e;
        }
        synchronized (this) {
            if (this.e == null) {
                this.e = NetThreadPoolExeFactory.getAmrThreadPool(context, handler);
            }
        }
        return this.e;
    }

    @TargetApi(9)
    private ActThreadPoolExecutor h(Context context, RejectedExecutionHandler handler) {
        if (this.f != null) {
            return this.f;
        }
        synchronized (this) {
            if (this.f == null) {
                this.f = NetThreadPoolExeFactory.getAmrUrgentThreadPool(context, handler);
            }
        }
        return this.f;
    }

    @TargetApi(9)
    private ActThreadPoolExecutor i(Context context, RejectedExecutionHandler handler) {
        if (this.g != null) {
            return this.g;
        }
        synchronized (this) {
            if (this.g == null) {
                this.g = NetThreadPoolExeFactory.getUrgentThreadPool(context, handler);
            }
        }
        return this.g;
    }

    private String b(ActThreadPoolExecutor executor) {
        try {
            return String.format(getClass().getSimpleName() + MetaRecord.LOG_SEPARATOR + hashCode() + ": TaskTypeName = %s, Active Task = %d, Completed Task = %d, All Task = %d, Queue Size = %d", new Object[]{executor.getTaskTypeName(), Integer.valueOf(executor.getActiveCount()), Long.valueOf(executor.getCompletedTaskCount()), Long.valueOf(executor.getTaskCount()), Integer.valueOf(executor.getQueue().size())});
        } catch (Exception e2) {
            LogCatUtil.warn((String) TAG, "dumpPerf log exception : " + e2.toString());
            return "";
        }
    }

    private TaskDoneObserver b() {
        if (this.m == null) {
            this.m = new TaskDoneObserver();
        }
        return this.m;
    }

    /* access modifiers changed from: private */
    public void a(ZFutureTask httpTask) {
        LogCatUtil.debug(TAG, "TaskId: " + httpTask.getTaskId() + " DONE");
        if ((httpTask.getTaskType() == 0 || httpTask.getTaskType() == 4) && this.n.get() && getFgExecutor().getActiveCount() + getUrgentExecutor().getActiveCount() <= 1) {
            ActThreadPoolExecutor bgExecutor = getBgExecutor();
            ActThreadPoolExecutor imgExecutor = getImgExecutor();
            ActThreadPoolExecutor amrExecutor = getAmrExecutor();
            ActThreadPoolExecutor logExecutor = getLogExecutor();
            synchronized (this) {
                if (bgExecutor.isPaused()) {
                    bgExecutor.resume();
                }
                if (imgExecutor.isPaused()) {
                    imgExecutor.resume();
                }
                if (amrExecutor.isPaused()) {
                    amrExecutor.resume();
                }
                if (logExecutor.isPaused()) {
                    logExecutor.resume();
                }
                this.n.set(false);
            }
            LogCatUtil.debug(TAG, "resetPauseBgExecutor mPauseBgExecutor=[" + this.n.get() + "]");
        }
    }

    private void b(ZFutureTask httpTask) {
        this.o.put(httpTask.getTaskId(), httpTask);
    }

    /* access modifiers changed from: private */
    public ZFutureTask a(String taskId) {
        return this.o.remove(taskId);
    }

    private ZFutureTask b(String taskId) {
        return this.p.remove(taskId);
    }

    /* access modifiers changed from: private */
    public void c(ZFutureTask task) {
        ZFutureTask otcWaitTask = b(task.getTaskId());
        if (otcWaitTask != null && otcWaitTask != task) {
            String logTemplete = "setResp2OtcWaitTask#%s otcWaitTaskType=[" + otcWaitTask.getTaskType() + "]  TaskId=" + otcWaitTask.getTaskId();
            try {
                otcWaitTask.set(task.get());
                LogCatUtil.debug(TAG, String.format(logTemplete, new Object[]{"otcWaitTask.set(..);"}));
            } catch (Exception e2) {
                otcWaitTask.setException(e2);
                LogCatUtil.debug(TAG, String.format(logTemplete, new Object[]{"otcWaitTask.setException(..);"}));
            }
        }
    }
}
