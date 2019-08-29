package com.alipay.mobile.common.transportext.biz.spdy.internal.http;

import com.alipay.mobile.common.transportext.biz.spdy.internal.AbstractOutputStream;
import com.alipay.mobile.common.transportext.biz.spdy.internal.Util;
import com.alipay.mobile.common.transportext.biz.spdy.mwallet.ErrorMsgHelper;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.ProtocolException;

final class RetryableOutputStream extends AbstractOutputStream {
    private final ByteArrayOutputStream content;
    private final int limit;

    public RetryableOutputStream(int limit2) {
        this.limit = limit2;
        this.content = new ByteArrayOutputStream(limit2);
    }

    public RetryableOutputStream() {
        this.limit = -1;
        this.content = new ByteArrayOutputStream();
    }

    public final synchronized void close() {
        if (!this.closed) {
            this.closed = true;
            if (this.content.size() < this.limit) {
                throw new ProtocolException(String.format(ErrorMsgHelper.getMessage(ErrorMsgHelper.CONTENT_LENGTH_NO_EQ_LIMIT), new Object[]{Integer.valueOf(this.limit), Integer.valueOf(this.content.size())}));
            }
        }
    }

    public final synchronized void write(byte[] buffer, int offset, int count) {
        checkNotClosed();
        Util.checkOffsetAndCount(buffer.length, offset, count);
        if (this.limit == -1 || this.content.size() <= this.limit - count) {
            this.content.write(buffer, offset, count);
        } else {
            throw new ProtocolException(String.format(ErrorMsgHelper.getMessage(ErrorMsgHelper.EXCEEDED_CONTENT_LENGTH_LIMIT), new Object[]{Integer.valueOf(this.limit)}));
        }
    }

    public final synchronized int contentLength() {
        try {
            close();
        }
        return this.content.size();
    }

    public final void writeToSocket(OutputStream socketOut) {
        this.content.writeTo(socketOut);
    }
}
