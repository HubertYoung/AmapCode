package com.alipay.mobile.common.nbnet.biz.netlib;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.ClosedChannelException;

public class NBNetOutputStream extends FilterOutputStream {
    private boolean a = false;
    private int b = 0;

    public NBNetOutputStream(OutputStream out) {
        super(out);
        if (out == null) {
            throw new IllegalArgumentException("OutputStream may not be null");
        }
    }

    public void write(int b2) {
        if (this.a) {
            throw new ClosedChannelException();
        }
        super.write(b2);
    }

    public void write(byte[] b2) {
        if (this.a) {
            throw new IOException();
        }
        super.write(b2);
    }

    public void write(byte[] b2, int off, int len) {
        if (this.a) {
            throw new ClosedChannelException();
        }
        this.b += len;
        super.write(b2, off, len);
    }

    public void close() {
        if (!this.a) {
            this.a = true;
        }
    }

    public final int a() {
        return this.b;
    }
}
