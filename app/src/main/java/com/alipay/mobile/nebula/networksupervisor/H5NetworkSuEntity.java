package com.alipay.mobile.nebula.networksupervisor;

import java.util.HashMap;
import java.util.Map;

public class H5NetworkSuEntity {
    private byte[] body;
    private Map<String, String> extras = new HashMap();
    private String method;
    private String url;

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url2) {
        this.url = url2;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method2) {
        this.method = method2;
    }

    public byte[] getBody() {
        return this.body;
    }

    public void setBody(byte[] body2) {
        this.body = body2;
    }

    public void putExtra(String key, String value) {
        this.extras.put(key, value);
    }

    public String getExtra(String key) {
        return this.extras.get(key);
    }

    public String toString() {
        return "url " + this.url + " method " + this.method;
    }
}
