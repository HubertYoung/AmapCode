package com.alipay.mobile.tinyappcustom.api;

public class TinyAppAuthManager {
    private TinyAppAuthCallback mAuthCallback;

    private static class TinyAppAuthManagerInner {
        public static TinyAppAuthManager INSTANCE = new TinyAppAuthManager();

        private TinyAppAuthManagerInner() {
        }
    }

    private TinyAppAuthManager() {
    }

    public static TinyAppAuthManager get() {
        return TinyAppAuthManagerInner.INSTANCE;
    }

    public void registerAuthCallback(TinyAppAuthCallback callback) {
        this.mAuthCallback = callback;
    }

    public TinyAppAuthCallback getAuthCallback() {
        return this.mAuthCallback;
    }
}
