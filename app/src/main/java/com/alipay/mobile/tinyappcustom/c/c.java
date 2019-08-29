package com.alipay.mobile.tinyappcustom.c;

import android.content.Context;
import android.util.Log;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;
import java.util.HashMap;
import java.util.Map;

/* compiled from: Utils */
public final class c {
    private static Map<Class, Object> a = new HashMap();

    public static Context a() {
        return LauncherApplicationAgent.getInstance().getApplicationContext();
    }

    public static <T> T a(Class clazz) {
        Object service = a.get(clazz);
        if (service == null) {
            try {
                service = LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(clazz.getName());
                if (service != null) {
                    a.put(clazz, service);
                }
            } catch (Exception e) {
                Log.d("Utils", "getService exp: " + e.toString());
            }
        }
        return service;
    }

    public static boolean a(Runnable runnable) {
        TaskScheduleService service = (TaskScheduleService) a(TaskScheduleService.class);
        if (service == null) {
            return false;
        }
        service.acquireExecutor(ScheduleType.IO).execute(runnable);
        return true;
    }
}
