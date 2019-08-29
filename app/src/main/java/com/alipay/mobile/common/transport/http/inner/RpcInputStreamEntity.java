package com.alipay.mobile.common.transport.http.inner;

import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.entity.InputStreamEntity;

public class RpcInputStreamEntity extends InputStreamEntity {
    public RpcInputStreamEntity(InputStream instream, long dataLength) {
        super(instream, dataLength);
    }

    public void writeTo(OutputStream outstream) {
        RpcInputStreamEntity.super.writeTo(outstream);
    }
}
