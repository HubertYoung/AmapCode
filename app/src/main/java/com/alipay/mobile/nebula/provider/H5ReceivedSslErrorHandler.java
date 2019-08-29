package com.alipay.mobile.nebula.provider;

import android.net.http.SslError;
import com.alipay.mobile.nebula.webview.APSslErrorHandler;
import com.alipay.mobile.nebula.webview.APWebView;

public interface H5ReceivedSslErrorHandler {
    void onReceivedSslError(APWebView aPWebView, APSslErrorHandler aPSslErrorHandler, SslError sslError);
}
