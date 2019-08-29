package com.squareup.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class LruCache implements Cache {
    private int evictionCount;
    private int hitCount;
    final LinkedHashMap<String, Bitmap> map;
    private final int maxSize;
    private int missCount;
    private int putCount;
    private int size;

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

    public Bitmap get(String str) {
        if (str == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            Bitmap bitmap = this.map.get(str);
            if (bitmap != null) {
                this.hitCount++;
                return bitmap;
            }
            this.missCount++;
            return null;
        }
    }

    public void set(String str, Bitmap bitmap) {
        if (str == null || bitmap == null) {
            throw new NullPointerException("key == null || bitmap == null");
        }
        synchronized (this) {
            this.putCount++;
            this.size += Utils.getBitmapBytes(bitmap);
            Bitmap bitmap2 = (Bitmap) this.map.put(str, bitmap);
            if (bitmap2 != null) {
                this.size -= Utils.getBitmapBytes(bitmap2);
            }
        }
        trimToSize(this.maxSize);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0053, code lost:
        r0 = new java.lang.StringBuilder();
        r0.append(getClass().getName());
        r0.append(".sizeOf() is reporting inconsistent results!");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0071, code lost:
        throw new java.lang.IllegalStateException(r0.toString());
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void trimToSize(int r4) {
        /*
            r3 = this;
        L_0x0000:
            monitor-enter(r3)
            int r0 = r3.size     // Catch:{ all -> 0x0072 }
            if (r0 < 0) goto L_0x0053
            java.util.LinkedHashMap<java.lang.String, android.graphics.Bitmap> r0 = r3.map     // Catch:{ all -> 0x0072 }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x0072 }
            if (r0 == 0) goto L_0x0012
            int r0 = r3.size     // Catch:{ all -> 0x0072 }
            if (r0 == 0) goto L_0x0012
            goto L_0x0053
        L_0x0012:
            int r0 = r3.size     // Catch:{ all -> 0x0072 }
            if (r0 <= r4) goto L_0x0051
            java.util.LinkedHashMap<java.lang.String, android.graphics.Bitmap> r0 = r3.map     // Catch:{ all -> 0x0072 }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x0072 }
            if (r0 == 0) goto L_0x001f
            goto L_0x0051
        L_0x001f:
            java.util.LinkedHashMap<java.lang.String, android.graphics.Bitmap> r0 = r3.map     // Catch:{ all -> 0x0072 }
            java.util.Set r0 = r0.entrySet()     // Catch:{ all -> 0x0072 }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x0072 }
            java.lang.Object r0 = r0.next()     // Catch:{ all -> 0x0072 }
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch:{ all -> 0x0072 }
            java.lang.Object r1 = r0.getKey()     // Catch:{ all -> 0x0072 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x0072 }
            java.lang.Object r0 = r0.getValue()     // Catch:{ all -> 0x0072 }
            android.graphics.Bitmap r0 = (android.graphics.Bitmap) r0     // Catch:{ all -> 0x0072 }
            java.util.LinkedHashMap<java.lang.String, android.graphics.Bitmap> r2 = r3.map     // Catch:{ all -> 0x0072 }
            r2.remove(r1)     // Catch:{ all -> 0x0072 }
            int r1 = r3.size     // Catch:{ all -> 0x0072 }
            int r0 = com.squareup.picasso.Utils.getBitmapBytes(r0)     // Catch:{ all -> 0x0072 }
            int r1 = r1 - r0
            r3.size = r1     // Catch:{ all -> 0x0072 }
            int r0 = r3.evictionCount     // Catch:{ all -> 0x0072 }
            int r0 = r0 + 1
            r3.evictionCount = r0     // Catch:{ all -> 0x0072 }
            monitor-exit(r3)     // Catch:{ all -> 0x0072 }
            goto L_0x0000
        L_0x0051:
            monitor-exit(r3)     // Catch:{ all -> 0x0072 }
            return
        L_0x0053:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0072 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0072 }
            r0.<init>()     // Catch:{ all -> 0x0072 }
            java.lang.Class r1 = r3.getClass()     // Catch:{ all -> 0x0072 }
            java.lang.String r1 = r1.getName()     // Catch:{ all -> 0x0072 }
            r0.append(r1)     // Catch:{ all -> 0x0072 }
            java.lang.String r1 = ".sizeOf() is reporting inconsistent results!"
            r0.append(r1)     // Catch:{ all -> 0x0072 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0072 }
            r4.<init>(r0)     // Catch:{ all -> 0x0072 }
            throw r4     // Catch:{ all -> 0x0072 }
        L_0x0072:
            r4 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0072 }
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.picasso.LruCache.trimToSize(int):void");
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
        Iterator<Entry<String, Bitmap>> it = this.map.entrySet().iterator();
        boolean z = false;
        while (it.hasNext()) {
            Entry next = it.next();
            String str2 = (String) next.getKey();
            Bitmap bitmap = (Bitmap) next.getValue();
            int indexOf = str2.indexOf(10);
            if (indexOf == length && str2.substring(0, indexOf).equals(str)) {
                it.remove();
                this.size -= Utils.getBitmapBytes(bitmap);
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
