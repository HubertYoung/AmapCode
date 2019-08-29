package com.alibaba.baichuan.android.jsbridge;

import android.content.Context;
import android.webkit.WebView;

public interface AlibcWebview {
    void clearCache();

    Context getContext();

    Object getJsObject(String str);

    WebView getWebView();

    void loadUrl(String str);
}
