package com.alipay.inside.android.phone.mrpc.core;

public abstract class Request {
    private boolean cancel = false;
    protected TransportCallback mCallback;

    public TransportCallback getCallback() {
        return this.mCallback;
    }

    public void setTransportCallback(TransportCallback transportCallback) {
        this.mCallback = transportCallback;
    }

    public void cancel() {
        this.cancel = true;
    }

    public boolean isCanceled() {
        return this.cancel;
    }
}
