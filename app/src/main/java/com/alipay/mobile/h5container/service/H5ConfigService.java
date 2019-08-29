package com.alipay.mobile.h5container.service;

import com.alipay.mobile.framework.service.ext.ExternalService;
import com.alipay.mobile.nebula.providermanager.H5ProviderConfig;
import java.util.Map;

public abstract class H5ConfigService extends ExternalService {
    public abstract void addExternalPlugins();

    public abstract Map<String, H5ProviderConfig> getProviderInfoMap();
}
