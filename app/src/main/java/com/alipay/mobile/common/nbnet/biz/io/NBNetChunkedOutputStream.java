package com.alipay.mobile.common.nbnet.biz.io;

import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.mobile.common.nbnet.biz.util.IOUtils;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.channels.ClosedChannelException;

public class NBNetChunkedOutputStream extends OutputStream {
    public static final byte[] b = {48, 13, 10, 13, 10};
    private static final byte[] c = {13, 10};
    private static final byte[] d = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
    protected boolean a;
    private final byte[] e;
    private final OutputStream f;
    private final int g;
    private final ByteArrayOutputStream h;

    private NBNetChunkedOutputStream(OutputStream socketOut) {
        this.e = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 13, 10};
        this.f = socketOut;
        this.g = Math.max(1, 1293);
        this.h = new ByteArrayOutputStream(SecExceptionCode.SEC_ERROR_MALDETECT);
    }

    public NBNetChunkedOutputStream(OutputStream socketOut, byte b2) {
        this(socketOut);
    }

    public synchronized void write(byte[] buffer, int offset, int count) {
        int numBytesWritten;
        b();
        IOUtils.a(buffer.length, offset, count);
        while (count > 0) {
            if (this.h.size() > 0 || count < this.g) {
                numBytesWritten = Math.min(count, this.g - this.h.size());
                this.h.write(buffer, offset, numBytesWritten);
                if (this.h.size() == this.g) {
                    a();
                }
            } else {
                numBytesWritten = this.g;
                a(numBytesWritten);
                this.f.write(buffer, offset, numBytesWritten);
                this.f.write(c);
            }
            offset += numBytesWritten;
            count -= numBytesWritten;
        }
    }

    private void a(int i) {
        int cursor = 8;
        do {
            cursor--;
            this.e[cursor] = d[i & 15];
            i >>>= 4;
        } while (i != 0);
        this.f.write(this.e, cursor, this.e.length - cursor);
    }

    public synchronized void flush() {
        if (!this.a) {
            a();
            this.f.flush();
        }
    }

    public synchronized void close() {
        if (!this.a) {
            this.a = true;
            a();
            this.f.write(b);
            this.f.flush();
        }
    }

    private void a() {
        int size = this.h.size();
        if (size > 0) {
            a(size);
            this.h.writeTo(this.f);
            this.h.reset();
            this.f.write(c);
        }
    }

    public final void write(int data) {
        write(new byte[]{(byte) data});
    }

    private void b() {
        if (this.a) {
            throw new ClosedChannelException();
        }
    }
}
