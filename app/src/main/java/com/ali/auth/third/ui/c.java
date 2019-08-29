package com.ali.auth.third.ui;

import android.webkit.WebView;
import com.ali.auth.third.ui.webview.BridgeWebChromeClient;

class c extends BridgeWebChromeClient {
    final /* synthetic */ LoginWebViewActivity a;

    c(LoginWebViewActivity loginWebViewActivity) {
        this.a = loginWebViewActivity;
    }

    public void onReceivedTitle(WebView webView, String str) {
        if (!this.a.canReceiveTitle) {
            return;
        }
        if ((str == null || !str.contains("我喜欢")) && str != null) {
            this.a.titleText.setText(str);
        }
    }
}
