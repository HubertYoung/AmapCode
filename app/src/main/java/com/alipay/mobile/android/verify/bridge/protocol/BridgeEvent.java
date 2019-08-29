package com.alipay.mobile.android.verify.bridge.protocol;

import com.alibaba.fastjson.JSONObject;

public class BridgeEvent {
    public String action;
    public JSONObject data;
    public String id;

    public static BridgeEvent cloneAsResponse(BridgeEvent bridgeEvent) {
        BridgeEvent bridgeEvent2 = new BridgeEvent();
        if (bridgeEvent != null) {
            bridgeEvent2.id = bridgeEvent.id;
            bridgeEvent2.action = BridgeEventTypes.JS_CALLBACK;
            bridgeEvent2.data = new JSONObject();
        }
        return bridgeEvent2;
    }

    public static JSONObject response() {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put((String) "success", (Object) Boolean.TRUE);
        return jSONObject;
    }
}
