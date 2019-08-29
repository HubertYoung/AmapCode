package com.alipay.mobile.tinyappcustom.h5plugin;

import android.app.Activity;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.android.verify.sdk.ServiceFactory;
import com.alipay.mobile.android.verify.sdk.interfaces.ICallback;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import java.util.Map;

public class CertifyServicePlugin extends H5SimplePlugin {
    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction("startAPVerify");
    }

    public boolean handleEvent(H5Event event, final H5BridgeContext bridgeContext) {
        if ("startAPVerify".equals(event.getAction())) {
            Activity activity = event.getActivity();
            if (activity != null) {
                final JSONObject result = new JSONObject();
                ServiceFactory.build().startService(activity, event.getParam(), (ICallback) new ICallback() {
                    public void onResponse(Map<String, String> map) {
                        if (map != null) {
                            for (String key : map.keySet()) {
                                result.put(key, (Object) map.get(key));
                            }
                        }
                        bridgeContext.sendBridgeResult(result);
                    }
                });
            }
        }
        return true;
    }
}
