package com.alipay.multimedia.js.utils;

import android.content.Context;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;
import java.util.HashMap;
import java.util.Map;

public class Utils {
    private static Map a = new HashMap();

    public Utils() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static Context getContext() {
        return LauncherApplicationAgent.getInstance().getApplicationContext();
    }

    public static Object getService(Class clazz) {
        Object service = a.get(clazz);
        if (service == null) {
            service = LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(clazz.getName());
            if (service != null) {
                a.put(clazz, service);
            }
        }
        return service;
    }

    public static boolean execute(Runnable runnable) {
        TaskScheduleService service = (TaskScheduleService) getService(TaskScheduleService.class);
        if (service == null) {
            return false;
        }
        service.acquireExecutor(ScheduleType.IO).execute(runnable);
        return true;
    }
}
