package com.amap.bundle.utils.cache;

import java.util.concurrent.ConcurrentHashMap;

public class KeyExpiryMap<K, V> extends ConcurrentHashMap<K, Long> {
    private static final int DEFAULT_CONCURRENCY_LEVEL = 16;
    private static final long serialVersionUID = 1;

    public KeyExpiryMap(int i, float f, int i2) {
        super(i, f, i2);
    }

    public KeyExpiryMap(int i, float f) {
        super(i, f, 16);
    }

    public KeyExpiryMap(int i) {
        super(i);
    }

    public KeyExpiryMap() {
    }

    public synchronized Long get(Object obj) {
        if (!containsKey(obj)) {
            return null;
        }
        return (Long) super.get(obj);
    }

    public synchronized Long put(K k, Long l) {
        if (containsKey(k)) {
            remove((Object) k);
        }
        return (Long) super.put(k, l);
    }

    public synchronized boolean containsKey(Object obj) {
        boolean z;
        z = false;
        Long l = (Long) super.get(obj);
        if (l != null) {
            if (System.currentTimeMillis() < l.longValue()) {
                z = true;
            } else {
                remove(obj);
            }
        }
        return z;
    }

    public synchronized Long remove(Object obj) {
        return (Long) super.remove(obj);
    }

    public synchronized void clear() {
        super.clear();
    }
}
