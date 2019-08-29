package com.alipay.mobile.common.nbnet.biz.io;

import java.io.Closeable;

public interface UploadInputStream extends Closeable {
    boolean a();

    void close();

    int read(byte[] bArr);
}
