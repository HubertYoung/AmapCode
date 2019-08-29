package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.internal.SDKFactory;

/* compiled from: ProGuard */
final class ci implements ValueCallback {
    ci() {
    }

    public final void onReceiveValue(Object obj) {
        SDKFactory.q = true;
        SDKFactory.r = true;
    }
}
