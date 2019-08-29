package com.alipay.android.phone.mobilesdk.socketcraft.handshake;

public interface ServerHandshake extends Handshakedata {
    short getHttpStatus();

    String getHttpStatusMessage();
}
