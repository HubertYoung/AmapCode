package com.ali.auth.third.ui.webview;

import android.webkit.WebView;

class c extends BridgeWebChromeClient {
    final /* synthetic */ BaseWebViewActivity a;

    c(BaseWebViewActivity baseWebViewActivity) {
        this.a = baseWebViewActivity;
    }

    public void onReceivedTitle(WebView webView, String str) {
        if (this.a.canReceiveTitle) {
            this.a.titleText.setText(str);
        }
    }
}
