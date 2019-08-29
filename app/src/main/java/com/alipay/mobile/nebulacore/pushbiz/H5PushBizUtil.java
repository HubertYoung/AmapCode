package com.alipay.mobile.nebulacore.pushbiz;

import com.alipay.mobile.h5container.api.H5BridgeContext;

public class H5PushBizUtil {
    private static H5BridgeContext a;

    public static void setH5BridgeContext(H5BridgeContext bridgeContext) {
        a = bridgeContext;
    }

    public static H5BridgeContext getH5BridgeContext() {
        return a;
    }
}
