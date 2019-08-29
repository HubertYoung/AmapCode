package com.alipay.mobile.nebulauc.impl;

import com.alipay.mobile.nebula.webview.APHttpAuthHandler;
import com.uc.webview.export.HttpAuthHandler;

class UCHttpAuthHandler implements APHttpAuthHandler {
    HttpAuthHandler mHttpAuthHandler;

    UCHttpAuthHandler(HttpAuthHandler httpAuthHandler) {
        this.mHttpAuthHandler = httpAuthHandler;
    }

    public void cancel() {
        this.mHttpAuthHandler.cancel();
    }

    public void proceed(String username, String password) {
        this.mHttpAuthHandler.proceed(username, password);
    }

    public boolean useHttpAuthUsernamePassword() {
        return this.mHttpAuthHandler.useHttpAuthUsernamePassword();
    }
}
