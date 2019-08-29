package com.alipay.android.phone.mobilecommon.multimediabiz.biz.task;

import android.annotation.SuppressLint;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.others.TaskUtils;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskEngine {
    private ExecutorService a;
    private int b = TaskUtils.getTaskOccurs(TaskConstants.DEFAULT_MAX_TASK_OCCURS);

    public void setCoreSize(int coreSize) {
        if (coreSize > 0 && coreSize < TaskConstants.DEFAULT_MAX_TASK_OCCURS) {
            this.b = coreSize;
        }
    }

    public ExecutorService loadMMTaskExecutor() {
        if (this.a == null) {
            this.a = createLoadExecutor("mmTask", false, this.b);
        }
        return this.a;
    }

    public ExecutorService createLoadExecutor(String name, boolean lifo, int maximumPoolSize) {
        int i = maximumPoolSize;
        ThreadPoolExecutor executor = new ThreadPoolExecutor(TaskUtils.getTaskOccurs(this.b), i, 60, TimeUnit.SECONDS, new LinkedBlockingDeque(), new DefaultThreadFactory(name));
        a(executor);
        return executor;
    }

    @SuppressLint({"NewApi"})
    private static void a(ExecutorService executor) {
        ((ThreadPoolExecutor) executor).allowCoreThreadTimeOut(true);
    }
}
