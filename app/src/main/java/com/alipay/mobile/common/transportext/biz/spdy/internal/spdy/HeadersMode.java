package com.alipay.mobile.common.transportext.biz.spdy.internal.spdy;

enum HeadersMode {
    SPDY_SYN_STREAM,
    SPDY_REPLY,
    SPDY_HEADERS,
    HTTP_20_HEADERS;

    public final boolean failIfStreamAbsent() {
        return this == SPDY_REPLY || this == SPDY_HEADERS;
    }

    public final boolean failIfStreamPresent() {
        return this == SPDY_SYN_STREAM;
    }

    public final boolean failIfHeadersAbsent() {
        return this == SPDY_HEADERS;
    }

    public final boolean failIfHeadersPresent() {
        return this == SPDY_REPLY;
    }
}
