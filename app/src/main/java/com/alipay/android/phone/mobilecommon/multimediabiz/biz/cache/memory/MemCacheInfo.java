package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.memory;

public class MemCacheInfo<T> {
    public long lastAccessTime = System.currentTimeMillis();
    public final T mCache;

    public MemCacheInfo(T cache) {
        this.mCache = cache;
    }
}
