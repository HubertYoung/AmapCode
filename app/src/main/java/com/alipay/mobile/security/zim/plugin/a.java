package com.alipay.mobile.security.zim.plugin;

import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.security.zim.api.ZIMCallback;
import com.alipay.mobile.security.zim.api.ZIMResponse;

/* compiled from: ZIMH5Plugin */
final class a implements ZIMCallback {
    final /* synthetic */ H5BridgeContext a;
    final /* synthetic */ ZIMH5Plugin b;

    a(ZIMH5Plugin zIMH5Plugin, H5BridgeContext h5BridgeContext) {
        this.b = zIMH5Plugin;
        this.a = h5BridgeContext;
    }

    public final boolean response(ZIMResponse zIMResponse) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put((String) "code", (Object) zIMResponse.code);
        if (zIMResponse.extInfo != null) {
            jSONObject.put((String) "verifyCode", (Object) zIMResponse.extInfo.get("verifyCode"));
        }
        jSONObject.put((String) "reason", (Object) zIMResponse.reason);
        jSONObject.put((String) "bizData", (Object) zIMResponse.bizData);
        new StringBuilder("result").append(jSONObject.toString());
        this.a.sendBridgeResult(jSONObject);
        return true;
    }
}
