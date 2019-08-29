package com.alipay.android.phone.mobilecommon.multimediabiz.biz.io;

import java.io.FilterInputStream;
import java.io.InputStream;

public class ProgressInputStream extends FilterInputStream {
    private InputProgressListener a;
    private int b = 0;

    public ProgressInputStream(InputStream in) {
        super(in);
    }

    public ProgressInputStream(InputStream in, InputProgressListener listener) {
        super(in);
        this.a = listener;
    }

    public int read(byte[] buffer, int byteOffset, int byteCount) {
        int read = super.read(buffer, byteOffset, byteCount);
        if (this.a != null) {
            if (read != -1) {
                this.b += read;
            } else {
                this.a.onReadFinish(this.b);
            }
            this.a.onReadProgress(read, this.b);
        }
        return read;
    }
}
