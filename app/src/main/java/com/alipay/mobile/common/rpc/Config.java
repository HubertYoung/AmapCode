package com.alipay.mobile.common.rpc;

import com.alipay.mobile.common.transport.Transport;
import com.alipay.mobile.common.transport.http.HttpUrlHeader;
import com.alipay.mobile.common.transport.http.HttpUrlRequest;

public interface Config {
    @Deprecated
    void addExtHeaders(HttpUrlRequest httpUrlRequest);

    @Deprecated
    String getAppKey();

    Transport getTransport();

    String getUrl();

    @Deprecated
    void giveResponseHeader(String str, HttpUrlHeader httpUrlHeader);

    @Deprecated
    boolean isCompress();
}
