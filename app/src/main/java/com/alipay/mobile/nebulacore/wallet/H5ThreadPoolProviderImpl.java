package com.alipay.mobile.nebulacore.wallet;

import android.text.TextUtils;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;
import com.alipay.mobile.nebula.provider.H5ThreadPoolProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.wallet.H5ThreadPoolFactory;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

public class H5ThreadPoolProviderImpl implements H5ThreadPoolProvider {
    public ThreadPoolExecutor getExecutor(String scheduleType) {
        H5Log.d("H5ThreadPoolProviderImpl", "H5ThreadPoolProviderImpl:" + scheduleType);
        TaskScheduleService scheduleService = (TaskScheduleService) H5Utils.findServiceByInterface(TaskScheduleService.class.getName());
        if (scheduleService == null) {
            H5Log.d("H5ThreadPoolProviderImpl", "scheduleService == null");
            return null;
        }
        if (!TextUtils.isEmpty(scheduleType) && !scheduleType.equals(H5ThreadType.URGENT)) {
            if (scheduleType.equals(H5ThreadType.NORMAL)) {
                return scheduleService.acquireExecutor(ScheduleType.NORMAL);
            }
            if (scheduleType.equals(H5ThreadType.IO)) {
                return scheduleService.acquireExecutor(ScheduleType.IO);
            }
            if (scheduleType.equals("RPC")) {
                return scheduleService.acquireExecutor(ScheduleType.RPC);
            }
            if (scheduleType.equals(H5ThreadType.MMS_HTTP)) {
                return scheduleService.acquireExecutor(ScheduleType.MMS_HTTP);
            }
            if (scheduleType.equals(H5ThreadType.MMS_DJANGO)) {
                return scheduleService.acquireExecutor(ScheduleType.MMS_DJANGO);
            }
            if (scheduleType.equals(H5ThreadType.URGENT_DISPLAY)) {
                return scheduleService.acquireExecutor(ScheduleType.URGENT_DISPLAY);
            }
        }
        return scheduleService.acquireExecutor(ScheduleType.URGENT);
    }

    public ScheduledThreadPoolExecutor getScheduledExecutor() {
        TaskScheduleService scheduleService = (TaskScheduleService) H5Utils.findServiceByInterface(TaskScheduleService.class.getName());
        if (scheduleService != null) {
            return scheduleService.acquireScheduledExecutor();
        }
        H5Log.d("H5ThreadPoolProviderImpl", "scheduleService == null");
        return null;
    }

    public void submitOrdered(String key, Runnable runnable) {
        TaskScheduleService scheduleService = (TaskScheduleService) H5Utils.findServiceByInterface(TaskScheduleService.class.getName());
        if (scheduleService == null) {
            H5ThreadPoolFactory.getSingleThreadExecutor().execute(runnable);
        } else {
            scheduleService.acquireOrderedExecutor().submit(key, runnable);
        }
    }
}
