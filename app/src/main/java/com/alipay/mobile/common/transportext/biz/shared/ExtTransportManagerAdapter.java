package com.alipay.mobile.common.transportext.biz.shared;

import android.content.Context;
import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.ext.ExtTransportClient;
import com.alipay.mobile.common.transportext.api.ExtTransportManager;

public class ExtTransportManagerAdapter implements ExtTransportManager {
    public void init(Context context) {
    }

    public boolean isInited() {
        return false;
    }

    public ExtTransportClient getExtTransportClient(Context context, TransportContext transportContext) {
        return null;
    }
}
