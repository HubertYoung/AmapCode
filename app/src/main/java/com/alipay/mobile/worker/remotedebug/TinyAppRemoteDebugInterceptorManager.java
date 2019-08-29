package com.alipay.mobile.worker.remotedebug;

public class TinyAppRemoteDebugInterceptorManager {
    private TinyAppRemoteDebugInterceptor a;
    private volatile boolean b;

    private static class TinyAppRemoteDebugInterceptorManagerInner {
        public static TinyAppRemoteDebugInterceptorManager INSTANCE = new TinyAppRemoteDebugInterceptorManager(0);

        private TinyAppRemoteDebugInterceptorManagerInner() {
        }
    }

    /* synthetic */ TinyAppRemoteDebugInterceptorManager(byte b2) {
        this();
    }

    private TinyAppRemoteDebugInterceptorManager() {
        this.b = false;
    }

    public static TinyAppRemoteDebugInterceptorManager get() {
        return TinyAppRemoteDebugInterceptorManagerInner.INSTANCE;
    }

    public void injectRemoteDebugInterceptor(TinyAppRemoteDebugInterceptor interceptor) {
        this.a = interceptor;
    }

    public TinyAppRemoteDebugInterceptor getTinyAppRemoteDebugInterceptor() {
        return this.a;
    }

    public void setRemoteDebug(boolean remoteDebug) {
        this.b = remoteDebug;
    }

    public boolean isRemoteDebug() {
        return this.b;
    }
}
