package com.alipay.mobile.common.transportext.biz.spdy.internal.http;

import com.alipay.mobile.common.transportext.biz.spdy.internal.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CacheRequest;

abstract class AbstractHttpInputStream extends InputStream {
    private final OutputStream cacheBody;
    private final CacheRequest cacheRequest;
    protected boolean closed;
    protected final HttpEngine httpEngine;
    protected final InputStream in;

    AbstractHttpInputStream(InputStream in2, HttpEngine httpEngine2, CacheRequest cacheRequest2) {
        this.in = in2;
        this.httpEngine = httpEngine2;
        OutputStream cacheBody2 = cacheRequest2 != null ? cacheRequest2.getBody() : null;
        cacheRequest2 = cacheBody2 == null ? null : cacheRequest2;
        this.cacheBody = cacheBody2;
        this.cacheRequest = cacheRequest2;
    }

    public final int read() {
        return Util.readSingleByte(this);
    }

    /* access modifiers changed from: protected */
    public final void checkNotClosed() {
        if (this.closed) {
            throw new IOException("stream closed");
        }
    }

    /* access modifiers changed from: protected */
    public final void cacheWrite(byte[] buffer, int offset, int count) {
        if (this.cacheBody != null) {
            this.cacheBody.write(buffer, offset, count);
        }
    }

    /* access modifiers changed from: protected */
    public final void endOfInput() {
        if (this.cacheRequest != null) {
            this.cacheBody.close();
        }
        this.httpEngine.release(false);
    }

    /* access modifiers changed from: protected */
    public final void unexpectedEndOfInput() {
        if (this.cacheRequest != null) {
            this.cacheRequest.abort();
        }
        this.httpEngine.release(true);
    }
}
