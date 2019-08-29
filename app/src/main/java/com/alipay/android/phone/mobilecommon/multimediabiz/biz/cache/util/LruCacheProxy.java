package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.util;

import android.support.v4.util.LruCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.util.Map;

public class LruCacheProxy<K, V> extends CacheProxy<K, V> {
    private LruCache<K, V> a;

    public LruCacheProxy(LruCache<K, V> target) {
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
        Logger.D("LruCacheProxy", toString() + ", size: " + size(), new Object[0]);
    }
}
