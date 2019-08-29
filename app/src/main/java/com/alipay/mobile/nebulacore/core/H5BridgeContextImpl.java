package com.alipay.mobile.nebulacore.core;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Bridge;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Builder;
import com.alipay.mobile.nebula.basebridge.H5BaseBridgeContext;
import com.alipay.mobile.nebula.util.H5Log;

public class H5BridgeContextImpl extends H5BaseBridgeContext {
    public static final String FORBIDDEN = "forbidden!";
    public static final String INVALID_PARAM = "invalid parameter!";
    public static final String NONE_ERROR = "none error!";
    public static final String NOT_FOUND = "not implemented!";
    public static final String UNKNOWN_ERROR = "unknown error!";
    private String a;

    public H5BridgeContextImpl(H5Bridge bridge, String id, String action) {
        this.bridge = bridge;
        this.a = action;
        if (TextUtils.isEmpty(id)) {
            this.id = "-1";
        } else {
            this.id = id;
        }
    }

    public boolean sendBack(JSONObject param, boolean keep) {
        Builder builder = new Builder();
        if (TextUtils.isEmpty(this.id) || "-1".equals(this.id)) {
            H5Log.w("H5BridgeContextImpl", "client id not specified " + this.a);
            return false;
        } else if (this.id.startsWith("native_")) {
            H5Log.w("H5BridgeContextImpl", "ignore native fired event " + this.a);
            return false;
        } else {
            builder.id(this.id).action(this.a).keepCallback(keep).param(param).type("callback");
            H5Event event = builder.build();
            if (this.bridge != null) {
                this.bridge.sendToWeb(event);
                return true;
            }
            H5Log.e((String) "H5BridgeContextImpl", (String) "[FATAL ERROR] in sendBack() bridge is null");
            return false;
        }
    }
}
