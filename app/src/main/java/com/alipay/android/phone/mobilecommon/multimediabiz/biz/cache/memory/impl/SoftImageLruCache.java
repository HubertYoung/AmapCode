package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.memory.impl;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.memory.BitmapReference;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.memory.MemoryCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.Map.Entry;

public class SoftImageLruCache implements MemoryCache {
    public static final String TAG = "SoftImageLruCache";
    private LruCache<String, BitmapReference> a;

    public SoftImageLruCache(int maxSize) {
        this.a = new LruCache<>(maxSize);
    }

    public boolean put(String key, Bitmap value) {
        if (value == null || value.isRecycled()) {
            return false;
        }
        this.a.put(key, a(value));
        return true;
    }

    public Bitmap get(String key) {
        SoftReference reference = (SoftReference) this.a.get(key);
        if (reference == null) {
            return null;
        }
        return (Bitmap) reference.get();
    }

    public Bitmap remove(String key) {
        SoftReference reference = (SoftReference) this.a.remove(key);
        if (reference == null) {
            return null;
        }
        return (Bitmap) reference.get();
    }

    public void trimToSize(int maxSize) {
        this.a.trimToSize(maxSize);
    }

    public long getMemoryMaxSize() {
        return 0;
    }

    public Collection<String> keys() {
        return this.a.snapshot().keySet();
    }

    public void clear() {
        this.a.evictAll();
    }

    public void knockOutExpired(long aliveTime) {
        Logger.D(TAG, "knockOutExpired aliveTime: " + aliveTime, new Object[0]);
        for (Entry entry : this.a.snapshot().entrySet()) {
            BitmapReference reference = (BitmapReference) entry.getValue();
            if (reference == null || System.currentTimeMillis() - reference.getLastAccessTime() > aliveTime) {
                Logger.D(TAG, "knockOutExpired key: " + ((String) entry.getKey()) + ", reference: " + reference, new Object[0]);
                this.a.remove(entry.getKey());
            }
        }
    }

    public void debugInfo() {
        if (this.a != null) {
            Logger.D(TAG, "debugInfo: " + this.a.toString() + ", size: " + this.a.size(), new Object[0]);
        }
    }

    public Bitmap get(String key, Bitmap pre) {
        return get(key);
    }

    private static BitmapReference a(Bitmap bitmap) {
        return new BitmapReference(bitmap);
    }
}
