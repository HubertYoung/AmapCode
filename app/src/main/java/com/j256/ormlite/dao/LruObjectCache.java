package com.j256.ormlite.dao;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class LruObjectCache implements ObjectCache {
    private final int capacity;
    private final ConcurrentHashMap<Class<?>, Map<Object, Object>> classMaps = new ConcurrentHashMap<>();

    class LimitedLinkedHashMap<K, V> extends LinkedHashMap<K, V> {
        private static final long serialVersionUID = -4566528080395573236L;
        private final int capacity;

        public LimitedLinkedHashMap(int capacity2) {
            super(capacity2, 0.75f, true);
            this.capacity = capacity2;
        }

        /* access modifiers changed from: protected */
        public boolean removeEldestEntry(Entry<K, V> eldest) {
            return size() > this.capacity;
        }
    }

    public LruObjectCache(int capacity2) {
        this.capacity = capacity2;
    }

    public synchronized <T> void registerClass(Class<T> clazz) {
        if (this.classMaps.get(clazz) == null) {
            this.classMaps.put(clazz, Collections.synchronizedMap(new LimitedLinkedHashMap(this.capacity)));
        }
    }

    public <T, ID> T get(Class<T> clazz, ID id) {
        Map objectMap = getMapForClass(clazz);
        if (objectMap == null) {
            return null;
        }
        return objectMap.get(id);
    }

    public <T, ID> void put(Class<T> clazz, ID id, T data) {
        Map objectMap = getMapForClass(clazz);
        if (objectMap != null) {
            objectMap.put(id, data);
        }
    }

    public <T> void clear(Class<T> clazz) {
        Map objectMap = getMapForClass(clazz);
        if (objectMap != null) {
            objectMap.clear();
        }
    }

    public void clearAll() {
        for (Map<Object, Object> clear : this.classMaps.values()) {
            clear.clear();
        }
    }

    public <T, ID> void remove(Class<T> clazz, ID id) {
        Map objectMap = getMapForClass(clazz);
        if (objectMap != null) {
            objectMap.remove(id);
        }
    }

    public <T, ID> T updateId(Class<T> clazz, ID oldId, ID newId) {
        Map objectMap = getMapForClass(clazz);
        if (objectMap == null) {
            return null;
        }
        Object obj = objectMap.remove(oldId);
        if (obj == null) {
            return null;
        }
        objectMap.put(newId, obj);
        return obj;
    }

    public <T> int size(Class<T> clazz) {
        Map objectMap = getMapForClass(clazz);
        if (objectMap == null) {
            return 0;
        }
        return objectMap.size();
    }

    public int sizeAll() {
        int size = 0;
        for (Map<Object, Object> objectMap : this.classMaps.values()) {
            size += objectMap.size();
        }
        return size;
    }

    private Map<Object, Object> getMapForClass(Class<?> clazz) {
        Map objectMap = this.classMaps.get(clazz);
        if (objectMap == null) {
            return null;
        }
        return objectMap;
    }
}
