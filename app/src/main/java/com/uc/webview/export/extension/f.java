package com.uc.webview.export.extension;

import android.webkit.ValueCallback;
import com.uc.webview.export.WebResourceResponse;

/* compiled from: ProGuard */
final class f implements ValueCallback<WebResourceResponse> {
    final /* synthetic */ ValueCallback a;

    f(ValueCallback valueCallback) {
        this.a = valueCallback;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        WebResourceResponse webResourceResponse = (WebResourceResponse) obj;
        if (this.a != null) {
            this.a.onReceiveValue(new android.webkit.WebResourceResponse(webResourceResponse.getMimeType(), webResourceResponse.getEncoding(), webResourceResponse.getData()));
        }
    }
}
