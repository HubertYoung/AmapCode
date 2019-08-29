package com.alipay.android.phone.mobilesdk.socketcraft.platform.ssl;

import javax.net.ssl.SSLSocket;

public interface SSLExtensions {
    void enableTlsExtensions(SSLSocket sSLSocket, String str);
}
