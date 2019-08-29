package com.alipay.mobile.common.nbnet.biz.task;

import com.alipay.mobile.common.nbnet.biz.GlobalNBNetContext;
import com.alipay.mobile.common.transport.concurrent.TaskExecutorManager;
import com.alipay.mobile.common.transport.concurrent.ZFutureTask;
import java.util.concurrent.Future;

public class JobSchedulerImpl implements JobScheduler {
    private TaskExecutorManager a = TaskExecutorManager.getInstance(GlobalNBNetContext.a().getApplicationContext());

    public final Future a(Job job) {
        ZFutureTask zFutureTask = new ZFutureTask(job, 5);
        this.a.execute(zFutureTask);
        return zFutureTask;
    }
}
