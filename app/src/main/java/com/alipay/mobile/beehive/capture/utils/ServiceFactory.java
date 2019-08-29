package com.alipay.mobile.beehive.capture.utils;

import com.alipay.mobile.framework.LauncherApplicationAgent;

public class ServiceFactory {
    private static final String TAG = "ServiceFactory";

    public static <T> T getAliService(Class<T> clazz) {
        Object ret = LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(clazz.getName());
        if (ret == null) {
            Logger.warn(TAG, "Get alipay service return null!");
        }
        return ret;
    }
}
