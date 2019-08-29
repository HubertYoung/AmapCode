package com.autonavi.minimap.landingpage;

import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebBackForwardList;
import android.webkit.WebHistoryItem;
import android.webkit.WebView;
import com.autonavi.widget.webview.android.SafeWebView.WebViewClientEx;

public class LandingPageHander$6 extends WebViewClientEx {
    final /* synthetic */ dnj this$0;

    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        return false;
    }

    public LandingPageHander$6(dnj dnj) {
        this.this$0 = dnj;
    }

    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        if (VERSION.SDK_INT >= 16 && VERSION.SDK_INT <= 18) {
            webView.setLayerType(1, null);
            webView.setDrawingCacheEnabled(false);
        }
        super.onPageStarted(webView, str, bitmap);
    }

    public void onPageFinished(WebView webView, String str) {
        super.onPageFinished(webView, str);
        dnj.a(this.this$0.c, (View) this.this$0.f, (View) this.this$0.g);
        WebBackForwardList copyBackForwardList = webView.copyBackForwardList();
        if (copyBackForwardList != null) {
            WebHistoryItem currentItem = copyBackForwardList.getCurrentItem();
            if (currentItem != null && str.equals(currentItem.getUrl())) {
                String title = currentItem.getTitle();
                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(this.this$0.m) && !title.equals(this.this$0.m)) {
                    dnj.a(this.this$0, webView.getUrl(), title);
                }
            }
        }
    }
}
