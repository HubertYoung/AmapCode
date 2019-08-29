package com.alipay.mobile.common.transport.http.method;

import java.net.URI;
import org.apache.http.client.methods.HttpPost;

public class HttpPostExt extends HttpPost {
    private String a;

    public HttpPostExt(String method) {
        this.a = method;
    }

    public HttpPostExt(URI uri, String method) {
        super(uri);
        this.a = method;
    }

    public HttpPostExt(String uri, String method) {
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
