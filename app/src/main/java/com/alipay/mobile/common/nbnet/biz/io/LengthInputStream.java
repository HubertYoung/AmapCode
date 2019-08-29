package com.alipay.mobile.common.nbnet.biz.io;

import java.io.InputStream;

public class LengthInputStream extends InputStream {
    private final InputStream a;
    private int b;

    public LengthInputStream(InputStream inputStream, int length) {
        this.a = inputStream;
        this.b = length;
    }

    public synchronized int read() {
        int readedByte;
        readedByte = -1;
        if (this.b > 0) {
            readedByte = this.a.read();
            this.b--;
        }
        return readedByte;
    }

    public synchronized int read(byte[] buffer, int offset, int byteCount) {
        int readedCount;
        readedCount = -1;
        if (this.a != null && this.b > 0) {
            readedCount = this.a.read(buffer, offset, Math.min(this.b, byteCount));
            this.b -= readedCount;
        }
        return readedCount;
    }

    public void close() {
        if (this.a != null) {
            this.a.close();
        }
    }
}
