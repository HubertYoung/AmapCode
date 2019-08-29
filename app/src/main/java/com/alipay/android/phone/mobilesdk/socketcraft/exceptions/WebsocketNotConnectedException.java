package com.alipay.android.phone.mobilesdk.socketcraft.exceptions;

import com.alipay.android.phone.mobilesdk.socketcraft.util.WsMessageConstants;

public class WebsocketNotConnectedException extends RuntimeException {
    public WebsocketNotConnectedException() {
        super(WsMessageConstants.MSG_WEBSOCKET_NOT_CONNECTED);
    }
}
