package com.alipay.mobile.common.transport.io;

import java.io.BufferedInputStream;
import java.io.InputStream;

public class RpcBufferedInputStream extends BufferedInputStream {
    protected int mReaded = 0;

    public RpcBufferedInputStream(InputStream in) {
        super(in, 8192);
    }

    public synchronized int read() {
        int tmpReaded;
        int tmpmReaded = this.mReaded;
        tmpReaded = super.read();
        if (tmpmReaded == this.mReaded) {
            this.mReaded++;
        }
        return tmpReaded;
    }

    public int read(byte[] b) {
        return read(b, 0, b.length);
    }

    public synchronized int read(byte[] buffer, int offset, int byteCount) {
        int readed;
        try {
            readed = super.read(buffer, offset, byteCount);
            if (readed > 0) {
                this.mReaded += readed;
            }
        }
        return readed;
    }

    public int getPos() {
        return this.pos;
    }

    public int getCount() {
        return this.count;
    }

    public int getReaded() {
        return this.mReaded;
    }

    public synchronized int available() {
        return super.available();
    }
}
