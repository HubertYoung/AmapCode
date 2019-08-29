package com.alipay.mobile.common.transportext.api;

import android.content.Context;
import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.ext.ExtTransportClient;

public interface ExtTransportManager {
    ExtTransportClient getExtTransportClient(Context context, TransportContext transportContext);

    void init(Context context);

    boolean isInited();
}
