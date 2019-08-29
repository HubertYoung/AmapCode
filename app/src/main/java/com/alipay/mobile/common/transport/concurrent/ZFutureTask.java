package com.alipay.mobile.common.transport.concurrent;

import android.text.TextUtils;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class ZFutureTask<V> extends FutureTask<V> {
    public static final int TASK_STATE_AFTER_RUN = 2;
    public static final int TASK_STATE_BEFORE_RUN = 0;
    public static final int TASK_STATE_DONE = 4;
    public static final int TASK_STATE_READY = -1;
    public static final int TASK_STATE_RUNNING = 1;
    protected Callable<V> callable;
    protected Observable mObservable;
    protected boolean mOtcWaitTask;
    protected String mTaskId;
    protected int mTaskState;
    protected int mTaskType;

    class DoneObservable extends Observable {
        DoneObservable() {
        }

        public void notifyObservers(Object data) {
            setChanged();
            super.notifyObservers(data);
        }
    }

    public ZFutureTask(Callable<V> callable2, int taskType) {
        super(callable2);
        this.mTaskState = -1;
        this.mTaskType = taskType;
        this.callable = callable2;
    }

    public ZFutureTask(Runnable runnable, V result, int taskType) {
        this(Executors.callable(runnable, result), taskType);
    }

    public void run() {
        this.mTaskState = 1;
        super.run();
    }

    /* access modifiers changed from: protected */
    public void done() {
        this.mTaskState = 4;
        this.mObservable.notifyObservers(this);
        super.done();
    }

    /* access modifiers changed from: protected */
    public void set(V v) {
        super.set(v);
    }

    /* access modifiers changed from: protected */
    public void setException(Throwable t) {
        super.setException(t);
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        return super.cancel(mayInterruptIfRunning);
    }

    public void fail(Throwable t) {
        setException(t);
    }

    public void addDoneObserver(Observer doneObserver) {
        if (this.mObservable == null) {
            this.mObservable = new DoneObservable();
        }
        this.mObservable.addObserver(doneObserver);
    }

    public int getTaskState() {
        return this.mTaskState;
    }

    public void setTaskState(int taskState) {
        this.mTaskState = taskState;
    }

    public void setTaskType(int taskType) {
        this.mTaskType = taskType;
    }

    public int getTaskType() {
        return this.mTaskType;
    }

    public String getTaskId() {
        if (TextUtils.isEmpty(this.mTaskId)) {
            this.mTaskId = String.valueOf(this.callable.hashCode());
        }
        return this.mTaskId;
    }

    public boolean isOtcWaitTask() {
        return this.mOtcWaitTask;
    }

    public void setOtcWaitTask(boolean otcWaitTask) {
        this.mOtcWaitTask = otcWaitTask;
    }

    public boolean isRunning() {
        if (isDone() || this.mTaskState != 1) {
            return false;
        }
        return true;
    }

    public boolean isReady() {
        if (isDone() || this.mTaskState != -1) {
            return false;
        }
        return true;
    }

    public boolean isBeforeRun() {
        if (isDone() || this.mTaskState != 0) {
            return false;
        }
        return true;
    }
}
