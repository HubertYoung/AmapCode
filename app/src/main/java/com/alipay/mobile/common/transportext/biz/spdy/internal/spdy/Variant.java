package com.alipay.mobile.common.transportext.biz.spdy.internal.spdy;

import java.io.InputStream;
import java.io.OutputStream;

interface Variant {
    public static final Variant SPDY3 = new Spdy3();

    FrameReader newReader(InputStream inputStream, boolean z);

    FrameWriter newWriter(OutputStream outputStream, boolean z);
}
