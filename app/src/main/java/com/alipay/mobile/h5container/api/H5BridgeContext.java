package com.alipay.mobile.h5container.api;

import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Event.Error;

public interface H5BridgeContext {
    public static final String INVALID_ID = "-1";

    String getId();

    String getInvokeType();

    boolean sendBridgeResult(JSONObject jSONObject);

    boolean sendBridgeResult(String str, Object obj);

    boolean sendBridgeResultWithCallbackKept(JSONObject jSONObject);

    boolean sendBridgeResultWithCallbackKept(String str, Object obj);

    void sendError(int i, String str);

    boolean sendError(H5Event h5Event, Error error);

    void sendNoRigHtToInvoke();

    void sendNoRigHtToInvoke4NewJSAPIPermission();

    void sendNotGrantPermission();

    void sendSuccess();

    void sendToWeb(String str, JSONObject jSONObject, H5CallBack h5CallBack);

    void useCancel();
}
