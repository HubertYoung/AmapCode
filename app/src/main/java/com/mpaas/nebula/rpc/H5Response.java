package com.mpaas.nebula.rpc;

import java.util.Map;

public class H5Response {
    private Map<String, String> a;
    private String b;

    public H5Response(Map<String, String> headers, String response) {
        this.a = headers;
        this.b = response;
    }

    public Map<String, String> getHeaders() {
        return this.a;
    }

    public String getResponse() {
        return this.b;
    }
}
