package com.alipay.mobile.beehive.photo.util;

import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;

public class BackgroundTaskUtil {
    public static void executeNormal(Runnable r) {
        TaskScheduleService ts = (TaskScheduleService) MicroServiceUtil.getMicroService(TaskScheduleService.class);
        if (ts != null) {
            ts.acquireExecutor(ScheduleType.NORMAL).execute(r);
        } else {
            PhotoLogger.e("BackgroundTaskUtil", "executeNormal:TaskScheduleService null.");
        }
    }
}
