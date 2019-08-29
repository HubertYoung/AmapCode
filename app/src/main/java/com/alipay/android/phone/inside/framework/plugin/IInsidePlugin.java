package com.alipay.android.phone.inside.framework.plugin;

import com.alipay.android.phone.inside.framework.service.IInsideService;
import java.util.Map;

public interface IInsidePlugin {
    Map<String, IInsideService> getServiceMap();
}
