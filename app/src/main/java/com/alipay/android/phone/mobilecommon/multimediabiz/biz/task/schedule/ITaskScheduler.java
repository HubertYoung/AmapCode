package com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.schedule;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.MMTask;

public interface ITaskScheduler {
    MMTask addTask(MMTask mMTask);

    MMTask cancelTask(String str);

    MMTask getTask(String str);

    void removeTask(String str);
}
