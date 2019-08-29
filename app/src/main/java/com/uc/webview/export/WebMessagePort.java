package com.uc.webview.export;

import android.os.Handler;
import com.uc.webview.export.annotations.Api;

@Api
/* compiled from: ProGuard */
public abstract class WebMessagePort {

    @Api
    /* compiled from: ProGuard */
    public static abstract class WebMessageCallback {
        public void onMessage(WebMessagePort webMessagePort, WebMessage webMessage) {
        }
    }

    public abstract void close();

    public abstract void postMessage(WebMessage webMessage);

    public abstract void setWebMessageCallback(WebMessageCallback webMessageCallback);

    public abstract void setWebMessageCallback(WebMessageCallback webMessageCallback, Handler handler);
}
