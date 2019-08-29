package com.uc.webview.export.internal.android;

import android.webkit.WebView.FindListener;
import com.uc.webview.export.WebView;

/* compiled from: ProGuard */
final class r implements FindListener {
    final /* synthetic */ WebView.FindListener a;
    final /* synthetic */ WebViewAndroid b;

    r(WebViewAndroid webViewAndroid, WebView.FindListener findListener) {
        this.b = webViewAndroid;
        this.a = findListener;
    }

    public final void onFindResultReceived(int i, int i2, boolean z) {
        this.a.onFindResultReceived(i, i2, z);
    }
}
