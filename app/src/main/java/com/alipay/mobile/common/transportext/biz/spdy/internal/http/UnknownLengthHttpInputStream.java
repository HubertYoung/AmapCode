package com.alipay.mobile.common.transportext.biz.spdy.internal.http;

import com.alipay.mobile.common.transportext.biz.spdy.internal.Util;
import java.io.InputStream;
import java.net.CacheRequest;

final class UnknownLengthHttpInputStream extends AbstractHttpInputStream {
    private boolean inputExhausted;

    UnknownLengthHttpInputStream(InputStream in, CacheRequest cacheRequest, HttpEngine httpEngine) {
        super(in, httpEngine, cacheRequest);
    }

    public final int read(byte[] buffer, int offset, int count) {
        Util.checkOffsetAndCount(buffer.length, offset, count);
        checkNotClosed();
        if (this.in == null || this.inputExhausted) {
            return -1;
        }
        int read = this.in.read(buffer, offset, count);
        if (read == -1) {
            this.inputExhausted = true;
            endOfInput();
            return -1;
        }
        cacheWrite(buffer, offset, read);
        return read;
    }

    public final int available() {
        checkNotClosed();
        if (this.in == null) {
            return 0;
        }
        return this.in.available();
    }

    public final void close() {
        if (!this.closed) {
            this.closed = true;
            if (!this.inputExhausted) {
                unexpectedEndOfInput();
            }
        }
    }
}
