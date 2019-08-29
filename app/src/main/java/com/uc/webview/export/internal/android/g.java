package com.uc.webview.export.internal.android;

import com.uc.webview.export.SslErrorHandler;
import com.uc.webview.export.annotations.Interface;

@Interface
/* compiled from: ProGuard */
final class g extends SslErrorHandler {
    g(android.webkit.SslErrorHandler sslErrorHandler) {
        this.mHandler = sslErrorHandler;
    }
}
