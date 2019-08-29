package com.alipay.mobile.common.rpc;

import java.util.Map;

public interface RpcInvokeContext {
    void addRequestHeader(String str, String str2);

    void addRpcInterceptor(RpcInterceptor rpcInterceptor);

    void clearRequestHeaders();

    Map<String, String> getRequestHeaders();

    Map<String, String> getResponseHeaders();

    boolean isAllowBgLogin();

    boolean isAllowNonNet();

    void removeRequestHeaders(String str);

    boolean removeRpcInterceptor(RpcInterceptor rpcInterceptor);

    void setAllowBgLogin(boolean z);

    void setAllowNonNet(boolean z);

    void setAllowRetry(boolean z);

    void setAppId(String str);

    void setAppKey(String str);

    void setBgRpc(boolean z);

    void setCompress(boolean z);

    void setDisableEncrypt(boolean z);

    void setEnableEncrypt(boolean z);

    void setGetMethod(boolean z);

    void setGwUrl(String str);

    void setRequestHeaders(Map<String, String> map);

    void setResetCookie(boolean z);

    void setRpcLoggerLevel(int i);

    void setRpcV2(boolean z);

    void setSwitchUserLoginRpc(boolean z);

    void setTimeout(long j);

    void setUrgent(boolean z);
}
