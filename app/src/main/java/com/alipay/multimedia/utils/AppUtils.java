package com.alipay.multimedia.utils;

import android.app.Application;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.service.ext.ExternalService;

public class AppUtils {
    public static Application getApplication() {
        return getMicroAppCtx().getApplicationContext();
    }

    public static MicroApplicationContext getMicroAppCtx() {
        return LauncherApplicationAgent.getInstance().getMicroApplicationContext();
    }

    public static <T extends ExternalService> T getService(Class<T> clazz) {
        return (ExternalService) getMicroAppCtx().findServiceByInterface(clazz.getName());
    }
}
