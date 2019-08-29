package com.mpaas.nebula.adapter.api;

import android.net.Uri;
import com.alipay.mobile.h5container.api.H5BridgeContext;

public interface H5APServiceCallbackProvider {
    void handleCallback(Uri uri);

    void registerCallback(String str, H5BridgeContext h5BridgeContext);
}
