package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.client.methods.HttpRequestBase;

public class AbstractHttpReq {
    protected Map<String, Object> extraParams;
    protected HttpRequestBase httpRequestBase;
    public int mTimeout = -1;

    public HttpRequestBase getHttpRequestBase() {
        return this.httpRequestBase;
    }

    public void setHttpRequestBase(HttpRequestBase requestBase) {
        this.httpRequestBase = requestBase;
        if (this.httpRequestBase != null && this.httpRequestBase.getParams() != null && this.extraParams != null) {
            for (Entry entry : this.extraParams.entrySet()) {
                this.httpRequestBase.getParams().setParameter((String) entry.getKey(), entry.getValue());
            }
        }
    }

    public void abort() {
        if (this.httpRequestBase != null) {
            this.httpRequestBase.abort();
        }
    }

    public void addExtra(String key, Object val, boolean convert) {
        if (key != null && val != null) {
            if (this.extraParams == null) {
                this.extraParams = new HashMap();
            }
            if (convert || !this.extraParams.containsKey(key)) {
                this.extraParams.put(key, val);
            }
        }
    }
}
