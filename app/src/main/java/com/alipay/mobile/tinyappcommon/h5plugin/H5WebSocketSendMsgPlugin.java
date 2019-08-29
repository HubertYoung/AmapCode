package com.alipay.mobile.tinyappcommon.h5plugin;

import android.text.TextUtils;
import android.util.Base64;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.tinyappcommon.ws.WSResultEnum;
import com.alipay.mobile.tinyappcommon.ws.a;
import com.alipay.mobile.tinyappcommon.ws.b;
import com.alipay.mobile.tinyappcommon.ws.d;
import com.alipay.mobile.tinyappcommon.ws.e;
import java.nio.ByteBuffer;

public class H5WebSocketSendMsgPlugin extends H5SimplePlugin {
    public static final String SEND_SOCKET_MESSAGE = "sendSocketMessage";
    private static final String TAG = "WS_H5WebSocketSendMsgPlugin";

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(SEND_SOCKET_MESSAGE);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        if (!TextUtils.equals(SEND_SOCKET_MESSAGE, event.getAction())) {
            return false;
        }
        try {
            startSendSocketMessage(event, bridgeContext);
        } catch (Throwable e) {
            H5Log.e(TAG, "sendSocketMessage unknow error. ", e);
        }
        return true;
    }

    private void startSendSocketMessage(H5Event event, H5BridgeContext bridgeContext) {
        sendSocketMessage(b.a(event), event, bridgeContext);
    }

    public void sendSocketMessage(String appId, H5Event event, H5BridgeContext bridgeContext) {
        e oldWebSocketSession;
        H5Log.d(TAG, String.format("enter sendSocketMessage, appId: %s", new Object[]{appId}));
        String socketTaskID = b.b(event);
        if (!b.c(event) || TextUtils.isEmpty(socketTaskID)) {
            oldWebSocketSession = a.a().a(appId);
        } else {
            oldWebSocketSession = d.a().a(appId, socketTaskID);
        }
        if (oldWebSocketSession == null) {
            H5Log.d(TAG, "sendSocketMessage error , not exist WebsocketSession");
            b.a(bridgeContext, WSResultEnum.CANNOT_SEND_UNTIL_CONNECTION_IS_OPEN);
        } else if (!oldWebSocketSession.b()) {
            H5Log.d(TAG, "sendSocketMessage error , no websocket connection is established");
            b.a(bridgeContext, WSResultEnum.CANNOT_SEND_UNTIL_CONNECTION_IS_OPEN);
        } else {
            JSONObject param = event.getParam();
            String data = param.getString("data");
            if (data == null) {
                H5Log.e((String) TAG, (String) "Cannot send 'null' data to a WebSocket");
                b.a(bridgeContext, (String) "Cannot send 'null' data to a WebSocket");
                return;
            }
            try {
                Boolean isBuffer = param.getBoolean("isBuffer");
                if (isBuffer == null || !isBuffer.booleanValue()) {
                    oldWebSocketSession.a.send(data);
                } else {
                    oldWebSocketSession.a.send(ByteBuffer.wrap(Base64.decode(data.getBytes("utf-8"), 2)));
                }
                b.b(bridgeContext, "");
            } catch (Throwable e) {
                H5Log.e(TAG, String.format("sendSocketMessage error : %s", new Object[]{e.toString()}), e);
                b.a(bridgeContext, "exception: " + e.getMessage());
            }
        }
    }
}
