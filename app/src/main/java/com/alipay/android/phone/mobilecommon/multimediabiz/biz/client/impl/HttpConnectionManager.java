package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.impl;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config.ConnectionManager;
import org.apache.http.client.HttpClient;

public class HttpConnectionManager extends ConnectionManager<HttpClient> {
    private HttpClientProxy a;

    public HttpClient getConnection() {
        return getConnection(false);
    }

    public HttpClient getConnection(boolean bHttps) {
        if (this.a == null) {
            this.a = new HttpClientProxy(bHttps);
        }
        return this.a;
    }
}
