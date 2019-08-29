package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.fast;

import android.graphics.Bitmap;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.memory.MemoryCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ImageUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.util.Collection;

public class FastBitmapMemDiskCache implements MemoryCache {
    private BitmapNativeCache a;

    public FastBitmapMemDiskCache(int maxSize, boolean useTQ) {
        try {
            this.a = BitmapNativeCache.open(maxSize, useTQ);
        } catch (Throwable t) {
            Logger.E((String) "FastBitmapMemDiskCache", t, (String) "init BitmapNativeCache error", new Object[0]);
        }
    }

    public boolean put(String key, Bitmap value) {
        if (this.a == null) {
            return false;
        }
        this.a.putBitmap(key, value);
        return true;
    }

    public Bitmap get(String key) {
        return get(key, null);
    }

    public Bitmap get(String key, Bitmap pre) {
        if (this.a == null) {
            return null;
        }
        Bitmap bitmap = this.a.getBitmap(key, pre);
        if (ImageUtils.checkBitmap(bitmap)) {
            return bitmap;
        }
        String refKey = CacheContext.get().queryAliasKey(key);
        if (!TextUtils.isEmpty(refKey)) {
            return this.a.getBitmap(refKey, pre);
        }
        return bitmap;
    }

    public Bitmap remove(String key) {
        if (this.a != null) {
            this.a.remove(key);
        }
        return null;
    }

    public void trimToSize(int maxSize) {
        if (this.a != null) {
            this.a.trimToSize(maxSize);
        }
    }

    public long getMemoryMaxSize() {
        return 0;
    }

    public Collection<String> keys() {
        if (this.a != null) {
            return this.a.keys();
        }
        return null;
    }

    public void clear() {
        if (this.a != null) {
            this.a.cleanup();
        }
    }

    public void knockOutExpired(long aliveTime) {
        if (this.a != null) {
            this.a.knockOutExpired(aliveTime);
        }
    }

    public void debugInfo() {
        if (this.a != null) {
            this.a.debugInfo();
        }
    }
}
