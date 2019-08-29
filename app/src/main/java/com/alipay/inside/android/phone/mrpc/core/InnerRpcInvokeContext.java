package com.alipay.inside.android.phone.mrpc.core;

import com.alipay.android.phone.inside.log.api.LoggerFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class InnerRpcInvokeContext implements RpcInvokeContext {
    private static final List<RpcInterceptor> DEFAULT_RPC_INTERCEPTOR_LIST = Collections.emptyList();
    public String appKey;
    public Boolean compress;
    public String gwUrl;
    public boolean isRpcV2 = false;
    public Map<String, String> requestHeaders;
    public Boolean resetCookie;
    public Map<String, String> responseHeader;
    private List<RpcInterceptor> rpcInterceptorList = DEFAULT_RPC_INTERCEPTOR_LIST;
    public long timeout;

    public void setTimeout(long j) {
        this.timeout = j;
    }

    public void setGwUrl(String str) {
        this.gwUrl = str;
        LoggerFactory.f().b((String) "RpcInvokeContext", "setGwUrl: ".concat(String.valueOf(str)));
    }

    public void setRequestHeaders(Map<String, String> map) {
        this.requestHeaders = map;
    }

    public Map<String, String> getResponseHeaders() {
        return this.responseHeader;
    }

    public void setCompress(boolean z) {
        this.compress = Boolean.valueOf(z);
    }

    public void setAppKey(String str) {
        this.appKey = str;
    }

    public void setResetCookie(boolean z) {
        this.resetCookie = Boolean.valueOf(z);
    }

    public void addRpcInterceptor(RpcInterceptor rpcInterceptor) {
        if (this.rpcInterceptorList == null || this.rpcInterceptorList == DEFAULT_RPC_INTERCEPTOR_LIST) {
            this.rpcInterceptorList = new ArrayList(1);
        }
        this.rpcInterceptorList.add(rpcInterceptor);
    }

    public boolean removeRpcInterceptor(RpcInterceptor rpcInterceptor) {
        if (this.rpcInterceptorList == DEFAULT_RPC_INTERCEPTOR_LIST || this.rpcInterceptorList.isEmpty()) {
            return true;
        }
        return this.rpcInterceptorList.remove(rpcInterceptor);
    }

    public List<RpcInterceptor> getRpcInterceptorList() {
        return this.rpcInterceptorList;
    }
}
