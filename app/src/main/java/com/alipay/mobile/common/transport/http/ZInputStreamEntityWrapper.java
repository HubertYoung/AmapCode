package com.alipay.mobile.common.transport.http;

import com.alipay.mobile.common.transport.utils.LogCatUtil;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.entity.InputStreamEntity;

public class ZInputStreamEntityWrapper extends InputStreamEntity {
    private HttpEntity a;

    public ZInputStreamEntityWrapper(InputStream instream, HttpEntity originHttpEntity) {
        super(instream, originHttpEntity.getContentLength());
        this.a = originHttpEntity;
    }

    public void consumeContent() {
        try {
            this.a.consumeContent();
        } catch (Throwable e) {
            LogCatUtil.warn((String) "ZInputStreamEntityWrapper", "mOriginHttpEntity consumeContent exception." + e.toString());
        }
        try {
            ZInputStreamEntityWrapper.super.consumeContent();
        } catch (Throwable e2) {
            LogCatUtil.warn((String) "ZInputStreamEntityWrapper", "consumeContent exception." + e2.toString());
        }
    }
}
