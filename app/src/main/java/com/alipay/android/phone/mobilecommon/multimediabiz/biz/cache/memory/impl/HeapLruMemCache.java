package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.memory.impl;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.memory.BitmapCacheInfo;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.memory.MemoryCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.util.CacheProxy;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.util.LruCacheProxy;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.util.TQCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.util.TQCacheProxy;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ImageUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.util.Collection;
import java.util.Map.Entry;

public class HeapLruMemCache implements MemoryCache {
    private static final Logger a = Logger.getLogger((String) "HeapLruMemCache");
    private int b;
    private CacheProxy<String, BitmapCacheInfo> c;

    public HeapLruMemCache(int maxSize, boolean useTQ) {
        a.d("HeapLruMemCache construct, maxSize: " + maxSize, new Object[0]);
        this.b = maxSize;
        if (useTQ) {
            this.c = new TQCacheProxy(new TQCache<String, BitmapCacheInfo>(maxSize) {
                /* access modifiers changed from: protected */
                public int sizeOf(String key, BitmapCacheInfo value) {
                    return HeapLruMemCache.b(value);
                }
            });
        } else {
            this.c = new LruCacheProxy(new LruCache<String, BitmapCacheInfo>(maxSize) {
                /* access modifiers changed from: protected */
                public int sizeOf(String key, BitmapCacheInfo value) {
                    return HeapLruMemCache.b(value);
                }
            });
        }
    }

    public boolean put(String key, Bitmap value) {
        this.c.put(key, new BitmapCacheInfo(value));
        return true;
    }

    public Bitmap get(String key) {
        BitmapCacheInfo cache = (BitmapCacheInfo) this.c.get(key);
        if (cache == null || !ImageUtils.checkBitmap(cache.mBitmap)) {
            String refKey = CacheContext.get().queryAliasKey(key);
            if (!TextUtils.isEmpty(refKey)) {
                cache = (BitmapCacheInfo) this.c.get(refKey);
            }
        }
        if (cache != null) {
            cache.lastAccessTime = System.currentTimeMillis();
        }
        if (cache == null) {
            return null;
        }
        return cache.mBitmap;
    }

    public Bitmap remove(String key) {
        BitmapCacheInfo cache = (BitmapCacheInfo) this.c.remove(key);
        if (cache == null) {
            return null;
        }
        return cache.mBitmap;
    }

    public Bitmap get(String key, Bitmap pre) {
        return get(key);
    }

    public void trimToSize(int maxSize) {
        this.c.trimToSize(maxSize);
    }

    public long getMemoryMaxSize() {
        return (long) this.b;
    }

    public Collection<String> keys() {
        return this.c.snapshot().keySet();
    }

    public void clear() {
        this.c.evictAll();
    }

    public void knockOutExpired(long aliveTime) {
        a.d("knockOutExpired aliveTime: " + aliveTime, new Object[0]);
        for (Entry entry : this.c.snapshot().entrySet()) {
            BitmapCacheInfo cache = (BitmapCacheInfo) entry.getValue();
            if (cache == null || System.currentTimeMillis() - cache.lastAccessTime > aliveTime) {
                a.d("knockOutExpired key: " + ((String) entry.getKey()) + ", cache: " + cache, new Object[0]);
                this.c.remove(entry.getKey());
            }
        }
    }

    public void debugInfo() {
        this.c.debugInfo();
    }

    /* access modifiers changed from: private */
    public static int b(BitmapCacheInfo info) {
        Bitmap bitmap = info == null ? null : info.mBitmap;
        if (bitmap == null) {
            return 0;
        }
        return bitmap.getRowBytes() * bitmap.getHeight();
    }
}
