package com.alipay.mobile.common.transportext.biz.spdy.longlink;

public interface ISpdyCallBack {
    void onConnected();

    void onConnecting();

    void onDisconnected();

    void onRecvData(byte[] bArr);
}
