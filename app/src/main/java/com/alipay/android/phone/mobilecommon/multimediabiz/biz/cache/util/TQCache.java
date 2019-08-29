package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.util;

import android.annotation.SuppressLint;
import android.support.v4.util.LruCache;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class TQCache<K, V> {
    public static final int QUEUE_INDEX_FIFO = 0;
    public static final int QUEUE_INDEX_LRU = 1;
    private LruCache<K, V> a;
    private LruCache<K, V> b;
    /* access modifiers changed from: private */
    public final AtomicBoolean c;
    private int d;
    private int e;
    private int f;
    private int g;

    public TQCache(int maxSize, float ratio) {
        this.c = new AtomicBoolean(false);
        this.d = maxSize;
        int fifoSize = (int) (((float) maxSize) * ratio);
        this.a = new LruCache<K, V>(fifoSize) {
            /* access modifiers changed from: protected */
            public int sizeOf(K key, V value) {
                return TQCache.this.sizeOf(key, value);
            }

            /* access modifiers changed from: protected */
            public void entryRemoved(boolean evicted, K key, V oldValue, V newValue) {
                if (!TQCache.this.c.get()) {
                    TQCache.this.entryRemoved(0, evicted, key, oldValue, newValue);
                }
            }
        };
        this.b = new LruCache<K, V>(this.d - fifoSize) {
            /* access modifiers changed from: protected */
            public int sizeOf(K key, V value) {
                return TQCache.this.sizeOf(key, value);
            }

            /* access modifiers changed from: protected */
            public void entryRemoved(boolean evicted, K key, V oldValue, V newValue) {
                TQCache.this.entryRemoved(1, evicted, key, oldValue, newValue);
            }
        };
    }

    public TQCache(int maxSize) {
        this(maxSize, 0.5f);
    }

    /* access modifiers changed from: protected */
    public int sizeOf(K key, V value) {
        return 1;
    }

    /* access modifiers changed from: protected */
    public void entryRemoved(int queueIndex, boolean evicted, K key, V oldValue, V newValue) {
    }

    public V get(K key) {
        Object value = this.b.get(key);
        if (value == null) {
            synchronized (this.c) {
                this.c.set(true);
                value = this.a.remove(key);
                this.c.set(false);
            }
            if (value != null) {
                this.f++;
                this.b.put(key, value);
            }
        }
        if (value != null) {
            this.e++;
        } else {
            this.g++;
        }
        return value;
    }

    public V put(K key, V value) {
        Object pre = null;
        if (this.b.get(key) != null) {
            pre = this.b.put(key, value);
        }
        if (pre == null) {
            return this.a.put(key, value);
        }
        return pre;
    }

    public V remove(K key) {
        Object value = this.a.remove(key);
        if (value == null) {
            return this.b.remove(key);
        }
        return value;
    }

    public void evictAll() {
        synchronized (this.c) {
            this.a.evictAll();
        }
        this.b.evictAll();
    }

    public int size() {
        return this.a.size() + this.b.size();
    }

    public int maxSize() {
        return this.d;
    }

    public Map<K, V> snapshot() {
        Map snapshot = new HashMap();
        snapshot.putAll(this.a.snapshot());
        snapshot.putAll(this.b.snapshot());
        return snapshot;
    }

    public void trimToSize(int size) {
        if (size() > size) {
            int fifoKeepSize = size - this.b.size();
            synchronized (this.c) {
                this.a.trimToSize(fifoKeepSize);
            }
            this.b.trimToSize(size);
        }
    }

    @SuppressLint({"DefaultLocale"})
    public final String toString() {
        String format;
        int hitPercent = 0;
        synchronized (this) {
            int accesses = this.e + this.g;
            if (accesses != 0) {
                hitPercent = (this.e * 100) / accesses;
            }
            format = String.format("TwoQueuesCache[maxSize=%d,hits=%d<fifoHits=%d>,misses=%d,hitRate=%d%%]", new Object[]{Integer.valueOf(this.d), Integer.valueOf(this.e), Integer.valueOf(this.f), Integer.valueOf(this.g), Integer.valueOf(hitPercent)});
        }
        return format;
    }
}
