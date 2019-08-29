package com.autonavi.minimap.landingpage;

import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebView;

public class LandingPageHander$5 extends WebChromeClient {
    final /* synthetic */ dnj this$0;

    public LandingPageHander$5(dnj dnj) {
        this.this$0 = dnj;
    }

    public void onReceivedTitle(WebView webView, String str) {
        this.this$0.m = str;
        if (!TextUtils.isEmpty(str)) {
            dnj.a(this.this$0, webView.getUrl(), str);
        }
    }

    public void onShowCustomView(View view, CustomViewCallback customViewCallback) {
        this.this$0.a.setVisibility(4);
    }

    public void onHideCustomView() {
        this.this$0.a.setVisibility(0);
    }
}
