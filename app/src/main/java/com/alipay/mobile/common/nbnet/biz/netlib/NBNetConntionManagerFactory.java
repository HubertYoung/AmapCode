package com.alipay.mobile.common.nbnet.biz.netlib;

import com.alipay.mobile.common.nbnet.biz.constants.NBNetConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;

public class NBNetConntionManagerFactory {
    public static final NBNetConntionManager a() {
        if (TransportConfigureManager.getInstance().equalsString(NBNetConfigureItem.COMPOSITE_CONNECTION_SWITCH, "T")) {
            return NBNetCompositeConntionManager.a();
        }
        return NBNetDefaultConntionManager.a();
    }
}
