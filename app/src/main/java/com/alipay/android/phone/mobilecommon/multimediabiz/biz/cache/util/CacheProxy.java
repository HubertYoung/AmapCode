package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.util;

import java.util.Map;

public abstract class CacheProxy<K, V> {
    public abstract void debugInfo();

    public abstract void evictAll();

    public abstract V get(K k);

    public abstract int maxSize();

    public abstract V put(K k, V v);

    public abstract V remove(K k);

    public abstract int size();

    public abstract Map<K, V> snapshot();

    public abstract String toString();

    public abstract void trimToSize(int i);
}
