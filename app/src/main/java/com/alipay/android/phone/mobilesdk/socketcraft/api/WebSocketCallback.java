package com.alipay.android.phone.mobilesdk.socketcraft.api;

import java.nio.ByteBuffer;

public interface WebSocketCallback {
    void onSocketClose();

    void onSocketError(String str);

    void onSocketMessage(String str);

    void onSocketMessage(ByteBuffer byteBuffer);

    void onSocketOpen();
}
