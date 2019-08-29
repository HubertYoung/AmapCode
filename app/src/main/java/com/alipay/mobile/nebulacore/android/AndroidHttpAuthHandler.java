package com.alipay.mobile.nebulacore.android;

import android.webkit.HttpAuthHandler;
import com.alipay.mobile.nebula.webview.APHttpAuthHandler;

class AndroidHttpAuthHandler implements APHttpAuthHandler {
    HttpAuthHandler a;

    AndroidHttpAuthHandler(HttpAuthHandler httpAuthHandler) {
        this.a = httpAuthHandler;
    }

    public void cancel() {
        if (this.a != null) {
            this.a.cancel();
        }
    }

    public void proceed(String username, String password) {
        if (this.a != null) {
            this.a.proceed(username, password);
        }
    }

    public boolean useHttpAuthUsernamePassword() {
        return this.a != null && this.a.useHttpAuthUsernamePassword();
    }
}
