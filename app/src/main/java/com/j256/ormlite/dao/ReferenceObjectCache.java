package com.j256.ormlite.dao;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReferenceObjectCache implements ObjectCache {
    private final ConcurrentHashMap<Class<?>, Map<Object, Reference<Object>>> classMaps = new ConcurrentHashMap<>();
    private final boolean useWeak;

    public ReferenceObjectCache(boolean useWeak2) {
        this.useWeak = useWeak2;
    }

    public static ReferenceObjectCache makeWeakCache() {
        return new ReferenceObjectCache(true);
    }

    public static ReferenceObjectCache makeSoftCache() {
        return new ReferenceObjectCache(false);
    }

    public synchronized <T> void registerClass(Class<T> clazz) {
        if (this.classMaps.get(clazz) == null) {
            this.classMaps.put(clazz, new ConcurrentHashMap());
        }
    }

    public <T, ID> T get(Class<T> clazz, ID id) {
        Map objectMap = getMapForClass(clazz);
        if (objectMap == null) {
            return null;
        }
        Reference ref = objectMap.get(id);
        if (ref == null) {
            return null;
        }
        Object obj = ref.get();
        if (obj != null) {
            return obj;
        }
        objectMap.remove(id);
        return null;
    }

    public <T, ID> void put(Class<T> clazz, ID id, T data) {
        Map objectMap = getMapForClass(clazz);
        if (objectMap == null) {
            return;
        }
        if (this.useWeak) {
            objectMap.put(id, new WeakReference(data));
        } else {
            objectMap.put(id, new SoftReference(data));
        }
    }

    public <T> void clear(Class<T> clazz) {
        Map objectMap = getMapForClass(clazz);
        if (objectMap != null) {
            objectMap.clear();
        }
    }

    public void clearAll() {
        for (Map<Object, Reference<Object>> clear : this.classMaps.values()) {
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
        Reference ref = objectMap.remove(oldId);
        if (ref == null) {
            return null;
        }
        objectMap.put(newId, ref);
        return ref.get();
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
        for (Map<Object, Reference<Object>> objectMap : this.classMaps.values()) {
            size += objectMap.size();
        }
        return size;
    }

    public <T> void cleanNullReferences(Class<T> clazz) {
        Map objectMap = getMapForClass(clazz);
        if (objectMap != null) {
            cleanMap(objectMap);
        }
    }

    public <T> void cleanNullReferencesAll() {
        for (Map<Object, Reference<Object>> objectMap : this.classMaps.values()) {
            cleanMap(objectMap);
        }
    }

    private void cleanMap(Map<Object, Reference<Object>> objectMap) {
        Iterator iterator = objectMap.entrySet().iterator();
        while (iterator.hasNext()) {
            if (((Reference) iterator.next().getValue()).get() == null) {
                iterator.remove();
            }
        }
    }

    private Map<Object, Reference<Object>> getMapForClass(Class<?> clazz) {
        Map objectMap = this.classMaps.get(clazz);
        if (objectMap == null) {
            return null;
        }
        return objectMap;
    }
}
