package com.alipay.mobile.common.transport.http.multipart;

public abstract class PartBase extends Part {
    private String a;
    private String b;
    private String c;
    private String d;

    public PartBase(String name, String contentType, String charSet, String transferEncoding) {
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }
        this.a = name;
        this.b = contentType;
        this.c = charSet;
        this.d = transferEncoding;
    }

    public String getName() {
        return this.a;
    }

    public String getContentType() {
        return this.b;
    }

    public String getCharSet() {
        return this.c;
    }

    public String getTransferEncoding() {
        return this.d;
    }

    public void setCharSet(String charSet) {
        this.c = charSet;
    }

    public void setContentType(String contentType) {
        this.b = contentType;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }
        this.a = name;
    }

    public void setTransferEncoding(String transferEncoding) {
        this.d = transferEncoding;
    }
}
