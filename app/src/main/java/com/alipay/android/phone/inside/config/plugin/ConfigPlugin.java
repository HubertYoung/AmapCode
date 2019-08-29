package com.alipay.android.phone.inside.config.plugin;

import com.alipay.android.phone.inside.config.plugin.service.DynamicConfigLoadService;
import com.alipay.android.phone.inside.framework.plugin.IInsidePlugin;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import java.util.HashMap;
import java.util.Map;

public class ConfigPlugin implements IInsidePlugin {
    public static final String SERVICE_DYNAMI_CCONFIG_LOAD = "CONFIG_PLUGIN_DYNAMI_CCONFIG_LOAD";
    final Map<String, IInsideService> mServices = new HashMap();

    public void onRegisted(Object obj) {
    }

    public void onUnRegisted(Object obj) {
    }

    public ConfigPlugin() {
        this.mServices.put(SERVICE_DYNAMI_CCONFIG_LOAD, new DynamicConfigLoadService());
    }

    public Map<String, IInsideService> getServiceMap() {
        return this.mServices;
    }

    public IInsideService getService(String str) {
        return this.mServices.get(str);
    }
}
