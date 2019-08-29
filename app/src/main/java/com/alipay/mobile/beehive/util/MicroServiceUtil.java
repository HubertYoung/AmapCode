package com.alipay.mobile.beehive.util;

import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.service.CommonService;
import com.alipay.mobile.framework.service.MicroService;
import com.alipay.mobile.framework.service.ext.ExternalService;

public final class MicroServiceUtil {
    private static MicroApplicationContext microApplicationContext;

    private static final void init() {
        if (microApplicationContext == null) {
            microApplicationContext = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
        }
    }

    public static final <T extends MicroService> T getMicroService(Class<T> clazz) {
        init();
        if (CommonService.class.isAssignableFrom(clazz)) {
            return (MicroService) microApplicationContext.findServiceByInterface(clazz.getName());
        }
        if (ExternalService.class.isAssignableFrom(clazz)) {
            return microApplicationContext.getExtServiceByInterface(clazz.getName());
        }
        return null;
    }

    public static final <T extends CommonService> T getServiceByInterface(Class<T> clazz) {
        init();
        return (CommonService) microApplicationContext.findServiceByInterface(clazz.getName());
    }

    public static final <T extends ExternalService> T getExtServiceByInterface(Class<T> clazz) {
        init();
        return microApplicationContext.getExtServiceByInterface(clazz.getName());
    }
}
