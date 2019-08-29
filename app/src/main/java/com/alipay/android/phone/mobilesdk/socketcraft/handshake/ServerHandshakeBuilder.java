package com.alipay.android.phone.mobilesdk.socketcraft.handshake;

public interface ServerHandshakeBuilder extends HandshakeBuilder, ServerHandshake {
    void setHttpStatus(short s);

    void setHttpStatusMessage(String str);
}
