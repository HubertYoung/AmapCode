package com.alipay.mobile.common.cache.mem.lru;

import android.graphics.Bitmap;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.cache.mem.Entity;
import com.alipay.mobile.common.cache.mem.MemCache;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class ImageCache extends MemCache<Bitmap> {
    private static ImageCache a;
    protected long mSize;

    public static synchronized ImageCache getInstance() {
        ImageCache imageCache;
        synchronized (ImageCache.class) {
            if (a == null) {
                a = new ImageCache();
            }
            imageCache = a;
        }
        return imageCache;
    }

    private ImageCache() {
        this.mSize = 0;
        this.mMap = new LinkedHashMap<String, Entity<Bitmap>>(10, 0.75f, true) {
            {
                if (Boolean.FALSE.booleanValue()) {
                    Log.v("hackbyte ", ClassVerifier.class.toString());
                }
            }

            /* access modifiers changed from: protected */
            public boolean removeEldestEntry(Entry<String, Entity<Bitmap>> eldest) {
                if (ImageCache.this.mSize < ImageCache.a()) {
                    return false;
                }
                ((Bitmap) eldest.getValue().getValue()).recycle();
                ImageCache.this.mSize -= (long) ((ImageEntity) eldest.getValue()).getSize();
                return true;
            }
        };
        this.mGroup = new HashMap();
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    /* access modifiers changed from: private */
    public static long a() {
        return Runtime.getRuntime().maxMemory() / 8;
    }

    public synchronized void put(String owner, String group, String key, Bitmap value) {
        super.put(owner, group, key, value);
    }

    public synchronized Bitmap get(String owner, String key) {
        return (Bitmap) super.get(owner, key);
    }

    public synchronized Bitmap remove(String key) {
        return (Bitmap) super.remove(key);
    }

    public long getMaxsize() {
        return a();
    }

    public synchronized long getSize() {
        return this.mSize;
    }

    /* access modifiers changed from: protected */
    public Entity<Bitmap> makeEntity(String owner, String group, Bitmap value) {
        return new ImageEntity(owner, group, value);
    }

    /* access modifiers changed from: protected */
    public void recordRemove(Entity<Bitmap> entity) {
        this.mSize -= (long) ((ImageEntity) entity).getSize();
    }

    /* access modifiers changed from: protected */
    public void recordPut(Entity<Bitmap> entity) {
        this.mSize += (long) ((ImageEntity) entity).getSize();
    }
}
