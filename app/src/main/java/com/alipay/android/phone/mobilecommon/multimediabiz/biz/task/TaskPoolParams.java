package com.alipay.android.phone.mobilecommon.multimediabiz.biz.task;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.others.TaskUtils;

public class TaskPoolParams {
    public int mCoreSize = TaskUtils.getTaskOccurs(TaskConstants.DEFAULT_MAX_TASK_OCCURS);
    public int mMaxOccurs = TaskUtils.getTaskOccurs(TaskConstants.DEFAULT_MAX_TASK_OCCURS);
}
