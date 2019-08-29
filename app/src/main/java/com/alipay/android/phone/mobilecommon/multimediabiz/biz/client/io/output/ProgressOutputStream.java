package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.output;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.TransferredListener;
import java.io.FilterOutputStream;
import java.io.OutputStream;

public class ProgressOutputStream extends FilterOutputStream {
    private final TransferredListener a;
    private long b;
    private long c;

    public ProgressOutputStream(OutputStream out, TransferredListener listener) {
        this(out, 0, listener);
    }

    public ProgressOutputStream(OutputStream out, long offset, TransferredListener listener) {
        super(out);
        this.a = listener;
        this.b = 0;
        this.c = offset;
    }

    public void write(byte[] b2, int off, int len) {
        this.out.write(b2, off, len);
        this.b += (long) len;
        if (this.a != null) {
            this.a.onTransferred(this.b + this.c);
        }
    }

    public void write(int b2) {
        this.out.write(b2);
        this.b++;
        this.a.onTransferred(this.b + this.c);
    }
}
