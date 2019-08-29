package com.alipay.android.phone.mobilesdk.socketcraft.integrated.ssl;

import com.alipay.android.phone.mobilesdk.socketcraft.platform.ssl.DefaultSSLExtensions;
import com.alipay.mobile.common.transport.utils.SSLSocketUtil;
import javax.net.ssl.SSLSocket;

public class MPaaSSSLExtensions extends DefaultSSLExtensions {
    public void enableTlsExtensions(SSLSocket socket, String uriHost) {
        SSLSocketUtil.enableTlsExtensions(socket, uriHost);
    }
}
