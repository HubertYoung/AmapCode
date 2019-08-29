package com.alibaba.sqlcrypto;

import java.util.LinkedHashMap;
import java.util.Map;

public class LruCache<K, V> {
    private int createCount;
    private int evictionCount;
    private int hitCount;
    private final LinkedHashMap<K, V> map;
    private int maxSize;
    private int missCount;
    private int putCount;
    private int size;

    public LruCache(int maxSize2) {
        if (maxSize2 <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.maxSize = maxSize2;
        this.map = new LinkedHashMap<>(0, 0.75f, true);
    }

    public void resize(int maxSize2) {
        if (maxSize2 <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        synchronized (this) {
            this.maxSize = maxSize2;
        }
        trimToSize(maxSize2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0023, code lost:
        r0 = create(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0027, code lost:
        if (r0 != null) goto L_0x002e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x002e, code lost:
        monitor-enter(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        r4.createCount++;
        r1 = r4.map.put(r5, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x003b, code lost:
        if (r1 == null) goto L_0x004b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003d, code lost:
        r4.map.put(r5, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0042, code lost:
        monitor-exit(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0043, code lost:
        if (r1 == null) goto L_0x0058;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0045, code lost:
        entryRemoved(false, r5, r0, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        r4.size += safeSizeOf(r5, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0058, code lost:
        trimToSize(r4.maxSize);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final V get(K r5) {
        /*
            r4 = this;
            if (r5 != 0) goto L_0x000a
            java.lang.NullPointerException r2 = new java.lang.NullPointerException
            java.lang.String r3 = "key == null"
            r2.<init>(r3)
            throw r2
        L_0x000a:
            monitor-enter(r4)
            java.util.LinkedHashMap<K, V> r2 = r4.map     // Catch:{ all -> 0x002b }
            java.lang.Object r1 = r2.get(r5)     // Catch:{ all -> 0x002b }
            if (r1 == 0) goto L_0x001c
            int r2 = r4.hitCount     // Catch:{ all -> 0x002b }
            int r2 = r2 + 1
            r4.hitCount = r2     // Catch:{ all -> 0x002b }
            monitor-exit(r4)     // Catch:{ all -> 0x002b }
            r0 = r1
        L_0x001b:
            return r0
        L_0x001c:
            int r2 = r4.missCount     // Catch:{ all -> 0x002b }
            int r2 = r2 + 1
            r4.missCount = r2     // Catch:{ all -> 0x002b }
            monitor-exit(r4)     // Catch:{ all -> 0x002b }
            java.lang.Object r0 = r4.create(r5)
            if (r0 != 0) goto L_0x002e
            r0 = 0
            goto L_0x001b
        L_0x002b:
            r2 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x002b }
            throw r2
        L_0x002e:
            monitor-enter(r4)
            int r2 = r4.createCount     // Catch:{ all -> 0x0055 }
            int r2 = r2 + 1
            r4.createCount = r2     // Catch:{ all -> 0x0055 }
            java.util.LinkedHashMap<K, V> r2 = r4.map     // Catch:{ all -> 0x0055 }
            java.lang.Object r1 = r2.put(r5, r0)     // Catch:{ all -> 0x0055 }
            if (r1 == 0) goto L_0x004b
            java.util.LinkedHashMap<K, V> r2 = r4.map     // Catch:{ all -> 0x0055 }
            r2.put(r5, r1)     // Catch:{ all -> 0x0055 }
        L_0x0042:
            monitor-exit(r4)     // Catch:{ all -> 0x0055 }
            if (r1 == 0) goto L_0x0058
            r2 = 0
            r4.entryRemoved(r2, r5, r0, r1)
            r0 = r1
            goto L_0x001b
        L_0x004b:
            int r2 = r4.size     // Catch:{ all -> 0x0055 }
            int r3 = r4.safeSizeOf(r5, r0)     // Catch:{ all -> 0x0055 }
            int r2 = r2 + r3
            r4.size = r2     // Catch:{ all -> 0x0055 }
            goto L_0x0042
        L_0x0055:
            r2 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0055 }
            throw r2
        L_0x0058:
            int r2 = r4.maxSize
            r4.trimToSize(r2)
            goto L_0x001b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sqlcrypto.LruCache.get(java.lang.Object):java.lang.Object");
    }

    public final V put(K key, V value) {
        Object previous;
        if (key == null || value == null) {
            throw new NullPointerException("key == null || value == null");
        }
        synchronized (this) {
            this.putCount++;
            this.size += safeSizeOf(key, value);
            previous = this.map.put(key, value);
            if (previous != null) {
                this.size -= safeSizeOf(key, previous);
            }
        }
        if (previous != null) {
            entryRemoved(false, key, previous, value);
        }
        trimToSize(this.maxSize);
        return previous;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0031, code lost:
        throw new java.lang.IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void trimToSize(int r7) {
        /*
            r6 = this;
        L_0x0000:
            monitor-enter(r6)
            int r3 = r6.size     // Catch:{ all -> 0x0032 }
            if (r3 < 0) goto L_0x0011
            java.util.LinkedHashMap<K, V> r3 = r6.map     // Catch:{ all -> 0x0032 }
            boolean r3 = r3.isEmpty()     // Catch:{ all -> 0x0032 }
            if (r3 == 0) goto L_0x0035
            int r3 = r6.size     // Catch:{ all -> 0x0032 }
            if (r3 == 0) goto L_0x0035
        L_0x0011:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0032 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0032 }
            r4.<init>()     // Catch:{ all -> 0x0032 }
            java.lang.Class r5 = r6.getClass()     // Catch:{ all -> 0x0032 }
            java.lang.String r5 = r5.getName()     // Catch:{ all -> 0x0032 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0032 }
            java.lang.String r5 = ".sizeOf() is reporting inconsistent results!"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0032 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0032 }
            r3.<init>(r4)     // Catch:{ all -> 0x0032 }
            throw r3     // Catch:{ all -> 0x0032 }
        L_0x0032:
            r3 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x0032 }
            throw r3
        L_0x0035:
            int r3 = r6.size     // Catch:{ all -> 0x0032 }
            if (r3 <= r7) goto L_0x0041
            java.util.LinkedHashMap<K, V> r3 = r6.map     // Catch:{ all -> 0x0032 }
            boolean r3 = r3.isEmpty()     // Catch:{ all -> 0x0032 }
            if (r3 == 0) goto L_0x0043
        L_0x0041:
            monitor-exit(r6)     // Catch:{ all -> 0x0032 }
            return
        L_0x0043:
            java.util.LinkedHashMap<K, V> r3 = r6.map     // Catch:{ all -> 0x0032 }
            java.util.Set r3 = r3.entrySet()     // Catch:{ all -> 0x0032 }
            java.util.Iterator r3 = r3.iterator()     // Catch:{ all -> 0x0032 }
            java.lang.Object r1 = r3.next()     // Catch:{ all -> 0x0032 }
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1     // Catch:{ all -> 0x0032 }
            java.lang.Object r0 = r1.getKey()     // Catch:{ all -> 0x0032 }
            java.lang.Object r2 = r1.getValue()     // Catch:{ all -> 0x0032 }
            java.util.LinkedHashMap<K, V> r3 = r6.map     // Catch:{ all -> 0x0032 }
            r3.remove(r0)     // Catch:{ all -> 0x0032 }
            int r3 = r6.size     // Catch:{ all -> 0x0032 }
            int r4 = r6.safeSizeOf(r0, r2)     // Catch:{ all -> 0x0032 }
            int r3 = r3 - r4
            r6.size = r3     // Catch:{ all -> 0x0032 }
            int r3 = r6.evictionCount     // Catch:{ all -> 0x0032 }
            int r3 = r3 + 1
            r6.evictionCount = r3     // Catch:{ all -> 0x0032 }
            monitor-exit(r6)     // Catch:{ all -> 0x0032 }
            r3 = 1
            r4 = 0
            r6.entryRemoved(r3, r0, r2, r4)
            goto L_0x0000
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sqlcrypto.LruCache.trimToSize(int):void");
    }

    public final V remove(K key) {
        Object previous;
        if (key == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            previous = this.map.remove(key);
            if (previous != null) {
                this.size -= safeSizeOf(key, previous);
            }
        }
        if (previous != null) {
            entryRemoved(false, key, previous, null);
        }
        return previous;
    }

    /* access modifiers changed from: protected */
    public void entryRemoved(boolean evicted, K key, V oldValue, V newValue) {
    }

    /* access modifiers changed from: protected */
    public V create(K key) {
        return null;
    }

    private int safeSizeOf(K key, V value) {
        int result = sizeOf(key, value);
        if (result >= 0) {
            return result;
        }
        throw new IllegalStateException("Negative size: " + key + "=" + value);
    }

    /* access modifiers changed from: protected */
    public int sizeOf(K key, V value) {
        return 1;
    }

    public final void evictAll() {
        trimToSize(-1);
    }

    public final synchronized int size() {
        return this.size;
    }

    public final synchronized int maxSize() {
        return this.maxSize;
    }

    public final synchronized int hitCount() {
        return this.hitCount;
    }

    public final synchronized int missCount() {
        return this.missCount;
    }

    public final synchronized int createCount() {
        return this.createCount;
    }

    public final synchronized int putCount() {
        return this.putCount;
    }

    public final synchronized int evictionCount() {
        return this.evictionCount;
    }

    public final synchronized Map<K, V> snapshot() {
        return new LinkedHashMap(this.map);
    }

    public final synchronized String toString() {
        String format;
        int hitPercent = 0;
        synchronized (this) {
            int accesses = this.hitCount + this.missCount;
            if (accesses != 0) {
                hitPercent = (this.hitCount * 100) / accesses;
            }
            format = String.format("LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", new Object[]{Integer.valueOf(this.maxSize), Integer.valueOf(this.hitCount), Integer.valueOf(this.missCount), Integer.valueOf(hitPercent)});
        }
        return format;
    }
}
