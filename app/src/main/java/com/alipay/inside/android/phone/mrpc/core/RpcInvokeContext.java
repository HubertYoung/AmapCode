package com.alipay.inside.android.phone.mrpc.core;

import java.util.Map;

public interface RpcInvokeContext {
    void addRpcInterceptor(RpcInterceptor rpcInterceptor);

    Map<String, String> getResponseHeaders();

    boolean removeRpcInterceptor(RpcInterceptor rpcInterceptor);

    void setAppKey(String str);

    void setCompress(boolean z);

    void setGwUrl(String str);

    void setRequestHeaders(Map<String, String> map);

    void setResetCookie(boolean z);

    void setTimeout(long j);
}
