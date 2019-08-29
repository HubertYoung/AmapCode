package com.alipay.mobile.nebulacore.wallet;

import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.nebula.provider.H5TaskScheduleProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;

public class H5TaskScheduleProviderImpl implements H5TaskScheduleProvider {
    public static final String TAG = "H5TaskScheduleProviderImpl";

    public boolean addIdleTask(Runnable task, String threadName, int taskWeight) {
        TaskScheduleService taskService = (TaskScheduleService) H5Utils.findServiceByInterface(TaskScheduleService.class.getName());
        if (taskService == null) {
            H5Log.e((String) TAG, (String) "taskService==null");
            return false;
        }
        H5Log.d(TAG, "get taskService add addIdleTask");
        return taskService.addIdleTask(task, threadName, taskWeight);
    }
}
