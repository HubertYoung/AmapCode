package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.util;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.util.Map;

public class TQCacheProxy<K, V> extends CacheProxy<K, V> {
    private TQCache<K, V> a;

    public TQCacheProxy(TQCache<K, V> target) {
        this.a = target;
    }

    public V get(K key) {
        return this.a.get(key);
    }

    public V put(K key, V value) {
        return this.a.put(key, value);
    }

    public void trimToSize(int maxSize) {
        this.a.trimToSize(maxSize);
    }

    public V remove(K key) {
        return this.a.remove(key);
    }

    public void evictAll() {
        this.a.evictAll();
    }

    public int size() {
        return this.a.size();
    }

    public int maxSize() {
        return this.a.maxSize();
    }

    public String toString() {
        return this.a.toString();
    }

    public Map<K, V> snapshot() {
        return this.a.snapshot();
    }

    public void debugInfo() {
        Logger.D("TQCacheProxy", toString(), new Object[0]);
    }
}
