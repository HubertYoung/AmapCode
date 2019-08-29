package com.alipay.deviceid.module.rpc.mrpc.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class HttpUrlHeader implements Serializable {
    private static final long serialVersionUID = -6098125857367743614L;
    private Map<String, String> headers = new HashMap();

    public String getHead(String str) {
        return this.headers.get(str);
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public void setHead(String str, String str2) {
        this.headers.put(str, str2);
    }

    public void setHeaders(Map<String, String> map) {
        this.headers = map;
    }
}
