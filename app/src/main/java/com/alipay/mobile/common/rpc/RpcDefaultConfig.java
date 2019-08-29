package com.alipay.mobile.common.rpc;

import com.alipay.mobile.common.transport.Transport;
import com.alipay.mobile.common.transport.http.HttpUrlHeader;
import com.alipay.mobile.common.transport.http.HttpUrlRequest;

public abstract class RpcDefaultConfig implements Config {
    public abstract Transport getTransport();

    public abstract String getUrl();

    public void addExtHeaders(HttpUrlRequest request) {
    }

    public void giveResponseHeader(String operationType, HttpUrlHeader header) {
    }

    public boolean isCompress() {
        return true;
    }

    public String getAppKey() {
        return "";
    }
}
