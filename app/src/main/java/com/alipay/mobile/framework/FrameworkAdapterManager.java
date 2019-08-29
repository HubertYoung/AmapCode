package com.alipay.mobile.framework;

public class FrameworkAdapterManager {
    private static volatile FrameworkAdapterManager b;
    private ConfigAdapter a;

    public interface ConfigAdapter {
        String getConfig(String str);
    }

    private FrameworkAdapterManager() {
    }

    public static FrameworkAdapterManager g() {
        if (b == null) {
            synchronized (FrameworkAdapterManager.class) {
                try {
                    FrameworkAdapterManager tmp = new FrameworkAdapterManager();
                    if (b == null) {
                        b = tmp;
                    }
                }
            }
        }
        return b;
    }

    public void setConfigAdapter(ConfigAdapter configAdapter) {
        this.a = configAdapter;
    }

    public String getConfigFromAdapter(String key) {
        if (this.a == null) {
            return null;
        }
        return this.a.getConfig(key);
    }
}
