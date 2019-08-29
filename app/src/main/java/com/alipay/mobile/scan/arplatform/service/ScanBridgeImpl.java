package com.alipay.mobile.scan.arplatform.service;

import android.os.Bundle;
import com.alipay.mobile.bqcscanservice.Logger;
import com.alipay.mobile.bqcscanservice.plugin.PluginCallback;
import com.alipay.mobile.bqcscanservice.plugin.PluginType;

public class ScanBridgeImpl extends ScanBridge {
    public static final String TAG = "ScanBridge";
    private BridgeBuilder a;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
    }

    public void useBridge(PluginType pluginType, PluginCallback pluginCallback, Object... parameters) {
        Logger.d(TAG, "useBridge: " + pluginType + ", ");
        if (this.a != null) {
            this.a.useBridge(pluginType, pluginCallback, parameters);
        }
    }

    public void setBridgeBuilder(BridgeBuilder bridgeBuilder) {
        this.a = bridgeBuilder;
    }

    public void removeBridgeBuilder(BridgeBuilder builder) {
        Logger.d(TAG, "Original-Builder:" + this.a + ", Outer-Builder: " + builder);
        if (this.a == builder) {
            this.a = null;
        }
    }
}
