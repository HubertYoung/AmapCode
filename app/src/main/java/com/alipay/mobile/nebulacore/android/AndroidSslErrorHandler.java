package com.alipay.mobile.nebulacore.android;

import android.webkit.SslErrorHandler;
import com.alipay.mobile.nebula.webview.APSslErrorHandler;

class AndroidSslErrorHandler implements APSslErrorHandler {
    private SslErrorHandler a;

    AndroidSslErrorHandler(SslErrorHandler sslErrorHandler) {
        this.a = sslErrorHandler;
    }

    public void cancel() {
        this.a.cancel();
    }

    public void proceed() {
        this.a.proceed();
    }
}
