package com.alipay.mobile.nebulacore.embedview;

import com.alipay.mobile.nebula.newembedview.H5NewEmbedViewConfig;
import com.alipay.mobile.nebula.util.H5Log;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class H5NewEmbedViewConfigManager {
    public static final String TAG = "H5NewEmbedViewConfigManager";
    private static volatile H5NewEmbedViewConfigManager a;
    private Map<String, H5NewEmbedViewConfig> b = new ConcurrentHashMap();

    private H5NewEmbedViewConfigManager() {
    }

    public static H5NewEmbedViewConfigManager getInstance() {
        if (a == null) {
            synchronized (H5NewEmbedViewConfigManager.class) {
                try {
                    if (a == null) {
                        a = new H5NewEmbedViewConfigManager();
                    }
                }
            }
        }
        return a;
    }

    public synchronized void addTypeConfig(H5NewEmbedViewConfig config) {
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

    public synchronized void addTypeConfigs(List<H5NewEmbedViewConfig> configs) {
        if (configs != null) {
            if (!configs.isEmpty()) {
                for (H5NewEmbedViewConfig config : configs) {
                    addTypeConfig(config);
                }
            }
        }
    }

    public H5NewEmbedViewConfig getConfig(String type) {
        return this.b.get(type);
    }
}
