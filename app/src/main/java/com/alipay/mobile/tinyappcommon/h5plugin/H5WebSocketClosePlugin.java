package com.alipay.mobile.tinyappcommon.h5plugin;

import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.ws.a;
import com.alipay.mobile.tinyappcommon.ws.b;
import com.alipay.mobile.tinyappcommon.ws.d;
import com.alipay.mobile.tinyappcommon.ws.e;

public class H5WebSocketClosePlugin extends H5SimplePlugin {
    public static final String CLOSE_SOCKET = "closeSocket";
    private static final String TAG = "WS_H5WebSocketClosePlugin";

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(CLOSE_SOCKET);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        if (!TextUtils.equals(CLOSE_SOCKET, event.getAction())) {
            return false;
        }
        try {
            startCloseSocket(event, bridgeContext);
        } catch (Throwable e) {
            H5Log.e(TAG, "closeSocket unknow error. ", e);
        }
        return true;
    }

    private void startCloseSocket(H5Event event, H5BridgeContext bridgeContext) {
        closeSocket(b.a(event), event, bridgeContext);
    }

    public void closeSocket(String appId, H5Event event, H5BridgeContext bridgeContext) {
        e oldWebSocketSession;
        if (TextUtils.isEmpty(appId)) {
            H5Log.d(TAG, "closeSocket...appId is null");
            return;
        }
        H5Log.d(TAG, String.format("enter closeSocket, appId: %s", new Object[]{appId}));
        String socketTaskID = b.b(event);
        if (!b.c(event) || TextUtils.isEmpty(socketTaskID)) {
            oldWebSocketSession = a.a().a(appId);
        } else {
            oldWebSocketSession = d.a().a(appId, socketTaskID);
            d.a().a(appId, socketTaskID, false);
        }
        if (oldWebSocketSession == null) {
            H5Log.d(TAG, "closeSocket error , not exist WebsocketSession");
            b.b(bridgeContext, "No websocket connection is established");
        } else if (oldWebSocketSession.a == null) {
            H5Log.d(TAG, "closeSocket ok , no websocket connection is established");
            b.b(bridgeContext, "No websocket connection is established");
        } else {
            oldWebSocketSession.a.close(H5Utils.getInt(event.getParam(), (String) "code", 1000), H5Utils.getString(event.getParam(), (String) "reason", (String) ""));
            b.b(bridgeContext, "");
        }
    }
}
