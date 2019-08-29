package com.alipay.mobile.common.rpc.transport;

import android.text.TextUtils;
import com.alipay.mobile.common.rpc.RpcInterceptor;
import com.alipay.mobile.common.rpc.RpcInvokeContext;
import com.alipay.mobile.common.transport.http.HeaderMap;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class InnerRpcInvokeContext implements RpcInvokeContext {
    private static final List<RpcInterceptor> DEFAULT_RPC_INTERCEPTOR_LIST = Collections.emptyList();
    private static final String TAG = "RpcInvokeContext";
    public boolean allowBgLogin = false;
    public boolean allowNonNet = false;
    public Boolean allowRetry = null;
    public String appId = null;
    public String appKey;
    public Boolean bgRpc;
    public Boolean compress;
    public boolean disableEnctypt = false;
    public boolean enableEncrypt = false;
    public String gwUrl;
    public boolean isGetMethod = false;
    public boolean isRpcV2 = false;
    public boolean isUrgent = false;
    public int loggerLevel = -1;
    public Map<String, String> requestHeaders = new HeaderMap(5);
    public Boolean resetCookie;
    public Map<String, String> responseHeader;
    protected List<RpcInterceptor> rpcInterceptorList = DEFAULT_RPC_INTERCEPTOR_LIST;
    public boolean switchUserLoginRpc = false;
    public long timeout;

    public void setTimeout(long timeoutMs) {
        this.timeout = timeoutMs;
    }

    public void setGwUrl(String url) {
        this.gwUrl = url;
        LogCatUtil.info(TAG, "setGwUrl: " + url);
    }

    public void setRequestHeaders(Map<String, String> headers) {
        if (headers != null && !headers.isEmpty()) {
            for (Entry entry : headers.entrySet()) {
                if (this.requestHeaders.containsKey(entry.getKey())) {
                    LogCatUtil.warn((String) TAG, "setRequestHeaders. Find duplicate key : " + ((String) entry.getKey()) + " , ignore them.");
                } else {
                    this.requestHeaders.put(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    public Map<String, String> getRequestHeaders() {
        return this.requestHeaders;
    }

    public void addRequestHeader(String k, String v) {
        if (TextUtils.isEmpty(k)) {
            LogCatUtil.warn((String) TAG, (String) "addRequestHeader. key is empty.");
        } else if (v == null) {
            LogCatUtil.warn((String) TAG, (String) "addRequestHeader. value is null.");
        } else if (this.requestHeaders.containsKey(k)) {
            LogCatUtil.warn((String) TAG, "addRequestHeader. Find duplicate key : " + k + " , ignore them.");
        } else {
            this.requestHeaders.put(k, v);
        }
    }

    public void clearRequestHeaders() {
        this.requestHeaders.clear();
    }

    public void removeRequestHeaders(String k) {
        this.requestHeaders.remove(k);
    }

    public Map<String, String> getResponseHeaders() {
        return this.responseHeader;
    }

    public void setCompress(boolean compress2) {
        this.compress = Boolean.valueOf(compress2);
    }

    public void setAppKey(String appKey2) {
        this.appKey = appKey2;
    }

    public void setAppId(String appId2) {
        this.appId = appId2;
    }

    public void setResetCookie(boolean resetCookie2) {
        this.resetCookie = Boolean.valueOf(resetCookie2);
    }

    public void setBgRpc(boolean bgRpc2) {
        this.bgRpc = Boolean.valueOf(bgRpc2);
    }

    public void setAllowRetry(boolean allowRetry2) {
        this.allowRetry = Boolean.valueOf(allowRetry2);
    }

    public void setUrgent(boolean isUrgent2) {
        this.isUrgent = isUrgent2;
    }

    public void setRpcV2(boolean isRpcV22) {
        this.isRpcV2 = isRpcV22;
    }

    public void setAllowBgLogin(boolean allowBgLogin2) {
        this.allowBgLogin = allowBgLogin2;
    }

    public boolean isAllowBgLogin() {
        return this.allowBgLogin;
    }

    public void setAllowNonNet(boolean allowNonNet2) {
        this.allowNonNet = allowNonNet2;
    }

    public boolean isAllowNonNet() {
        return this.allowNonNet;
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

    public void setSwitchUserLoginRpc(boolean isSwitchUserLoginRpc) {
        this.switchUserLoginRpc = isSwitchUserLoginRpc;
    }

    public void setGetMethod(boolean getMethod) {
        this.isGetMethod = getMethod;
    }

    public void setDisableEncrypt(boolean disableEncrypt) {
        this.disableEnctypt = disableEncrypt;
    }

    public void setEnableEncrypt(boolean enableEncrypt2) {
        this.enableEncrypt = enableEncrypt2;
    }

    public void setRpcLoggerLevel(int loggerLevel2) {
        this.loggerLevel = loggerLevel2;
    }

    public List<RpcInterceptor> getRpcInterceptorList() {
        return this.rpcInterceptorList;
    }
}
