package com.alipay.mobile.beehive.util;

import com.alipay.mobile.framework.LauncherApplicationAgent;

public class ServiceUtil {
    public static <T> T getServiceByInterface(Class clazz) {
        return LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(clazz.getName());
    }
}
