package com.alipay.mobile.common.nbnet.biz.netlib;

import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.ClosedChannelException;

public class NBNetInputStream extends FilterInputStream {
    private boolean a = false;
    private int b = 0;

    protected NBNetInputStream(InputStream in) {
        super(in);
        if (in == null) {
            throw new IllegalArgumentException("InputStream may not be null");
        }
    }

    public int read(byte[] b2, int off, int len) {
        if (this.a) {
            throw new ClosedChannelException();
        }
        int read = super.read(b2, off, len);
        this.b += read;
        return read;
    }

    public int read() {
        if (!this.a) {
            return super.read();
        }
        throw new IOException("Attempted read from closed stream.");
    }

    public int read(byte[] b2) {
        if (!this.a) {
            return super.read(b2);
        }
        throw new ClosedChannelException();
    }

    public void close() {
        if (!this.a) {
            this.a = true;
            NBNetLogCat.a((String) "NBNetInputStream", (String) "closed");
        }
    }

    public final int a() {
        return this.b;
    }
}
