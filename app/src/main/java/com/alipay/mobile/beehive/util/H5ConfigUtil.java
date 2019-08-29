package com.alipay.mobile.beehive.util;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.config.H5PluginConfig;

public class H5ConfigUtil {
    public static void addConfig(String bundleName, String className, String scope, String Events) {
        try {
            H5PluginConfig config = new H5PluginConfig();
            config.bundleName = bundleName;
            config.className = className;
            config.scope = scope;
            config.lazyInit = false;
            config.setEvents(Events);
            H5Service h5Service = (H5Service) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(H5Service.class.getName());
            if (h5Service != null) {
                h5Service.addPluginConfig(config);
            }
            LoggerFactory.getTraceLogger().debug("RegisterPlugin", "finish");
        } catch (Exception globalException) {
            LoggerFactory.getTraceLogger().error((String) "RegisterPlugin", (Throwable) globalException);
        }
    }
}
