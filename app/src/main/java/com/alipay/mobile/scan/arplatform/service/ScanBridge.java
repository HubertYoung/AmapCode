package com.alipay.mobile.scan.arplatform.service;

import com.alipay.mobile.bqcscanservice.plugin.PluginCallback;
import com.alipay.mobile.bqcscanservice.plugin.PluginType;
import com.alipay.mobile.framework.service.ext.ExternalService;

public abstract class ScanBridge extends ExternalService {
    public abstract void removeBridgeBuilder(BridgeBuilder bridgeBuilder);

    public abstract void setBridgeBuilder(BridgeBuilder bridgeBuilder);

    public abstract void useBridge(PluginType pluginType, PluginCallback pluginCallback, Object... objArr);
}
