package com.alipay.mobile.common.transportext.biz.quic;

public class LibQuicNative {
    public native void ConnectAndSendRequest(byte[] bArr, int i, int i2, byte[] bArr2, int i3);

    public native void Init(LibQuicCallback libQuicCallback);

    public native void OnNetworkStatusChanged(int i, String str, String str2);
}
