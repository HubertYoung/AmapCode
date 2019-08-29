package com.alipay.mobile.common.transportext.biz.mmtp.mrpc.models;

import java.util.LinkedHashMap;
import java.util.Map;

public class MRpcRequest {
    public int connTimeout;
    private byte[] datas;
    private Map<String, String> headers = new LinkedHashMap(13);
    public boolean important = false;
    public int readTimeout;
    public int reqSeqId = -1;
    public int sslTimeout;
    private String url;

    public MRpcRequest(String url2) {
        this.url = url2;
    }

    public void setDatas(byte[] datas2) {
        this.datas = datas2;
    }

    public byte[] getDatas() {
        return this.datas;
    }

    public String getUrl() {
        return this.url;
    }

    public void addHeader(String name, String value) {
        this.headers.put(name, value);
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public String toString() {
        return "MRpcRequest [url=" + this.url + ", headers=" + this.headers + ", datas=" + new String(this.datas) + ", readTimeout=" + this.readTimeout + "]";
    }
}
