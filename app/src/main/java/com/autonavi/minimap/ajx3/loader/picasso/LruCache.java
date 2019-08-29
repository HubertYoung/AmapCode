package com.autonavi.minimap.ajx3.loader.picasso;

import android.content.Context;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class LruCache<T> implements Cache<T> {
    private int evictionCount;
    private int hitCount;
    final LinkedHashMap<String, T> map;
    private final int maxSize;
    private int missCount;
    private int putCount;
    private int size;

    /* access modifiers changed from: protected */
    public int calculateValueBytes(T t) {
        return 0;
    }

    public LruCache(Context context) {
        this(Utils.calculateMemoryCacheSize(context));
    }

    public LruCache(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("Max size must be positive.");
        }
        this.maxSize = i;
        this.map = new LinkedHashMap<>(0, 0.75f, true);
    }

    public T get(String str) {
        if (str == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            T t = this.map.get(str);
            if (t != null) {
                this.hitCount++;
                return t;
            }
            this.missCount++;
            return null;
        }
    }

    public void set(String str, T t) {
        if (str == null || t == null) {
            throw new NullPointerException("key == null || value == null");
        }
        synchronized (this) {
            this.putCount++;
            this.size += calculateValueBytes(t);
            Object put = this.map.put(str, t);
            if (put != null) {
                this.size -= calculateValueBytes(put);
            }
        }
        trimToSize(this.maxSize);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x006c, code lost:
        r0 = new java.lang.StringBuilder();
        r0.append(getClass().getName());
        r0.append(".sizeOf() is reporting inconsistent results!");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x008a, code lost:
        throw new java.lang.IllegalStateException(r0.toString());
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void trimToSize(int r4) {
        /*
            r3 = this;
        L_0x0000:
            monitor-enter(r3)
            int r0 = r3.size     // Catch:{ all -> 0x008b }
            if (r0 < 0) goto L_0x006c
            java.util.LinkedHashMap<java.lang.String, T> r0 = r3.map     // Catch:{ all -> 0x008b }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x008b }
            if (r0 == 0) goto L_0x0012
            int r0 = r3.size     // Catch:{ all -> 0x008b }
            if (r0 == 0) goto L_0x0012
            goto L_0x006c
        L_0x0012:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x008b }
            java.lang.String r1 = "LruCache: trimToSize() size = "
            r0.<init>(r1)     // Catch:{ all -> 0x008b }
            int r1 = r3.size     // Catch:{ all -> 0x008b }
            r0.append(r1)     // Catch:{ all -> 0x008b }
            java.lang.String r1 = ", maxSize = "
            r0.append(r1)     // Catch:{ all -> 0x008b }
            r0.append(r4)     // Catch:{ all -> 0x008b }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x008b }
            com.autonavi.minimap.ajx3.util.LogHelper.d(r0)     // Catch:{ all -> 0x008b }
            int r0 = r3.size     // Catch:{ all -> 0x008b }
            if (r0 <= r4) goto L_0x006a
            java.util.LinkedHashMap<java.lang.String, T> r0 = r3.map     // Catch:{ all -> 0x008b }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x008b }
            if (r0 == 0) goto L_0x003a
            goto L_0x006a
        L_0x003a:
            java.util.LinkedHashMap<java.lang.String, T> r0 = r3.map     // Catch:{ all -> 0x008b }
            java.util.Set r0 = r0.entrySet()     // Catch:{ all -> 0x008b }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x008b }
            java.lang.Object r0 = r0.next()     // Catch:{ all -> 0x008b }
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch:{ all -> 0x008b }
            java.lang.Object r1 = r0.getKey()     // Catch:{ all -> 0x008b }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x008b }
            java.lang.Object r0 = r0.getValue()     // Catch:{ all -> 0x008b }
            java.util.LinkedHashMap<java.lang.String, T> r2 = r3.map     // Catch:{ all -> 0x008b }
            r2.remove(r1)     // Catch:{ all -> 0x008b }
            int r1 = r3.size     // Catch:{ all -> 0x008b }
            int r0 = r3.calculateValueBytes(r0)     // Catch:{ all -> 0x008b }
            int r1 = r1 - r0
            r3.size = r1     // Catch:{ all -> 0x008b }
            int r0 = r3.evictionCount     // Catch:{ all -> 0x008b }
            int r0 = r0 + 1
            r3.evictionCount = r0     // Catch:{ all -> 0x008b }
            monitor-exit(r3)     // Catch:{ all -> 0x008b }
            goto L_0x0000
        L_0x006a:
            monitor-exit(r3)     // Catch:{ all -> 0x008b }
            return
        L_0x006c:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException     // Catch:{ all -> 0x008b }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x008b }
            r0.<init>()     // Catch:{ all -> 0x008b }
            java.lang.Class r1 = r3.getClass()     // Catch:{ all -> 0x008b }
            java.lang.String r1 = r1.getName()     // Catch:{ all -> 0x008b }
            r0.append(r1)     // Catch:{ all -> 0x008b }
            java.lang.String r1 = ".sizeOf() is reporting inconsistent results!"
            r0.append(r1)     // Catch:{ all -> 0x008b }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x008b }
            r4.<init>(r0)     // Catch:{ all -> 0x008b }
            throw r4     // Catch:{ all -> 0x008b }
        L_0x008b:
            r4 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x008b }
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.loader.picasso.LruCache.trimToSize(int):void");
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

    public final synchronized void clear() {
        evictAll();
    }

    public final synchronized void clearKeyUri(String str) {
        int length = str.length();
        Iterator<Entry<String, T>> it = this.map.entrySet().iterator();
        boolean z = false;
        while (it.hasNext()) {
            Entry next = it.next();
            String str2 = (String) next.getKey();
            Object value = next.getValue();
            int indexOf = str2.indexOf(10);
            if (indexOf == length && str2.substring(0, indexOf).equals(str)) {
                it.remove();
                this.size -= calculateValueBytes(value);
                z = true;
            }
        }
        if (z) {
            trimToSize(this.maxSize);
        }
    }

    public final synchronized int hitCount() {
        return this.hitCount;
    }

    public final synchronized int missCount() {
        return this.missCount;
    }

    public final synchronized int putCount() {
        return this.putCount;
    }

    public final synchronized int evictionCount() {
        return this.evictionCount;
    }
}
