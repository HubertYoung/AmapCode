package com.alipay.android.phone.mobilesdk.socketcraft.platform.ssl;

import com.alipay.android.phone.mobilesdk.socketcraft.platform.PlatformUtil;
import com.alipay.android.phone.mobilesdk.socketcraft.platform.logcat.SCLogCatUtil;

public class SSLExtensionsFactory {
    private static final String MPAAS_SSL_EXTENSIONS_IMPL = "com.alipay.android.phone.mobilesdk.socketcraft.integrated.ssl.MPaaSSSLExtensions";
    private static final String TAG = "SSLExtensionsFactory";
    private static SSLExtensions sslExtensions;

    public static final SSLExtensions getInstance() {
        if (sslExtensions != null) {
            return sslExtensions;
        }
        synchronized (SSLExtensions.class) {
            if (PlatformUtil.isAndroidMPaaSPlatform()) {
                try {
                    sslExtensions = (SSLExtensions) Class.forName(MPAAS_SSL_EXTENSIONS_IMPL).newInstance();
                    SCLogCatUtil.info(TAG, String.format("New instance ok, class: %s", new Object[]{MPAAS_SSL_EXTENSIONS_IMPL}));
                } catch (Throwable e) {
                    SCLogCatUtil.warn(TAG, String.format("New instance error, class: %s", new Object[]{MPAAS_SSL_EXTENSIONS_IMPL}), e);
                }
            }
            if (sslExtensions == null) {
                sslExtensions = new DefaultSSLExtensions();
            }
        }
        return sslExtensions;
    }
}
