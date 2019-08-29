package com.alipay.mobile.common.transportext.biz.shared.io;

import java.io.BufferedOutputStream;
import java.io.OutputStream;

public class ExtBufferedOutputStream extends BufferedOutputStream {
    public ExtBufferedOutputStream(OutputStream out) {
        super(out);
    }

    public ExtBufferedOutputStream(OutputStream out, int size) {
        super(out, size);
    }

    public synchronized void flush() {
        super.flush();
    }
}
