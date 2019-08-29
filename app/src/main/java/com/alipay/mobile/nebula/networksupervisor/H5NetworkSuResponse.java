package com.alipay.mobile.nebula.networksupervisor;

import java.util.List;
import java.util.Map;

public class H5NetworkSuResponse extends H5NetworkSuEntity {
    private Map<String, List<String>> headers;
    private boolean isChunked;
    private boolean isGzip;
    private int status;

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status2) {
        this.status = status2;
    }

    public Map<String, List<String>> getHeaders() {
        return this.headers;
    }

    public void setHeaders(Map<String, List<String>> headers2) {
        this.headers = headers2;
    }

    public boolean isGzip() {
        return this.isGzip;
    }

    public void setGzip(boolean gzip) {
        this.isGzip = gzip;
    }

    public boolean isChunked() {
        return this.isChunked;
    }

    public void setChunked(boolean chunked) {
        this.isChunked = chunked;
    }

    public String toString() {
        return "H5NetworkSuResponse " + super.toString();
    }
}
