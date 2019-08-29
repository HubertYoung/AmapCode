package com.alipay.mobile.scan.arplatform.service;

import com.alipay.mobile.bqcscanservice.plugin.PluginCallback;
import com.alipay.mobile.bqcscanservice.plugin.PluginType;

public interface BridgeBuilder {
    void useBridge(PluginType pluginType, PluginCallback pluginCallback, Object... objArr);
}
