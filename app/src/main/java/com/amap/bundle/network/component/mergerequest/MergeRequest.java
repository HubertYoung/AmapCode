package com.amap.bundle.network.component.mergerequest;

import com.amap.bundle.aosservice.request.AosRequest;

public class MergeRequest extends AosRequest {
    protected String a;
    protected String b;

    public void setKey(String str) {
        this.a = str;
    }

    public String getKey() {
        return this.a;
    }

    public void setPath(String str) {
        this.b = str;
    }

    public String getPath() {
        return this.b;
    }

    public bph createHttpRequest() {
        throw new RuntimeException("merge request must send by MergeRequester!");
    }
}
