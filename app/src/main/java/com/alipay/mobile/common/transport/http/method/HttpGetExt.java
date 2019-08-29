package com.alipay.mobile.common.transport.http.method;

import java.net.URI;
import org.apache.http.client.methods.HttpGet;

public class HttpGetExt extends HttpGet {
    private String a;

    public HttpGetExt(String method) {
        this.a = method;
    }

    public HttpGetExt(URI uri, String method) {
        super(uri);
        this.a = method;
    }

    public HttpGetExt(String uri, String method) {
        super(uri);
        this.a = method;
    }

    public void setMethod(String method) {
        this.a = method;
    }

    public String getMethod() {
        return this.a;
    }
}
