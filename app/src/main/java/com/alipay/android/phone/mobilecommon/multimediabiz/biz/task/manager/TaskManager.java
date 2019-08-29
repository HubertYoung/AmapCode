package com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.manager;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.TaskPoolParams;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.schedule.TaskScheduler;
import java.util.HashMap;

public class TaskManager {
    private static TaskManager a = null;
    private static final HashMap<String, TaskScheduler> b = new HashMap<>();

    private TaskManager() {
    }

    public static TaskManager getInstance() {
        if (a == null) {
            synchronized (TaskManager.class) {
                try {
                    if (a == null) {
                        a = new TaskManager();
                    }
                }
            }
        }
        return a;
    }

    public TaskScheduler createTaskScheduler(String key, TaskPoolParams params) {
        return a(key, params);
    }

    public TaskScheduler getTaskScheduler(String key) {
        return a(key, (TaskPoolParams) null);
    }

    public void removeTaskScheduler(String key) {
        b.remove(key);
    }

    private static TaskScheduler a(String key, TaskPoolParams params) {
        TaskScheduler scheduler;
        synchronized (b) {
            scheduler = a(key);
            if (scheduler == null) {
                scheduler = new TaskScheduler(params, key);
                a(key, scheduler);
            }
        }
        return scheduler;
    }

    private static void a(String key, TaskScheduler service) {
        b.put(key, service);
    }

    private static TaskScheduler a(String key) {
        return b.get(key);
    }
}
