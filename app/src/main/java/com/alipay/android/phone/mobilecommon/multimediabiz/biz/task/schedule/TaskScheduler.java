package com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.schedule;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.TaskStatistics;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.MMTask;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.TaskConstants;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.TaskEngine;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.TaskPoolParams;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.others.TaskLog;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.others.TaskUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.taskqueue.TaskQueue;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TaskScheduler implements ITaskScheduler, Observer {
    private TaskQueue a = new TaskQueue();
    private TaskEngine b = new TaskEngine();
    private int c = TaskUtils.getTaskOccurs(TaskConstants.DEFAULT_MAX_TASK_OCCURS);
    private AtomicInteger d = new AtomicInteger(0);
    private final ConcurrentHashMap<String, Future> e = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, MMTask> f = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Lock> g = new ConcurrentHashMap<>();
    private Boolean h = null;
    private String i;

    public TaskScheduler(TaskPoolParams params, String key) {
        this.i = key;
        this.a.addObserver(this);
        a(params);
    }

    private void a(TaskPoolParams params) {
        if (params != null && this.h == null) {
            this.h = Boolean.valueOf(true);
            this.c = params.mMaxOccurs;
            this.b.setCoreSize(params.mCoreSize);
        }
    }

    public MMTask addTask(MMTask task) {
        return b(task);
    }

    private void a(MMTask task) {
        boolean z = false;
        task.addObserver(this);
        TaskLog.D("TaskScheduler", "addTask task=" + task.toString() + ";mCurrOccurs=" + this.d + ";maxOccurs=" + this.c + ";" + this.a.toString(), new Object[0]);
        this.a.addTask(task);
        TaskStatistics instance = TaskStatistics.getInstance();
        String str = this.i;
        if (this.d.get() >= this.c) {
            z = true;
        }
        instance.addCount(str, z);
    }

    public void removeTask(String taskId) {
        TaskLog.D("TaskScheduler", "removeTask taskId: " + taskId, new Object[0]);
        MMTask task = a(taskId);
        if (task != null) {
            if (this.a.isTaskInQueue(task)) {
                this.a.removeTask(task);
                task.waitUnlock();
            }
            synchronized (this.e) {
                this.f.remove(taskId);
                this.e.remove(taskId);
            }
        }
    }

    public MMTask cancelTask(String taskId) {
        TaskLog.D("TaskScheduler", "cancelTask taskId: " + taskId, new Object[0]);
        MMTask task = a(taskId);
        Future future = c(taskId);
        if (task != null) {
            removeTask(taskId);
            task.cancel();
            if (future == null) {
                TaskLog.D("TaskScheduler", "cancelTask taskId: " + taskId + ", task is waiting in queue, but cancelled~", new Object[0]);
                task.onStateChange(2);
            }
        }
        if (future != null) {
            future.cancel(true);
            if (task != null) {
                TaskLog.D("TaskScheduler", "cancelTask taskId: " + taskId + ", task is calling, but cancelled~", new Object[0]);
                task.onStateChange(2);
            }
        }
        return task;
    }

    public MMTask getTask(String taskId) {
        if (TextUtils.isEmpty(taskId)) {
            return null;
        }
        return a(taskId);
    }

    private MMTask b(MMTask task) {
        String taskId = task.getTaskId();
        MMTask mmTask = a(taskId);
        Lock lock = null;
        if (mmTask == null) {
            lock = b(taskId);
            lock.lock();
            mmTask = a(taskId);
        }
        try {
            task.notifyAddTask();
            if (mmTask == null) {
                c(task);
                a(task);
                task.onAddTask();
            } else {
                TaskLog.D("TaskScheduler", "merge to task: " + task + ", mmTask: " + mmTask, new Object[0]);
                mmTask.onMergeTask(task);
                if (task.getPriority() > mmTask.getPriority() && this.a.isTaskInQueue(mmTask)) {
                    mmTask.setPriority(task.getPriority());
                    this.a.removeTask(mmTask);
                    this.a.addTask(mmTask);
                }
            }
            if (mmTask != null) {
                return mmTask;
            }
            return task;
        } finally {
            if (lock != null) {
                this.g.remove(taskId);
                lock.unlock();
            }
        }
    }

    private MMTask a() {
        return this.a.getTask();
    }

    private void c(MMTask task) {
        synchronized (this.f) {
            this.f.put(task.getTaskId(), task);
        }
    }

    private MMTask a(String taskId) {
        MMTask mMTask;
        synchronized (this.f) {
            mMTask = this.f.get(taskId);
        }
        return mMTask;
    }

    private Lock b(String taskId) {
        this.g.putIfAbsent(taskId, new ReentrantLock());
        return this.g.get(taskId);
    }

    private Future b() {
        MMTask task = a();
        Future future = null;
        if (task != null) {
            this.d.incrementAndGet();
            future = this.b.loadMMTaskExecutor().submit(task);
        }
        if (future != null) {
            a(task.getTaskId(), future);
        }
        return future;
    }

    private void a(String taskId, Future future) {
        synchronized (this.e) {
            this.e.put(taskId, future);
        }
    }

    private Future c(String taskId) {
        Future future;
        synchronized (this.e) {
            future = this.e.get(taskId);
        }
        return future;
    }

    public void update(Observable arg0, Object arg1) {
        if (arg1 != null) {
            if (this.d.get() > 0) {
                this.d.decrementAndGet();
            }
            removeTask((String) arg1);
        }
        TaskLog.P("TaskScheduler", "update mCurrOccurs=" + this.d.get() + ";MAX_OCCURS=" + this.c + ";arg1=" + arg1, new Object[0]);
        if (this.d.get() >= 0 && this.d.get() < this.c) {
            b();
        }
    }
}
