package com.sina.weibo.sdk.network.base;

public class WbResponse {
    private WbResponseBody responseBody;
    private int resultCode = 200;

    public WbResponse(WbResponseBody wbResponseBody) {
        this.responseBody = wbResponseBody;
    }

    public WbResponse(WbResponseBody wbResponseBody, int i) {
        this.responseBody = wbResponseBody;
        this.resultCode = i;
    }

    public WbResponseBody body() {
        return this.responseBody;
    }

    public boolean isSuccessful() {
        return this.resultCode == 200;
    }
}
