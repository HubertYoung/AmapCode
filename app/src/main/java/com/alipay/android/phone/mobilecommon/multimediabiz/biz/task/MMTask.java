package com.alipay.android.phone.mobilecommon.multimediabiz.biz.task;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.others.TaskLog;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.util.Collections;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class MMTask<V> extends Observable implements Callable<V> {
    private int a = 5;
    private String b = "MMTask";
    private boolean c = false;
    private boolean d = false;
    private V e = null;
    protected AtomicInteger mState = new AtomicInteger(0);
    protected final Set<CountDownLatch> waitLatchs = Collections.synchronizedSet(new HashSet());

    public abstract void onMergeTask(MMTask mMTask);

    public abstract void onStateChange(int i);

    public abstract V taskRun();

    public void setPriority(int priority) {
        if (priority > 0 && this.a <= 10) {
            this.a = priority;
        }
    }

    public int getPriority() {
        return this.a;
    }

    public void enableLIFO() {
        this.c = true;
    }

    public boolean isLIFO() {
        return this.c;
    }

    public void setTag(String tag) {
        if (!TextUtils.isEmpty(tag)) {
            this.b = tag;
        }
    }

    /* JADX INFO: finally extract failed */
    public final V call() {
        try {
            if (isCanceled()) {
                onStateChange(2);
                TaskLog.D("MMTask", "call canceled task=" + toString() + ", hashCode: " + hashCode(), new Object[0]);
            } else {
                a(1);
                this.e = taskRun();
            }
            a();
            return this.e;
        } catch (Throwable th) {
            a();
            throw th;
        }
    }

    public void onAddTask() {
    }

    public final void notifyAddTask() {
        this.d = true;
    }

    public V get() {
        int state = getState();
        Logger.D("MMTask", "get: this: " + this + "hashCode: " + hashCode() + " get enter, bAdd: " + this.d + ", state: " + state, new Object[0]);
        if (3 == state || 2 == state) {
            return this.e;
        }
        if (this.d) {
            CountDownLatch waitLock = new CountDownLatch(1);
            a(waitLock);
            waitLock.await();
            return this.e;
        }
        throw new IllegalStateException("This task is not added");
    }

    private void a(CountDownLatch lock) {
        this.waitLatchs.add(lock);
        Logger.D("MMTask", "addWaitLocks hashCode: " + hashCode() + " lock: " + lock, new Object[0]);
    }

    public void waitUnlock() {
        for (CountDownLatch item : this.waitLatchs) {
            item.countDown();
            Logger.D("MMTask", "waitUnlock hashCode" + hashCode() + ", lock: " + item, new Object[0]);
        }
    }

    public int getState() {
        return this.mState.get();
    }

    public void cancel() {
        this.mState.set(2);
    }

    public boolean isCanceled() {
        return this.mState.get() == 2 || Thread.interrupted();
    }

    public boolean isRunning() {
        return this.mState.get() == 1;
    }

    public String getTaskId() {
        return null;
    }

    private void a() {
        try {
            TaskLog.D("MMTask", "call end to notify observables task=" + toString(), new Object[0]);
            setChanged();
            notifyObservers(getTaskId());
            if (this.mState.get() != 2) {
                a(3);
            }
        } finally {
            waitUnlock();
            r1 = "MMTask";
            r3 = "call end to waitUnlock task=";
            StringBuilder append = new StringBuilder(r3).append(toString());
            r3 = ", hashCode: ";
            TaskLog.D(r1, append.append(r3).append(hashCode()).toString(), new Object[0]);
        }
    }

    private void a(int state) {
        this.mState.set(state);
        onStateChange(state);
    }

    public String toString() {
        return "MMTask [mPriority=" + this.a + ", mState=" + this.mState + ";taskId=" + getTaskId() + ", tag=" + this.b + "]";
    }
}
