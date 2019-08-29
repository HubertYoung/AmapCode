package com.alipay.android.phone.mobilecommon.biometric;

import com.alipay.android.phone.mobilecommon.logger.a;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.config.H5PluginConfig;
import com.alipay.mobile.security.bio.utils.BioLog;
import com.alipay.mobile.security.zim.plugin.ZIMH5Plugin;

public class BioMetricValve implements Runnable {
    private static final String TAG = "BioMetricValve";

    public void run() {
        BioLog.setLogger(new a());
        H5Service h5Service = (H5Service) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(H5Service.class.getName());
        LoggerFactory.getTraceLogger().info(TAG, "h5service : " + h5Service);
        if (h5Service != null) {
            addFalconCardPlugin(TAG, h5Service);
        } else {
            LoggerFactory.getTraceLogger().warn((String) TAG, (String) "Failed to add VI and falcon H5Plugin!!!");
        }
    }

    private static void addFalconCardPlugin(String str, H5Service h5Service) {
        H5PluginConfig h5PluginConfig = new H5PluginConfig();
        h5PluginConfig.bundleName = "android-phone-zoloz-zcloud";
        h5PluginConfig.className = ZIMH5Plugin.class.getName();
        h5PluginConfig.scope = "page";
        h5PluginConfig.setEvents(ZIMH5Plugin.ZIM_IDENTIFY);
        if (h5Service != null) {
            LoggerFactory.getTraceLogger().info("falcon", str + "h5Service addPluginConfig");
            h5Service.addPluginConfig(h5PluginConfig);
        }
    }
}
