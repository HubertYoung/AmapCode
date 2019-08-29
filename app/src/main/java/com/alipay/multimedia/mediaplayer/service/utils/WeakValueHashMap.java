package com.alipay.multimedia.mediaplayer.service.utils;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class WeakValueHashMap<K, V> implements Map<K, V> {
    private HashMap<K, EqualWeakReference<V>> mMap = new HashMap<>();

    private class EqualWeakReference<T> extends WeakReference<T> {
        public EqualWeakReference(T r) {
            super(r);
        }

        public EqualWeakReference(T r, ReferenceQueue<? super T> q) {
            super(r, q);
        }

        public boolean equals(Object o) {
            return o != null ? o.equals(get()) : super.equals(o);
        }

        public int hashCode() {
            return get() != null ? get().hashCode() : super.hashCode();
        }
    }

    public void clear() {
        this.mMap.clear();
    }

    public boolean containsKey(Object key) {
        return this.mMap.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return this.mMap.containsValue(new EqualWeakReference(value));
    }

    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    public V get(Object key) {
        WeakReference value = this.mMap.get(key);
        if (value != null) {
            return value.get();
        }
        return null;
    }

    public boolean isEmpty() {
        return this.mMap.isEmpty();
    }

    public Set<K> keySet() {
        return this.mMap.keySet();
    }

    public V put(K key, V value) {
        WeakReference pre = this.mMap.put(key, new EqualWeakReference(value));
        if (pre != null) {
            return pre.get();
        }
        return null;
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException("unsupported!!!");
    }

    public V remove(Object key) {
        WeakReference value = this.mMap.remove(key);
        if (value != null) {
            return value.get();
        }
        return null;
    }

    public int size() {
        return this.mMap.size();
    }

    public Collection<V> values() {
        Collection<EqualWeakReference<V>> values = this.mMap.values();
        Collection collection = new ArrayList();
        for (EqualWeakReference<V> equalWeakReference : values) {
            Object v = equalWeakReference.get();
            if (v != null) {
                collection.add(v);
            }
        }
        return collection;
    }
}
