package com.alipay.android.phone.mobilesdk.socketcraft.util;

import com.alipay.android.phone.mobilesdk.socketcraft.api.BasicWebSocketContext;
import com.alipay.android.phone.mobilesdk.socketcraft.api.WSContextConstant;
import com.alipay.android.phone.mobilesdk.socketcraft.api.WebSocketContext;

public class WSContextUtil {
    public static final WebSocketContext getInnerWebSocketContext(WebSocketContext webSocketContext) {
        if (webSocketContext == null) {
            throw new IllegalArgumentException("webSocketContext parameter can't null");
        }
        Object obj = webSocketContext.getAttribute(WSContextConstant.INNER_WS_CONTEXT);
        if (obj != null && (obj instanceof WebSocketContext)) {
            return (WebSocketContext) obj;
        }
        synchronized (WSContextUtil.class) {
            Object obj2 = webSocketContext.getAttribute(WSContextConstant.INNER_WS_CONTEXT);
            if (obj2 == null || !(obj2 instanceof WebSocketContext)) {
                BasicWebSocketContext basicWebSocketContext = new BasicWebSocketContext();
                webSocketContext.setAttribute(WSContextConstant.INNER_WS_CONTEXT, basicWebSocketContext);
                return basicWebSocketContext;
            }
            WebSocketContext webSocketContext2 = (WebSocketContext) obj2;
            return webSocketContext2;
        }
    }
}
