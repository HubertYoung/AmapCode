package com.alipay.mobile.tinyappcustom.b;

import java.util.Map;

/* compiled from: H5Response */
public final class a {
    private Map<String, String> a;
    private String b;

    public a(Map<String, String> headers, String response) {
        this.a = headers;
        this.b = response;
    }

    public final Map<String, String> a() {
        return this.a;
    }

    public final String b() {
        return this.b;
    }
}
