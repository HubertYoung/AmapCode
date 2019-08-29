package com.alipay.mobile.nebula.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class H5FixedMap<K, V> {
    private List<K> keyList;
    private HashMap<K, V> map;
    private int sizeLimit;

    public H5FixedMap(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("invalid size");
        }
        this.sizeLimit = size;
        this.map = new HashMap<>(size);
        this.keyList = new ArrayList(size);
    }

    public synchronized V get(K key) {
        V v;
        if (!this.map.containsKey(key)) {
            v = null;
        } else {
            this.keyList.remove(key);
            this.keyList.add(0, key);
            v = this.map.get(key);
        }
        return v;
    }

    public synchronized void set(K key, V value) {
        if (!this.map.containsKey(key)) {
            if (this.map.size() >= this.sizeLimit) {
                this.map.remove(this.keyList.remove(this.map.size() - 1));
            }
            this.keyList.add(0, key);
            this.map.put(key, value);
        }
    }

    public void remove(K key) {
        if (this.map.containsKey(key)) {
            this.map.remove(key);
            this.keyList.remove(key);
        }
    }
}
