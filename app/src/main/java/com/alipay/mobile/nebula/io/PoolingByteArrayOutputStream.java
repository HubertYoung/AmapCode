package com.alipay.mobile.nebula.io;

import com.alipay.mobile.nebula.util.H5IOUtils;
import java.io.ByteArrayOutputStream;

public class PoolingByteArrayOutputStream extends ByteArrayOutputStream {
    private static final int DEFAULT_SIZE = 1024;
    private final ByteArrayPool mPool;

    public PoolingByteArrayOutputStream() {
        this(H5IOUtils.getByteArrayPool(), 1024);
    }

    public PoolingByteArrayOutputStream(int size) {
        this(H5IOUtils.getByteArrayPool(), size);
    }

    public PoolingByteArrayOutputStream(ByteArrayPool pool) {
        this(pool, 1024);
    }

    public PoolingByteArrayOutputStream(ByteArrayPool pool, int size) {
        this.mPool = pool;
        this.buf = this.mPool.getBuf(Math.max(size, 1024));
    }

    public void close() {
        this.mPool.returnBuf(this.buf);
        this.buf = null;
        super.close();
    }

    public void finalize() {
        this.mPool.returnBuf(this.buf);
    }

    private void expand(int i) {
        if (this.count + i > this.buf.length) {
            byte[] newbuf = this.mPool.getBuf((this.count + i) * 2);
            System.arraycopy(this.buf, 0, newbuf, 0, this.count);
            this.mPool.returnBuf(this.buf);
            this.buf = newbuf;
        }
    }

    public synchronized void write(byte[] buffer, int offset, int len) {
        expand(len);
        super.write(buffer, offset, len);
    }

    public synchronized void write(int oneByte) {
        expand(1);
        super.write(oneByte);
    }
}
