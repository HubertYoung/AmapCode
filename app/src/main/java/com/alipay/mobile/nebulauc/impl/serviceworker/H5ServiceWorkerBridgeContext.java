package com.alipay.mobile.nebulauc.impl.serviceworker;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Bridge;
import com.alipay.mobile.h5container.api.H5CallBack;
import com.alipay.mobile.h5container.api.H5JsCallData;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5ServiceWorkerHook4Bridge;
import com.alipay.mobile.nebula.basebridge.H5BaseBridgeContext;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;

public class H5ServiceWorkerBridgeContext extends H5BaseBridgeContext {
    private static final String TAG = "H5ServiceWorkerBridgeContext";
    private String action;
    private H5Bridge h5Bridge;
    private H5Page h5Page;
    private H5ServiceWorkerHook4Bridge serviceWorkerHook4Bridge;
    private long startTime = System.currentTimeMillis();

    public H5ServiceWorkerBridgeContext(H5ServiceWorkerHook4Bridge serviceWorkerHook4Bridge2, String id, String action2, H5Bridge h5Bridge2, H5Page h5Page2) {
        this.serviceWorkerHook4Bridge = serviceWorkerHook4Bridge2;
        this.action = action2;
        this.id = id;
        this.h5Bridge = h5Bridge2;
        this.h5Page = h5Page2;
    }

    public boolean sendBack(JSONObject param, boolean keep) {
        if (TextUtils.isEmpty(this.id) || "-1".equals(this.id)) {
            H5Log.w(TAG, "client id not specified " + this.action);
            return false;
        } else if (this.id.startsWith("native_")) {
            H5Log.w(TAG, "ignore native fired event " + this.action);
            return false;
        } else {
            if (!(this.h5Page == null || this.h5Page.getPageData() == null || !H5Utils.enableJsApiPerformance())) {
                H5JsCallData jsCallData = this.h5Page.getPageData().getJsapiInfo(this.id);
                if (jsCallData != null) {
                    jsCallData.setElapse(System.currentTimeMillis() - this.startTime);
                }
            }
            if (this.h5Bridge != null) {
                this.h5Bridge.monitorBridgeLog(this.action, param, this.id);
            }
            if (this.serviceWorkerHook4Bridge != null) {
                this.serviceWorkerHook4Bridge.onReceiveJsapiResult(param);
                return true;
            }
            H5Log.e((String) TAG, (String) "[FATAL ERROR] in sendBack() bridge is null");
            return false;
        }
    }

    public void sendToWeb(String action2, JSONObject param, H5CallBack callback) {
        if (this.h5Bridge != null) {
            this.h5Bridge.sendToWeb(action2, param, callback);
        }
    }
}
