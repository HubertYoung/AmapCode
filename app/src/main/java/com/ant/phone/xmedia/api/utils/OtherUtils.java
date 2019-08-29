package com.ant.phone.xmedia.api.utils;

import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;
import java.util.HashMap;
import java.util.Map;

public class OtherUtils {
    private static Map<Class, Object> a = new HashMap();

    public static byte[] a(String str) {
        try {
            int cnt = str.length();
            byte[] res = str.getBytes("US-ASCII");
            byte[] result = new byte[(cnt + 1)];
            for (int i = 0; i < cnt; i++) {
                result[i] = res[i];
            }
            result[cnt] = 0;
            return result;
        } catch (Throwable th) {
            return null;
        }
    }

    private static <T> T a(Class clazz) {
        Object service = a.get(clazz);
        if (service == null) {
            service = LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(clazz.getName());
            if (service != null) {
                a.put(clazz, service);
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
