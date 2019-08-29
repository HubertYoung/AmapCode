package com.alipay.mobile.quinox.asynctask;

import com.alipay.mobile.quinox.utils.StringUtil;
import java.util.Timer;
import java.util.TimerTask;

class ScheduleTimer {
    Timer workTimer = new Timer("ScheduleTimer", true);

    public static class Task extends TimerTask {
        Runnable runnable;
        String threadName;

        public void run() {
            if (this.runnable != null) {
                if (StringUtil.isEmpty(this.threadName)) {
                    this.threadName = this.runnable.getClass().getName();
                }
                AsyncTaskExecutor.getInstance().execute(this.runnable, this.threadName);
            }
        }
    }

    ScheduleTimer() {
    }

    public TimerTask schedule(Runnable runnable, String str, long j) {
        Task task = new Task();
        task.runnable = runnable;
        task.threadName = str;
        this.workTimer.schedule(task, j);
        return task;
    }
}
