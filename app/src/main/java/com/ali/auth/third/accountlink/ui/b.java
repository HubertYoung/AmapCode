package com.ali.auth.third.accountlink.ui;

import android.webkit.WebView;
import com.ali.auth.third.core.callback.LoginCallback;
import com.ali.auth.third.core.model.Session;

class b implements LoginCallback {
    final /* synthetic */ WebView a;
    final /* synthetic */ a b;

    b(a aVar, WebView webView) {
        this.b = aVar;
        this.a = webView;
    }

    public void onFailure(int i, String str) {
    }

    public void onSuccess(Session session) {
        this.b.a.a = false;
        this.a.reload();
    }
}
