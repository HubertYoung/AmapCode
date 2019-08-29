package com.alipay.mobile.common.nbnet.biz.io;

import java.io.ByteArrayInputStream;

public class UploadBytesInputStream extends ByteArrayInputStream implements UploadInputStream {
    public UploadBytesInputStream(byte[] buf) {
        super(buf);
    }

    public UploadBytesInputStream(byte[] buf, int offset, int length) {
        super(buf, offset, length);
    }

    public final boolean a() {
        return false;
    }
}
