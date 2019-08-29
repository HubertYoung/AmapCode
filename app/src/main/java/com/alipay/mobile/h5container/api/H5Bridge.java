package com.alipay.mobile.h5container.api;

import com.alibaba.fastjson.JSONObject;

public interface H5Bridge {
    void monitorBridgeLog(String str, JSONObject jSONObject, String str2);

    void sendDataWarpToWeb(String str, JSONObject jSONObject, H5CallBack h5CallBack);

    void sendToNative(H5Event h5Event);

    void sendToNative(H5Event h5Event, H5BridgeContext h5BridgeContext);

    void sendToWeb(H5Event h5Event);

    @Deprecated
    void sendToWeb(String str, JSONObject jSONObject, H5CallBack h5CallBack);
}
