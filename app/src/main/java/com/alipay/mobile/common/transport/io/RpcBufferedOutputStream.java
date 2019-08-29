package com.alipay.mobile.common.transport.io;

import java.io.BufferedOutputStream;
import java.io.OutputStream;

public class RpcBufferedOutputStream extends BufferedOutputStream {
    private int a = 0;

    public RpcBufferedOutputStream(OutputStream out) {
        super(out);
    }

    public RpcBufferedOutputStream(OutputStream out, int size) {
        super(out, size);
    }

    public synchronized void write(int oneByte) {
        super.write(oneByte);
        this.a++;
    }

    public synchronized void write(byte[] buffer, int offset, int length) {
        super.write(buffer, offset, length);
        this.a += length;
    }

    public void write(byte[] b) {
        super.write(b, 0, b.length);
    }

    public int getContentLength() {
        return this.a;
    }
}
