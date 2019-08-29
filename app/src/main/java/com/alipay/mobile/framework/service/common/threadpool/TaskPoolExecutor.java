package com.alipay.mobile.framework.service.common.threadpool;

import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.util.LoggingUtil;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.threadpool.TaskPoolRunnable.TaskType;
import com.mpaas.nebula.plugin.H5ServicePlugin;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TaskPoolExecutor extends ThreadPoolExecutor {
    private TaskType a;
    private int b;
    private boolean c;
    private boolean d = false;
    private ReentrantLock e = new ReentrantLock();
    private Condition f = this.e.newCondition();
    private long g = -1;
    private Set<Integer> h = new ConcurrentSkipListSet();
    private volatile int i = 10;
    private volatile boolean j = false;

    public TaskPoolExecutor(TaskType taskType, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, boolean allowCoreTimeout, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        super.allowCoreThreadTimeOut(allowCoreTimeout);
        this.a = taskType;
        this.b = -1;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void setThreadPriority(int threadPriority) {
        this.b = threadPriority;
    }

    public void shutdownValidly() {
        super.shutdown();
    }

    /* access modifiers changed from: protected */
    public void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        this.e.lock();
        try {
            if (this.c) {
                if (this.d) {
                    LoggerFactory.getTraceLogger().info("TaskPoolExecutor", "wanna pause thread pool, but config is off");
                    Log.i("TaskPoolExecutor", "wanna pause thread pool, but config is off");
                    return;
                }
                try {
                    Object configService = LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface("com.alipay.mobile.base.config.ConfigService");
                    Object value = configService.getClass().getDeclaredMethod(H5ServicePlugin.GET_CONFIG, new Class[]{String.class}).invoke(configService, new Object[]{"thread_govern_pause_thread_pool"});
                    LoggerFactory.getTraceLogger().info("TaskPoolExecutor", "thread_govern_pause_thread_pool:" + value);
                    Log.i("TaskPoolExecutor", "thread_govern_pause_thread_pool:" + value);
                    this.d = CameraParams.FLASH_MODE_OFF.equals(value);
                    if (this.d) {
                        LoggerFactory.getTraceLogger().info("TaskPoolExecutor", "wanna pause thread pool, but config is off");
                        Log.i("TaskPoolExecutor", "wanna pause thread pool, but config is off");
                        this.e.unlock();
                        return;
                    } else if (this.g <= 0 || SystemClock.elapsedRealtime() - this.g <= TimeUnit.SECONDS.toMillis(4)) {
                        Log.i("TaskPoolExecutor", "try run :" + r + " but thread pool request pause.");
                        LoggerFactory.getTraceLogger().info("TaskPoolExecutor", "try run :" + r + " but thread pool request pause.");
                        this.f.await(4, TimeUnit.SECONDS);
                    } else {
                        LoggerFactory.getTraceLogger().info("TaskPoolExecutor", "wanna  pause thread pool, but exceed max wait time.");
                        resume();
                        this.e.unlock();
                        return;
                    }
                } catch (Throwable th) {
                    LoggerFactory.getTraceLogger().error((String) "TaskPoolExecutor", (String) "get configService error.");
                    this.e.unlock();
                    return;
                }
            }
            this.e.unlock();
        } catch (InterruptedException e2) {
            t.interrupt();
        } finally {
            this.e.unlock();
        }
    }

    public void pause() {
        LoggerFactory.getTraceLogger().info("TaskPoolExecutor", "pause execute : " + this.a);
        Log.i("TaskPoolExecutor", "pause execute:" + this.a);
        this.e.lock();
        try {
            this.c = true;
            this.g = SystemClock.elapsedRealtime();
        } finally {
            this.e.unlock();
        }
    }

    public void resume() {
        LoggerFactory.getTraceLogger().info("TaskPoolExecutor", "resume execute:" + this.a);
        Log.i("TaskPoolExecutor", "resume execute:" + this.a);
        this.e.lock();
        try {
            this.c = false;
            this.g = -1;
            this.f.signalAll();
        } finally {
            this.e.unlock();
        }
    }

    public void yield() {
        try {
            Set<Integer> copy = new HashSet<>(this.h);
            LoggerFactory.getTraceLogger().info("TaskPoolExecutor", "yield thread pool " + this.a);
            for (Integer integer : copy) {
                if (!this.j) {
                    try {
                        this.i = Process.getThreadPriority(integer.intValue());
                        this.j = true;
                    } catch (Throwable th) {
                    }
                }
                Process.setThreadPriority(integer.intValue(), 19);
                LoggerFactory.getTraceLogger().error((String) "TaskPoolExecutor", "yield thread " + integer);
            }
        } catch (IllegalArgumentException e2) {
            LoggerFactory.getTraceLogger().error((String) "TaskPoolExecutor", "yield thread " + integer + " failed.");
            this.h.remove(integer);
        } catch (Throwable e3) {
            LoggerFactory.getTraceLogger().error("TaskPoolExecutor", "yield thread poll " + this.a + " error", e3);
        }
    }

    public void restore() {
        try {
            Set<Integer> copy = new HashSet<>(this.h);
            LoggerFactory.getTraceLogger().info("TaskPoolExecutor", "restore thread pool " + this.a);
            for (Integer integer : copy) {
                Process.setThreadPriority(integer.intValue(), this.i);
            }
        } catch (IllegalArgumentException e2) {
            this.h.remove(integer);
        } catch (Throwable e3) {
            LoggerFactory.getTraceLogger().error("TaskPoolExecutor", "restore thread poll " + this.a + " error", e3);
        }
    }

    public void execute(Runnable command) {
        super.execute(new TaskPoolRunnable(command, this.a, this.b, this.h));
    }

    public void shutdown() {
        if (TaskPoolDiagnose.isShutdownCheckInvoker(LoggingUtil.backTrackInvoker())) {
            TaskPoolDiagnose.shouldNotBeInvoked(this.a, "TaskPoolExecutor", "shutdown");
        }
        super.shutdown();
    }

    public List<Runnable> shutdownNow() {
        if (TaskPoolDiagnose.isShutdownCheckInvoker(LoggingUtil.backTrackInvoker())) {
            TaskPoolDiagnose.shouldNotBeInvoked(this.a, "TaskPoolExecutor", "shutdownNow");
        }
        return super.shutdownNow();
    }

    public void purge() {
        TaskPoolDiagnose.shouldNotBeInvoked(this.a, "TaskPoolExecutor", "purge");
        super.purge();
    }

    public void setCorePoolSize(int corePoolSize) {
        TaskPoolDiagnose.shouldNotBeInvoked(this.a, "TaskPoolExecutor", "setCorePoolSize");
        super.setCorePoolSize(corePoolSize);
    }

    public void setKeepAliveTime(long time, TimeUnit unit) {
        TaskPoolDiagnose.shouldNotBeInvoked(this.a, "TaskPoolExecutor", "setKeepAliveTime");
        super.setKeepAliveTime(time, unit);
    }

    public void setThreadFactory(ThreadFactory threadFactory) {
        TaskPoolDiagnose.shouldNotBeInvoked(this.a, "TaskPoolExecutor", "setThreadFactory");
        super.setThreadFactory(threadFactory);
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        TaskPoolDiagnose.shouldNotBeInvoked(this.a, "TaskPoolExecutor", "setMaximumPoolSize");
        super.setMaximumPoolSize(maximumPoolSize);
    }

    public void setRejectedExecutionHandler(RejectedExecutionHandler handler) {
        TaskPoolDiagnose.shouldNotBeInvoked(this.a, "TaskPoolExecutor", "setRejectedExecutionHandler");
        super.setRejectedExecutionHandler(handler);
    }

    public void allowCoreThreadTimeOut(boolean value) {
        TaskPoolDiagnose.shouldNotBeInvoked(this.a, "TaskPoolExecutor", "allowCoreThreadTimeOut");
        super.allowCoreThreadTimeOut(value);
    }
}
