package com.alipay.mobile.common.transportext.biz.spdy;

public enum ResponseSource {
    CACHE,
    CONDITIONAL_CACHE,
    NETWORK;

    public final boolean requiresConnection() {
        return this == CONDITIONAL_CACHE || this == NETWORK;
    }
}
