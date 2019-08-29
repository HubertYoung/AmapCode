package com.alipay.mobile.common.transportext.biz.spdy.internal;

import java.io.IOException;
import java.io.OutputStream;

public abstract class AbstractOutputStream extends OutputStream {
    protected boolean closed;

    public final void write(int data) {
        write(new byte[]{(byte) data});
    }

    /* access modifiers changed from: protected */
    public final void checkNotClosed() {
        if (this.closed) {
            throw new IOException("stream closed");
        }
    }

    public boolean isClosed() {
        return this.closed;
    }
}
