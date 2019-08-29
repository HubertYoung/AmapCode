package com.alipay.mobile.common.cache.disk.lru;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.cache.disk.DiskCache;
import com.alipay.mobile.common.cache.disk.Entity;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public abstract class LruDiskCache extends DiskCache {
    public LruDiskCache() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void init() {
        this.mEntities = new LinkedHashMap<String, Entity>(10, 0.75f, true) {
            {
                if (Boolean.FALSE.booleanValue()) {
                    Log.v("hackbyte ", ClassVerifier.class.toString());
                }
            }

            /* access modifiers changed from: protected */
            public boolean removeEldestEntry(Entry<String, Entity> eldest) {
                if (LruDiskCache.this.mSize < LruDiskCache.this.mMaxsize) {
                    return false;
                }
                LruDiskCache.this.mSize = LruDiskCache.this.mSize - eldest.getValue().getSize();
                LruDiskCache.this.a(eldest.getKey());
                return true;
            }
        };
        this.mGroup = new HashMap();
    }

    /* access modifiers changed from: private */
    public void a(String url) {
        removeCacheFile(url);
    }
}
