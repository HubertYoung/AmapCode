package com.alipay.mobile.nebulacore.config;

import com.alipay.mobile.h5container.api.H5Plugin;
import com.alipay.mobile.h5container.api.H5PluginManager;
import com.alipay.mobile.nebula.config.H5PluginConfig;
import com.alipay.mobile.nebula.util.H5Log;
import java.util.LinkedList;
import java.util.List;

public class H5PluginConfigManager {
    public static final String TAG = "H5PluginConfigManager";
    private static H5PluginConfigManager a;
    private List<H5PluginConfig> b = new LinkedList();

    private H5PluginConfigManager() {
    }

    public static synchronized H5PluginConfigManager getInstance() {
        H5PluginConfigManager h5PluginConfigManager;
        synchronized (H5PluginConfigManager.class) {
            try {
                if (a == null) {
                    a = new H5PluginConfigManager();
                }
                h5PluginConfigManager = a;
            }
        }
        return h5PluginConfigManager;
    }

    public synchronized void addConfig(H5PluginConfig config) {
        if (config != null) {
            if (!config.configInvalid()) {
                H5Log.debug(TAG, "addConfig " + config.bundleName + "/" + config.className + "/" + config.eventList.toString());
                this.b.add(config);
            }
        }
    }

    public synchronized void addH5PluginConfigList(List<H5PluginConfig> config) {
        if (config != null) {
            if (!config.isEmpty()) {
                this.b.addAll(config);
            }
        }
    }

    public synchronized H5Plugin createPlugin(String scope, H5PluginManager pluginManager) {
        H5PluginProxy h5PluginProxy = null;
        synchronized (this) {
            try {
                if (!(this.b == null || this.b.isEmpty() || pluginManager == null)) {
                    long time = System.currentTimeMillis();
                    List list = new LinkedList();
                    for (H5PluginConfig info : this.b) {
                        if (scope.equals(info.scope)) {
                            list.add(info);
                        }
                    }
                    if (!list.isEmpty()) {
                        h5PluginProxy = new H5PluginProxy(list, pluginManager);
                        H5Log.d(TAG, "createPlugin " + scope + " elapse " + (System.currentTimeMillis() - time));
                    }
                }
            }
        }
        return h5PluginProxy;
    }
}
