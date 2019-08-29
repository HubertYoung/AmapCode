package android.support.v4.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

abstract class MapCollections<K, V> {
    EntrySet b;
    KeySet c;
    ValuesCollection d;

    final class ArrayIterator<T> implements Iterator<T> {
        final int a;
        int b;
        int c;
        boolean d = false;

        ArrayIterator(int i) {
            this.a = i;
            this.b = MapCollections.this.a();
        }

        public final boolean hasNext() {
            return this.c < this.b;
        }

        public final T next() {
            T a2 = MapCollections.this.a(this.c, this.a);
            this.c++;
            this.d = true;
            return a2;
        }

        public final void remove() {
            if (!this.d) {
                throw new IllegalStateException();
            }
            this.c--;
            this.b--;
            this.d = false;
            MapCollections.this.a(this.c);
        }
    }

    final class EntrySet implements Set<Entry<K, V>> {
        EntrySet() {
        }

        public final boolean addAll(Collection<? extends Entry<K, V>> collection) {
            int a2 = MapCollections.this.a();
            for (Entry entry : collection) {
                MapCollections.this.a(entry.getKey(), entry.getValue());
            }
            return a2 != MapCollections.this.a();
        }

        public final void clear() {
            MapCollections.this.c();
        }

        public final boolean contains(Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            int a2 = MapCollections.this.a(entry.getKey());
            if (a2 < 0) {
                return false;
            }
            return ContainerHelpers.a(MapCollections.this.a(a2, 1), entry.getValue());
        }

        public final boolean containsAll(Collection<?> collection) {
            for (Object contains : collection) {
                if (!contains(contains)) {
                    return false;
                }
            }
            return true;
        }

        public final boolean isEmpty() {
            return MapCollections.this.a() == 0;
        }

        public final Iterator<Entry<K, V>> iterator() {
            return new MapIterator();
        }

        public final boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        public final boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        public final boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        public final int size() {
            return MapCollections.this.a();
        }

        public final Object[] toArray() {
            throw new UnsupportedOperationException();
        }

        public final <T> T[] toArray(T[] tArr) {
            throw new UnsupportedOperationException();
        }

        public final boolean equals(Object obj) {
            return MapCollections.a((Set<T>) this, obj);
        }

        public final int hashCode() {
            int i;
            int i2 = 0;
            for (int a2 = MapCollections.this.a() - 1; a2 >= 0; a2--) {
                Object a3 = MapCollections.this.a(a2, 0);
                Object a4 = MapCollections.this.a(a2, 1);
                if (a3 == null) {
                    i = 0;
                } else {
                    i = a3.hashCode();
                }
                i2 += i ^ (a4 == null ? 0 : a4.hashCode());
            }
            return i2;
        }

        public final /* synthetic */ boolean add(Object obj) {
            throw new UnsupportedOperationException();
        }
    }

    final class KeySet implements Set<K> {
        KeySet() {
        }

        public final boolean add(K k) {
            throw new UnsupportedOperationException();
        }

        public final boolean addAll(Collection<? extends K> collection) {
            throw new UnsupportedOperationException();
        }

        public final void clear() {
            MapCollections.this.c();
        }

        public final boolean contains(Object obj) {
            return MapCollections.this.a(obj) >= 0;
        }

        public final boolean containsAll(Collection<?> collection) {
            return MapCollections.a(MapCollections.this.b(), collection);
        }

        public final boolean isEmpty() {
            return MapCollections.this.a() == 0;
        }

        public final Iterator<K> iterator() {
            return new ArrayIterator(0);
        }

        public final boolean remove(Object obj) {
            int a2 = MapCollections.this.a(obj);
            if (a2 < 0) {
                return false;
            }
            MapCollections.this.a(a2);
            return true;
        }

        public final boolean removeAll(Collection<?> collection) {
            return MapCollections.b(MapCollections.this.b(), collection);
        }

        public final boolean retainAll(Collection<?> collection) {
            return MapCollections.c(MapCollections.this.b(), collection);
        }

        public final int size() {
            return MapCollections.this.a();
        }

        public final Object[] toArray() {
            return MapCollections.this.b(0);
        }

        public final <T> T[] toArray(T[] tArr) {
            return MapCollections.this.a(tArr, 0);
        }

        public final boolean equals(Object obj) {
            return MapCollections.a((Set<T>) this, obj);
        }

        public final int hashCode() {
            int i;
            int i2 = 0;
            for (int a2 = MapCollections.this.a() - 1; a2 >= 0; a2--) {
                Object a3 = MapCollections.this.a(a2, 0);
                if (a3 == null) {
                    i = 0;
                } else {
                    i = a3.hashCode();
                }
                i2 += i;
            }
            return i2;
        }
    }

    final class MapIterator implements Iterator<Entry<K, V>>, Entry<K, V> {
        int a;
        int b;
        boolean c = false;

        MapIterator() {
            this.a = MapCollections.this.a() - 1;
            this.b = -1;
        }

        public final boolean hasNext() {
            return this.b < this.a;
        }

        public final void remove() {
            if (!this.c) {
                throw new IllegalStateException();
            }
            MapCollections.this.a(this.b);
            this.b--;
            this.a--;
            this.c = false;
        }

        public final K getKey() {
            if (this.c) {
                return MapCollections.this.a(this.b, 0);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public final V getValue() {
            if (this.c) {
                return MapCollections.this.a(this.b, 1);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public final V setValue(V v) {
            if (this.c) {
                return MapCollections.this.a(this.b, v);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public final boolean equals(Object obj) {
            if (!this.c) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            } else if (!(obj instanceof Entry)) {
                return false;
            } else {
                Entry entry = (Entry) obj;
                if (!ContainerHelpers.a(entry.getKey(), MapCollections.this.a(this.b, 0)) || !ContainerHelpers.a(entry.getValue(), MapCollections.this.a(this.b, 1))) {
                    return false;
                }
                return true;
            }
        }

        public final int hashCode() {
            int i;
            if (!this.c) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            }
            int i2 = 0;
            Object a2 = MapCollections.this.a(this.b, 0);
            Object a3 = MapCollections.this.a(this.b, 1);
            if (a2 == null) {
                i = 0;
            } else {
                i = a2.hashCode();
            }
            if (a3 != null) {
                i2 = a3.hashCode();
            }
            return i ^ i2;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(getKey());
            sb.append("=");
            sb.append(getValue());
            return sb.toString();
        }

        public final /* bridge */ /* synthetic */ Object next() {
            this.b++;
            this.c = true;
            return this;
        }
    }

    final class ValuesCollection implements Collection<V> {
        ValuesCollection() {
        }

        public final boolean add(V v) {
            throw new UnsupportedOperationException();
        }

        public final boolean addAll(Collection<? extends V> collection) {
            throw new UnsupportedOperationException();
        }

        public final void clear() {
            MapCollections.this.c();
        }

        public final boolean contains(Object obj) {
            return MapCollections.this.b(obj) >= 0;
        }

        public final boolean containsAll(Collection<?> collection) {
            for (Object contains : collection) {
                if (!contains(contains)) {
                    return false;
                }
            }
            return true;
        }

        public final boolean isEmpty() {
            return MapCollections.this.a() == 0;
        }

        public final Iterator<V> iterator() {
            return new ArrayIterator(1);
        }

        public final boolean remove(Object obj) {
            int b = MapCollections.this.b(obj);
            if (b < 0) {
                return false;
            }
            MapCollections.this.a(b);
            return true;
        }

        public final boolean removeAll(Collection<?> collection) {
            int a2 = MapCollections.this.a();
            int i = 0;
            boolean z = false;
            while (i < a2) {
                if (collection.contains(MapCollections.this.a(i, 1))) {
                    MapCollections.this.a(i);
                    i--;
                    a2--;
                    z = true;
                }
                i++;
            }
            return z;
        }

        public final boolean retainAll(Collection<?> collection) {
            int a2 = MapCollections.this.a();
            int i = 0;
            boolean z = false;
            while (i < a2) {
                if (!collection.contains(MapCollections.this.a(i, 1))) {
                    MapCollections.this.a(i);
                    i--;
                    a2--;
                    z = true;
                }
                i++;
            }
            return z;
        }

        public final int size() {
            return MapCollections.this.a();
        }

        public final Object[] toArray() {
            return MapCollections.this.b(1);
        }

        public final <T> T[] toArray(T[] tArr) {
            return MapCollections.this.a(tArr, 1);
        }
    }

    /* access modifiers changed from: protected */
    public abstract int a();

    /* access modifiers changed from: protected */
    public abstract int a(Object obj);

    /* access modifiers changed from: protected */
    public abstract Object a(int i, int i2);

    /* access modifiers changed from: protected */
    public abstract V a(int i, V v);

    /* access modifiers changed from: protected */
    public abstract void a(int i);

    /* access modifiers changed from: protected */
    public abstract void a(K k, V v);

    /* access modifiers changed from: protected */
    public abstract int b(Object obj);

    /* access modifiers changed from: protected */
    public abstract Map<K, V> b();

    /* access modifiers changed from: protected */
    public abstract void c();

    MapCollections() {
    }

    public static <K, V> boolean a(Map<K, V> map, Collection<?> collection) {
        for (Object containsKey : collection) {
            if (!map.containsKey(containsKey)) {
                return false;
            }
        }
        return true;
    }

    public static <K, V> boolean b(Map<K, V> map, Collection<?> collection) {
        int size = map.size();
        for (Object remove : collection) {
            map.remove(remove);
        }
        return size != map.size();
    }

    public static <K, V> boolean c(Map<K, V> map, Collection<?> collection) {
        int size = map.size();
        Iterator<K> it = map.keySet().iterator();
        while (it.hasNext()) {
            if (!collection.contains(it.next())) {
                it.remove();
            }
        }
        return size != map.size();
    }

    public final Object[] b(int i) {
        int a = a();
        Object[] objArr = new Object[a];
        for (int i2 = 0; i2 < a; i2++) {
            objArr[i2] = a(i2, i);
        }
        return objArr;
    }

    public final <T> T[] a(T[] tArr, int i) {
        int a = a();
        if (tArr.length < a) {
            tArr = (Object[]) Array.newInstance(tArr.getClass().getComponentType(), a);
        }
        for (int i2 = 0; i2 < a; i2++) {
            tArr[i2] = a(i2, i);
        }
        if (tArr.length > a) {
            tArr[a] = null;
        }
        return tArr;
    }

    public static <T> boolean a(Set<T> set, Object obj) {
        if (set == obj) {
            return true;
        }
        if (!(obj instanceof Set)) {
            return false;
        }
        Set set2 = (Set) obj;
        try {
            return set.size() == set2.size() && set.containsAll(set2);
        } catch (NullPointerException unused) {
            return false;
        } catch (ClassCastException unused2) {
            return false;
        }
    }
}
