package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.TaskScheduleManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ImageTaskEngine {
    private static final Logger a = Logger.getLogger((String) "ImageNetTaskEngine");
    private static ImageTaskEngine g = new ImageTaskEngine();
    private ConcurrentHashMap<String, ImageNetTask> b = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Future> c = new ConcurrentHashMap<>();
    private ExecutorService d = TaskScheduleManager.get().djangoImageExecutor();
    private ExecutorService e = TaskScheduleManager.get().localImageExecutor();
    private ExecutorService f = TaskScheduleManager.get().commonExecutor();
    private final ConcurrentHashMap<String, Lock> h = new ConcurrentHashMap<>();

    private ImageTaskEngine() {
    }

    public static ImageTaskEngine get() {
        return g;
    }

    public Future submit(ImageNetTask task) {
        Future future;
        if (task == null) {
            a.e("submit task is null", new Object[0]);
            return null;
        }
        String taskId = task.getTaskId();
        ImageNetTask netTask = this.b.get(taskId);
        Lock lock = null;
        if (netTask == null) {
            lock = a(taskId);
            lock.lock();
            netTask = this.b.get(taskId);
        }
        if (netTask == null) {
            try {
                a.d("new task: " + task + ", taskId: " + taskId, new Object[0]);
                if (task.loadReq.taskModel != null) {
                    task.loadReq.taskModel.setTaskId(taskId);
                }
                this.b.put(taskId, task);
            } catch (Throwable th) {
                if (lock != null) {
                    this.h.remove(taskId);
                    lock.unlock();
                }
                throw th;
            }
        } else {
            a.d("merge to task: " + netTask + ", taskId: " + taskId, new Object[0]);
            netTask.addImageLoadReq(task.loadReq);
        }
        synchronized (this.c) {
            future = this.c.get(taskId);
            if (future == null) {
                future = this.d.submit(task);
                this.c.put(taskId, future);
            }
        }
        if (lock != null) {
            this.h.remove(taskId);
            lock.unlock();
        }
        if (!task.loadReq.options.isSyncLoading()) {
            return future;
        }
        try {
            future.get();
            return future;
        } catch (Exception e2) {
            a.e(e2, "future get exception", new Object[0]);
            return future;
        }
    }

    public Future submit(ImageLocalTask task) {
        if (task == null) {
            return null;
        }
        return a(this.e, task);
    }

    public Future submit(ImageCustomLocalTask task) {
        if (task == null) {
            return null;
        }
        return a(this.e, task);
    }

    public Future submit(ImageLocalSmartCutTask task) {
        if (task == null) {
            return null;
        }
        return a(this.e, task);
    }

    public Future submit(ImageBase64Task task) {
        if (task == null) {
            return null;
        }
        return a(this.e, task);
    }

    public Future submit(ImageDisplayTask task) {
        if (task == null) {
            return null;
        }
        return a(this.f, task);
    }

    private static Future a(ExecutorService executor, ImageTask task) {
        if (!task.loadReq.options.isSyncLoading()) {
            return executor.submit(task);
        }
        try {
            task.call();
            return null;
        } catch (Exception e2) {
            a.e(e2, "syncOrSubmit sync execute error", new Object[0]);
            return null;
        }
    }

    private Lock a(String taskId) {
        this.h.putIfAbsent(taskId, new ReentrantLock());
        return this.h.get(taskId);
    }

    public void submit(Runnable runnable) {
        if (runnable != null) {
            this.f.submit(runnable);
        }
    }
}
