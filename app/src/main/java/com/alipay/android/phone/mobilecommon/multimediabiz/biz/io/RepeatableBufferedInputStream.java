package com.alipay.android.phone.mobilecommon.multimediabiz.biz.io;

import java.io.BufferedInputStream;
import java.io.InputStream;

public class RepeatableBufferedInputStream extends BufferedInputStream {
    public RepeatableBufferedInputStream(InputStream in) {
        super(in);
    }

    public void flip() {
        this.pos = 0;
    }
}
