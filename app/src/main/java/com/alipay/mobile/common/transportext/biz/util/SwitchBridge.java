package com.alipay.mobile.common.transportext.biz.util;

import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.ext.ExternalService;
import com.mpaas.nebula.plugin.H5ServicePlugin;

public class SwitchBridge {
    public static String getSwitchFromOriginal(String key) {
        try {
            ExternalService mConfigService = LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface("com.alipay.mobile.base.config.ConfigService");
            return (String) mConfigService.getClass().getDeclaredMethod(H5ServicePlugin.GET_CONFIG, new Class[]{String.class}).invoke(mConfigService, new Object[]{key});
        } catch (Throwable ex) {
            LogCatUtil.error((String) "SwitchBridge", ex);
            return null;
        }
    }
}
