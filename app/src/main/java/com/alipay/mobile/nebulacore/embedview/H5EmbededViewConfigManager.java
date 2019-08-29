package com.alipay.mobile.nebulacore.embedview;

import com.alipay.mobile.nebula.config.H5EmbedViewConfig;
import com.alipay.mobile.nebula.util.H5Log;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class H5EmbededViewConfigManager {
    public static final String TAG = "H5EmbededViewConfigManager";
    private static H5EmbededViewConfigManager a;
    private Map<String, H5EmbedViewConfig> b = new ConcurrentHashMap();

    private H5EmbededViewConfigManager() {
    }

    public static H5EmbededViewConfigManager getInstance() {
        if (a == null) {
            synchronized (H5EmbededViewConfigManager.class) {
                try {
                    if (a == null) {
                        a = new H5EmbededViewConfigManager();
                    }
                }
            }
        }
        return a;
    }

    public synchronized void addTypeConfig(H5EmbedViewConfig config) {
        if (config != null) {
            String type = config.getType();
            String bundleName = config.getBundleName();
            H5Log.debug(TAG, "addType " + bundleName + "/" + config.getClassName() + "/" + type);
            if (this.b.containsKey(type)) {
                H5Log.d(TAG, "addType repeated type " + type);
            } else {
                this.b.put(type, config);
            }
        }
    }

    public synchronized void addTypeConfigs(List<H5EmbedViewConfig> configs) {
        if (configs != null) {
            if (!configs.isEmpty()) {
                for (H5EmbedViewConfig config : configs) {
                    addTypeConfig(config);
                }
            }
        }
    }

    public H5EmbedViewConfig getConfig(String type) {
        return this.b.get(type);
    }
}
