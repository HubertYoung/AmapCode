package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

public class BaseDownResp extends BaseResp {
    private HttpRequestBase method;
    private HttpResponse resp;

    public HttpResponse getResp() {
        return this.resp;
    }

    public void setResp(HttpResponse resp2) {
        this.resp = resp2;
    }

    public HttpRequestBase getMethod() {
        return this.method;
    }

    public void setMethod(HttpRequestBase method2) {
        this.method = method2;
    }
}
