package com.alipay.mobile.beehive.util;

import android.os.Handler;
import android.os.Looper;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;
import java.util.concurrent.ThreadPoolExecutor;

public class MultiThreadUtil {
    public static void runOnBackgroundThread(Runnable r) {
        ThreadPoolExecutor e = ((TaskScheduleService) ServiceUtil.getServiceByInterface(TaskScheduleService.class)).acquireExecutor(ScheduleType.IO);
        if (e != null) {
            e.execute(r);
        } else {
            LoggerFactory.getTraceLogger().warn((String) SpmUtils.SPM_BIZTYPE, (String) "获取线程池失败");
        }
    }

    public static void runOnUiThread(Runnable r) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            r.run();
        } else {
            new Handler(Looper.getMainLooper()).post(r);
        }
    }
}
