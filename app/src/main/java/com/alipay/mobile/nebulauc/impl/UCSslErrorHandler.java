package com.alipay.mobile.nebulauc.impl;

import com.alipay.mobile.nebula.webview.APSslErrorHandler;
import com.uc.webview.export.SslErrorHandler;

class UCSslErrorHandler implements APSslErrorHandler {
    private SslErrorHandler mSslErrorHandler;

    UCSslErrorHandler(SslErrorHandler sslErrorHandler) {
        this.mSslErrorHandler = sslErrorHandler;
    }

    public void cancel() {
        this.mSslErrorHandler.cancel();
    }

    public void proceed() {
        this.mSslErrorHandler.proceed();
    }
}
