package com.uc.webview.export;

import android.os.Handler;
import com.uc.webview.export.annotations.Api;

@Api
/* compiled from: ProGuard */
public class SslErrorHandler extends Handler {
    public android.webkit.SslErrorHandler mHandler = null;

    public void proceed() {
        this.mHandler.proceed();
    }

    public void cancel() {
        this.mHandler.cancel();
    }
}
