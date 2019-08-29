package android.support.v4.util;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ArrayMap<K, V> extends SimpleArrayMap<K, V> implements Map<K, V> {
    MapCollections<K, V> mCollections;

    public ArrayMap() {
    }

    public ArrayMap(int i) {
        super(i);
    }

    public ArrayMap(SimpleArrayMap simpleArrayMap) {
        super(simpleArrayMap);
    }

    private MapCollections<K, V> getCollection() {
        if (this.mCollections == null) {
            this.mCollections = new MapCollections<K, V>() {
                /* access modifiers changed from: protected */
                public final int a() {
                    return ArrayMap.this.mSize;
                }

                /* access modifiers changed from: protected */
                public final Object a(int i, int i2) {
                    return ArrayMap.this.mArray[(i << 1) + i2];
                }

                /* access modifiers changed from: protected */
                public final int a(Object obj) {
                    return ArrayMap.this.indexOfKey(obj);
                }

                /* access modifiers changed from: protected */
                public final int b(Object obj) {
                    return ArrayMap.this.indexOfValue(obj);
                }

                /* access modifiers changed from: protected */
                public final Map<K, V> b() {
                    return ArrayMap.this;
                }

                /* access modifiers changed from: protected */
                public final void a(K k, V v) {
                    ArrayMap.this.put(k, v);
                }

                /* access modifiers changed from: protected */
                public final V a(int i, V v) {
                    return ArrayMap.this.setValueAt(i, v);
                }

                /* access modifiers changed from: protected */
                public final void a(int i) {
                    ArrayMap.this.removeAt(i);
                }

                /* access modifiers changed from: protected */
                public final void c() {
                    ArrayMap.this.clear();
                }
            };
        }
        return this.mCollections;
    }

    public boolean containsAll(Collection<?> collection) {
        return MapCollections.a((Map<K, V>) this, collection);
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        ensureCapacity(this.mSize + map.size());
        for (Entry next : map.entrySet()) {
            put(next.getKey(), next.getValue());
        }
    }

    public boolean removeAll(Collection<?> collection) {
        return MapCollections.b(this, collection);
    }

    public boolean retainAll(Collection<?> collection) {
        return MapCollections.c(this, collection);
    }

    public Set<Entry<K, V>> entrySet() {
        MapCollections collection = getCollection();
        if (collection.b == null) {
            collection.b = new EntrySet<>();
        }
        return collection.b;
    }

    public Set<K> keySet() {
        MapCollections collection = getCollection();
        if (collection.c == null) {
            collection.c = new KeySet<>();
        }
        return collection.c;
    }

    public Collection<V> values() {
        MapCollections collection = getCollection();
        if (collection.d == null) {
            collection.d = new ValuesCollection<>();
        }
        return collection.d;
    }
}
