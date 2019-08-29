package com.alipay.mobile.common.transport.concurrent;

import android.text.TextUtils;
import com.alipay.mobile.common.transport.http.HttpTask;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ActThreadPoolExecutor extends ThreadPoolExecutor {
    private String a = "";
    private int b;
    protected Condition mPauseCondition = this.mPauseLock.newCondition();
    protected ReentrantLock mPauseLock = new ReentrantLock();
    protected boolean mPaused = false;

    public ActThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public ActThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public ActThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public ActThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    /* access modifiers changed from: protected */
    public void beforeExecute(Thread t, Runnable r) {
        this.mPauseLock.lock();
        try {
            if (r instanceof HttpTask) {
                ((HttpTask) r).setTaskState(0);
            }
            if (isPaused()) {
                b(r);
                this.mPauseCondition.await();
            }
            super.beforeExecute(t, r);
        } catch (InterruptedException e) {
            LogCatUtil.warn((String) "ActThreadPoolExecutor", (Throwable) e);
            t.interrupt();
        } finally {
            this.mPauseLock.unlock();
        }
        a(r);
    }

    private void a(Runnable r) {
        if (r instanceof HttpTask) {
            HttpTask httpTask = (HttpTask) r;
            if (!TextUtils.isEmpty(httpTask.getOperationType())) {
                LogCatUtil.debug("ActThreadPoolExecutor", "beforeExecute: " + this.a + "," + httpTask.getOperationType());
            } else {
                LogCatUtil.debug("ActThreadPoolExecutor", "beforeExecute: " + this.a + "," + httpTask.getUrl());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void afterExecute(Runnable r, Throwable t) {
        if (r instanceof HttpTask) {
            ((HttpTask) r).setTaskState(2);
        }
        super.afterExecute(r, t);
    }

    public void pause() {
        this.mPauseLock.lock();
        try {
            LogCatUtil.debug("ActThreadPoolExecutor", "mTaskTypeName：" + this.a + ", pause");
            a();
        } finally {
            this.mPauseLock.unlock();
        }
    }

    public void resume() {
        this.mPauseLock.lock();
        try {
            LogCatUtil.debug("ActThreadPoolExecutor", "mTaskTypeName：" + this.a + ",resume");
            b();
            this.mPauseCondition.signalAll();
        } finally {
            this.mPauseLock.unlock();
        }
    }

    public String getTaskTypeName() {
        return this.a;
    }

    public void setTaskTypeName(String taskTypeName) {
        this.a = taskTypeName;
    }

    public int getTaskType() {
        return this.b;
    }

    public void setTaskType(int taskType) {
        this.b = taskType;
    }

    public boolean isHighPriorityThreadPool() {
        return this.b == 0 || this.b == 4 || this.b == 5 || this.b == 6 || this.b == 8;
    }

    public boolean isPaused() {
        this.mPauseLock.lock();
        try {
            return this.mPaused;
        } finally {
            this.mPauseLock.unlock();
        }
    }

    private void a() {
        this.mPaused = true;
    }

    private void b() {
        this.mPaused = false;
    }

    private void b(Runnable r) {
        if (r instanceof HttpTask) {
            HttpTask httpTask = (HttpTask) r;
            if (!TextUtils.isEmpty(httpTask.getOperationType())) {
                LogCatUtil.debug("ActThreadPoolExecutor", "beforeExecute.await: " + this.a + "," + httpTask.getOperationType());
            } else {
                LogCatUtil.debug("ActThreadPoolExecutor", "beforeExecute.await: " + this.a + "," + httpTask.getUrl());
            }
        }
    }
}
