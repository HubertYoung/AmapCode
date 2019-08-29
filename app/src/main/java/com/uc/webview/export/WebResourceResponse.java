package com.uc.webview.export;

import com.uc.webview.export.annotations.Api;
import java.io.InputStream;
import java.util.Map;

@Api
/* compiled from: ProGuard */
public class WebResourceResponse {
    private String a;
    private String b;
    private InputStream c;
    private String d = "OK";
    private int e = 200;
    private Map<String, String> f;

    public WebResourceResponse(String str, String str2, InputStream inputStream) {
        this.a = str;
        this.b = str2;
        this.c = inputStream;
    }

    public void setMimeType(String str) {
        this.a = str;
    }

    public String getMimeType() {
        return this.a;
    }

    public void setEncoding(String str) {
        this.b = str;
    }

    public String getEncoding() {
        return this.b;
    }

    public void setData(InputStream inputStream) {
        this.c = inputStream;
    }

    public InputStream getData() {
        return this.c;
    }

    public String getReasonPhrase() {
        return this.d;
    }

    public int getStatusCode() {
        return this.e;
    }

    public void setStatusCodeAndReasonPhrase(int i, String str) {
        this.d = str;
        this.e = i;
    }

    public Map<String, String> getResponseHeaders() {
        return this.f;
    }

    public void setResponseHeaders(Map<String, String> map) {
        this.f = map;
    }
}
