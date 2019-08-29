package com.uc.webview.export.internal.android;

import android.annotation.TargetApi;
import android.webkit.WebMessage;

@TargetApi(23)
/* compiled from: ProGuard */
public final class m extends WebMessage {
    private com.uc.webview.export.WebMessage a;

    public m(com.uc.webview.export.WebMessage webMessage) {
        super(webMessage.getData());
        this.a = webMessage;
    }

    public final String getData() {
        return this.a.getData();
    }
}
