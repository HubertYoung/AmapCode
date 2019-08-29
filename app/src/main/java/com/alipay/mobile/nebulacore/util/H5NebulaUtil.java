package com.alipay.mobile.nebulacore.util;

import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5ProviderManager;
import com.alipay.mobile.nebula.util.H5Utils;

public class H5NebulaUtil {
    public static String getConfigWithProcessCache(String configKey) {
        H5ProviderManager providerManager = getProviderManager();
        if (providerManager == null) {
            return null;
        }
        H5ConfigProvider provider = (H5ConfigProvider) providerManager.getProvider(H5ConfigProvider.class.getName());
        if (provider != null) {
            return provider.getConfigWithProcessCache(configKey);
        }
        return null;
    }

    public static H5Service getH5Service() {
        H5Service h5Service = (H5Service) H5Utils.findServiceByInterface(H5Service.class.getName());
        if (h5Service != null) {
            return h5Service;
        }
        return null;
    }

    public static H5ProviderManager getProviderManager() {
        H5Service h5Service = getH5Service();
        if (h5Service != null) {
            return h5Service.getProviderManager();
        }
        return null;
    }
}
