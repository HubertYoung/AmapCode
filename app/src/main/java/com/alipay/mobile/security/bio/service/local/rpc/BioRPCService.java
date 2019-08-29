package com.alipay.mobile.security.bio.service.local.rpc;

import com.alipay.mobile.security.bio.service.local.LocalService;
import java.util.Map;

public abstract class BioRPCService extends LocalService {
    public abstract <T> T getRpcProxy(Class<T> cls);

    public abstract void setRemoteUrl(String str);

    public void addRequestHeaders(Object obj, Map<String, String> map) {
    }
}
