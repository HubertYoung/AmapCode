package com.mpaas.nebula.provider;

import android.net.Uri;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.mpaas.nebula.adapter.api.H5APServiceCallbackProvider;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class H5APServiceCallbackProviderImpl implements H5APServiceCallbackProvider {
    private Map<String, H5BridgeContext> a = new ConcurrentHashMap();

    public void registerCallback(String callbackId, H5BridgeContext bridgeContext) {
        this.a.put(callbackId, bridgeContext);
    }

    public void handleCallback(Uri uri) {
        String callbackId = uri.getQueryParameter("amapCallbackId");
        H5BridgeContext bridgeContext = this.a.get(callbackId);
        if (bridgeContext != null) {
            JSONObject result = new JSONObject();
            for (String name : uri.getQueryParameterNames()) {
                if (!TextUtils.equals(name, "amapCallbackId")) {
                    result.put(name, (Object) uri.getQueryParameter(name));
                }
            }
            bridgeContext.sendBridgeResult(result);
        }
        this.a.remove(callbackId);
    }
}
