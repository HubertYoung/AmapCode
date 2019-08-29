package com.autonavi.bundle.account.api;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.autonavi.common.Callback;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

public interface IThirdAuth {

    @KeepImplementations
    @KeepName
    public interface IBaichuanSDKWebViewApi {
        void a(WebView webView, WebViewClient webViewClient, WebChromeClient webChromeClient, String str);

        boolean a();

        boolean a(String str);
    }

    public interface a extends bie {
        void a(b bVar);

        boolean a();

        boolean a(BaseReq baseReq);

        boolean b();

        boolean c();
    }

    public interface b {
        void triggerWxShare(BaseResp baseResp);
    }

    a a();

    void a(Callback<String> callback);

    @Deprecated
    IBaichuanSDKWebViewApi b();
}
