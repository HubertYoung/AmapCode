package com.autonavi.core.network.inter.response;

public class StringResponse extends bpk<String> {
    /* access modifiers changed from: protected */
    public String parseResult() {
        return getResponseBodyString();
    }
}
