package com.alipay.mobile.common.transport.http;

public class ResponseSizeModel {
    public long compressedSize;
    public long rawSize;

    public ResponseSizeModel() {
    }

    public ResponseSizeModel(long compressedSize2, long rawSize2) {
        this.compressedSize = compressedSize2;
        this.rawSize = rawSize2;
    }
}
