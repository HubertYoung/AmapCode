package com.alipay.mobile.nebula.provider;

public abstract class H5ProviderManager {
    public abstract <T> T getProvider(String str);

    public abstract <T> T getProviderUseCache(String str, boolean z);

    public abstract boolean removeProvider(String str);

    public abstract void setProvider(String str, Object obj);
}
