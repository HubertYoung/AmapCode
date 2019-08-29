package com.alipay.mobile.common.transport;

public abstract class Request {
    private boolean a = false;
    protected TransportCallback mCallback;

    public TransportCallback getCallback() {
        return this.mCallback;
    }

    public void setTransportCallback(TransportCallback callback) {
        this.mCallback = callback;
    }

    public void cancel() {
        this.a = true;
    }

    public void cancel(String cancelMsg) {
    }

    public boolean isCanceled() {
        return this.a;
    }
}
