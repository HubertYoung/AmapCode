package com.alipay.mobile.tinyappcommon.remotedebug.a;

import android.util.Base64;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5CallBack;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Builder;
import com.alipay.mobile.nebula.basebridge.H5BaseBridgeContext;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.h5plugin.H5WebSocketClosePlugin;
import com.alipay.mobile.tinyappcommon.h5plugin.H5WebSocketConnectPlugin;
import com.alipay.mobile.tinyappcommon.h5plugin.H5WebSocketSendMsgPlugin;
import com.alipay.mobile.tinyappcommon.remotedebug.a.C0101a;
import java.util.HashMap;

/* compiled from: WebSocketDataChannel */
public class a extends com.alipay.mobile.tinyappcommon.remotedebug.a {
    /* access modifiers changed from: private */
    public static final String a = a.class.getSimpleName();
    /* access modifiers changed from: private */
    public C0101a b;
    private HashMap<String, Boolean> c = new HashMap<>();
    private H5Event d;
    private H5WebSocketConnectPlugin e = new H5WebSocketConnectPlugin();
    private H5WebSocketSendMsgPlugin f = new H5WebSocketSendMsgPlugin();
    private H5WebSocketClosePlugin g = new H5WebSocketClosePlugin();
    private H5BaseBridgeContext h;

    public final void a(C0101a listener) {
        this.b = listener;
    }

    public final void a(String url, H5Event h5Event) {
        Boolean alreadyConnect = this.c.get(url);
        if (alreadyConnect != null && alreadyConnect.booleanValue()) {
            H5Log.d(a, "connect...already connecting." + url);
        } else if (h5Event == null) {
            H5Log.d(a, "connect...h5Event is null");
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put((String) "url", (Object) url);
            jsonObject.put((String) "fromRemoteDebug", (Object) "true");
            H5Event event = new Builder().type(h5Event.getType()).id(h5Event.getId()).build();
            event.setAction(H5WebSocketConnectPlugin.CONNECT_SOCKET);
            event.setParam(jsonObject);
            this.c.put(url, Boolean.valueOf(true));
            this.d = event;
            String tmpAppId = null;
            if (h5Event.getH5page() != null) {
                tmpAppId = H5Utils.getString(h5Event.getH5page().getParams(), (String) "appId");
            }
            final String appId = tmpAppId;
            this.h = new H5BaseBridgeContext() {
                public final boolean sendBack(JSONObject param, boolean keep) {
                    return false;
                }

                public final void sendToWeb(String action, JSONObject param, H5CallBack callback) {
                    if ("socketOpen".equals(action)) {
                        if (a.this.b != null) {
                            a.this.b.onConnectSuccess(appId);
                        }
                    } else if ("socketClose".equals(action)) {
                        if (a.this.b != null) {
                            a.this.b.onConnectClosed(appId);
                        }
                    } else if ("socketError".equals(action)) {
                        if (a.this.b != null && param != null) {
                            Integer error = param.getInteger("error");
                            a.this.b.onConnectError(appId, error != null ? error.intValue() : 99999, param.getString("errorMessage"));
                        }
                    } else if ("socketMessage".equals(action) && a.this.b != null && param != null) {
                        JSONObject data = param.getJSONObject("data");
                        if (data != null) {
                            try {
                                String recvedMessage = data.getString("data");
                                Boolean isBuffer = data.getBoolean("isBuffer");
                                if (isBuffer == null || !isBuffer.booleanValue()) {
                                    a.this.b.recv(recvedMessage);
                                } else {
                                    a.this.b.recv(Base64.decode(recvedMessage, 2));
                                }
                            } catch (Throwable e) {
                                H5Log.e(a.a, "socketMessage...e=" + e);
                            }
                        }
                    }
                }
            };
            this.e.startConnectSocket("fromRemoteDebug", event, this.h);
        }
    }

    public final boolean a(String message) {
        if (this.d == null) {
            H5Log.d(a, "send...h5Event is null");
            return false;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put((String) "data", (Object) message);
        jsonObject.put((String) "fromRemoteDebug", (Object) "true");
        H5Event event = new Builder(this.d).build();
        event.setAction(H5WebSocketSendMsgPlugin.SEND_SOCKET_MESSAGE);
        event.setParam(jsonObject);
        H5Log.d(a, "send..." + message);
        this.f.sendSocketMessage("fromRemoteDebug", event, this.h);
        return true;
    }

    public final void b(String reason) {
        if (this.d == null) {
            H5Log.d(a, "close...h5Event is null");
            return;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put((String) "code", (Object) Integer.valueOf(1000));
        jsonObject.put((String) "reason", (Object) reason);
        jsonObject.put((String) "fromRemoteDebug", (Object) "true");
        H5Event event = new Builder(this.d).build();
        event.setAction(H5WebSocketClosePlugin.CLOSE_SOCKET);
        event.setParam(jsonObject);
        this.g.closeSocket("fromRemoteDebug", event, this.h);
        this.d = null;
    }
}
