package com.alipay.mobile.tinyappcommon.ws;

import android.text.TextUtils;
import android.util.Base64;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilesdk.socketcraft.api.WebSocketCallbackAdapter;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.nebula.util.H5Log;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/* compiled from: H5WebSocketCallback */
public final class c extends WebSocketCallbackAdapter {
    private H5BridgeContext a;
    private boolean b;
    private String c;

    public c(H5BridgeContext h5BridgeContext, String socketTaskID, boolean isMultipleSocket) {
        this.a = h5BridgeContext;
        this.b = isMultipleSocket;
        this.c = socketTaskID;
    }

    public final void onSocketOpen() {
        H5Log.d("WS_H5WebSocketCallback", "enter onSocketOpen. ");
        if (!this.b || TextUtils.isEmpty(this.c)) {
            this.a.sendToWeb("socketOpen", null, null);
            return;
        }
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        data.put((String) "socketTaskID", (Object) this.c);
        result.put((String) "data", (Object) data);
        this.a.sendToWeb("onSocketTaskOpen", result, null);
    }

    public final void onSocketClose() {
        H5Log.d("WS_H5WebSocketCallback", "enter onSocketClose. ");
        if (!this.b || TextUtils.isEmpty(this.c)) {
            this.a.sendToWeb("socketClose", null, null);
            return;
        }
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        data.put((String) "socketTaskID", (Object) this.c);
        result.put((String) "data", (Object) data);
        this.a.sendToWeb("onSocketTaskClose", result, null);
    }

    public final void onSocketMessage(ByteBuffer byteBuffer) {
        JSONObject hashMap = new JSONObject();
        try {
            hashMap.put((String) "data", (Object) new String(Base64.encode(byteBuffer.array(), 2), "utf-8"));
            hashMap.put((String) "isBuffer", (Object) Boolean.TRUE);
            JSONObject dataWarp = new JSONObject();
            dataWarp.put((String) "data", (Object) hashMap);
            if (!this.b || TextUtils.isEmpty(this.c)) {
                this.a.sendToWeb("socketMessage", dataWarp, null);
                return;
            }
            hashMap.put((String) "socketTaskID", (Object) this.c);
            this.a.sendToWeb("onSocketTaskMessage", dataWarp, null);
        } catch (UnsupportedEncodingException e) {
            H5Log.e((String) "WS_H5WebSocketCallback", String.format("create string by buffer error. exception : %s", new Object[]{e.toString()}));
        }
    }

    public final void onSocketMessage(String s) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put((String) "data", (Object) s);
        jsonObject.put((String) "isBuffer", (Object) Boolean.FALSE);
        JSONObject dataWarp = new JSONObject();
        dataWarp.put((String) "data", (Object) jsonObject);
        if (!this.b || TextUtils.isEmpty(this.c)) {
            this.a.sendToWeb("socketMessage", dataWarp, null);
            return;
        }
        jsonObject.put((String) "socketTaskID", (Object) this.c);
        this.a.sendToWeb("onSocketTaskMessage", dataWarp, null);
    }

    public final void onSocketError(String s) {
        H5Log.d("WS_H5WebSocketCallback", "enter onSocketError. " + s);
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        WSResultEnum resultEnumByWsMsg = WSResultEnum.getResultEnumByWsMsg(s);
        data.put((String) "error", (Object) Integer.valueOf(resultEnumByWsMsg.getErrCode()));
        if (resultEnumByWsMsg == WSResultEnum.UNKNOW_ERROR) {
            data.put((String) "errorMessage", (Object) s);
        }
        if (!this.b || TextUtils.isEmpty(this.c)) {
            result.put((String) "data", (Object) data);
            this.a.sendToWeb("socketError", result, null);
            return;
        }
        data.put((String) "socketTaskID", (Object) this.c);
        result.put((String) "data", (Object) data);
        this.a.sendToWeb("onSocketTaskError", result, null);
    }
}
