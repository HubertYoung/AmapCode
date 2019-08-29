package com.alipay.mobile.nebula.data;

import java.io.InputStream;
import org.apache.http.Header;

public class H5CustomHttpResponse {
    private Header[] headers;
    private InputStream inputStream;
    private byte[] resData;
    private int statusCode;

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode2) {
        this.statusCode = statusCode2;
    }

    public Header[] getHeaders() {
        return this.headers;
    }

    public void setHeaders(Header[] headers2) {
        this.headers = headers2;
    }

    public byte[] getResData() {
        return this.resData;
    }

    public void setResData(byte[] resData2) {
        this.resData = resData2;
    }

    public InputStream getResStream() {
        return this.inputStream;
    }

    public void setResStream(InputStream inputStream2) {
        this.inputStream = inputStream2;
    }
}
