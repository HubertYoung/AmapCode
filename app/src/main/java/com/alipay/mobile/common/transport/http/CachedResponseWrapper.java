package com.alipay.mobile.common.transport.http;

import java.io.Serializable;
import org.apache.http.Header;
import org.apache.http.message.BufferedHeader;
import org.apache.http.util.CharArrayBuffer;

public class CachedResponseWrapper implements Serializable {
    private String a;
    private byte[] b;
    private String c;
    private String d;

    public String getEtag() {
        return this.a;
    }

    public void setEtag(String etag) {
        this.a = etag;
    }

    public byte[] getValue() {
        return this.b;
    }

    public void setValue(byte[] value) {
        this.b = value;
    }

    public Header getTypeHeader() {
        String temp = this.c + ": " + this.d;
        CharArrayBuffer buffer = new CharArrayBuffer(temp.length());
        buffer.append(temp);
        return new BufferedHeader(buffer);
    }

    public void setTypeHeader(Header typeHeader) {
        if (typeHeader != null) {
            this.c = typeHeader.getName();
            this.d = typeHeader.getValue();
        }
    }
}
