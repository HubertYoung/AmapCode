package com.alipay.mobile.worker;

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

public class H5WorkerBridgeContext extends H5BaseBridgeContext {
    private String a;
    private H5ServiceWorkerHook4Bridge b;
    private H5Bridge c;
    private H5Page d;
    private long e = System.currentTimeMillis();

    public H5WorkerBridgeContext(H5ServiceWorkerHook4Bridge serviceWorkerHook4Bridge, String id, String action, H5Bridge h5Bridge, H5Page h5Page) {
        this.b = serviceWorkerHook4Bridge;
        this.a = action;
        this.id = id;
        this.c = h5Bridge;
        this.d = h5Page;
    }

    public boolean sendBack(JSONObject param, boolean keep) {
        if (TextUtils.isEmpty(this.id) || "-1".equals(this.id)) {
            H5Log.w("H5WorkerBridgeContext", "client id not specified " + this.a);
            return false;
        } else if (this.id.startsWith("native_")) {
            H5Log.w("H5WorkerBridgeContext", "ignore native fired event " + this.a);
            return false;
        } else {
            if (!(this.d == null || this.d.getPageData() == null || !H5Utils.enableJsApiPerformance())) {
                H5JsCallData jsCallData = this.d.getPageData().getJsapiInfo(this.id);
                if (jsCallData != null) {
                    jsCallData.setElapse(System.currentTimeMillis() - this.e);
                }
            }
            if (this.b != null) {
                this.b.onReceiveJsapiResult(param);
                return true;
            }
            H5Log.e((String) "H5WorkerBridgeContext", (String) "[FATAL ERROR] in sendBack() bridge is null");
            return false;
        }
    }

    public void sendToWeb(String action, JSONObject param, H5CallBack callback) {
        if (this.c != null) {
            this.c.sendToWeb(action, param, callback);
        }
    }
}
